package com.appweb.natural.intellij.formatting

import com.appweb.natural.intellij.psi.NaturalTypes
import com.intellij.formatting.Alignment
import com.intellij.formatting.Block
import com.intellij.formatting.ChildAttributes
import com.intellij.formatting.Indent
import com.intellij.formatting.Spacing
import com.intellij.formatting.SpacingBuilder
import com.intellij.formatting.Wrap
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

class NaturalBlock(
    private val node: ASTNode,
    private val indent: Indent,
    wrap: Wrap?,
    alignment: Alignment?,
    private val spacingBuilder: SpacingBuilder
) : AbstractBlock(node, wrap, alignment) {

    companion object {
        // Block types whose direct STATEMENT children should be indented
        private val CONTAINER_BLOCK_TYPES = TokenSet.create(
            NaturalTypes.IF_BLOCK,
            NaturalTypes.FOR_BLOCK,
            NaturalTypes.REPEAT_BLOCK,
            NaturalTypes.READ_BLOCK,
            NaturalTypes.FIND_BLOCK,
            NaturalTypes.SORT_BLOCK,
            NaturalTypes.DEFINE_SUBROUTINE_BLOCK,
            NaturalTypes.AT_BREAK_BLOCK,
            NaturalTypes.AT_END_OF_DATA_BLOCK,
        )

        // DECIDE block clause types that should be indented inside a DECIDE block
        private val DECIDE_FOR_CLAUSE_TYPES = TokenSet.create(
            NaturalTypes.WHEN_FOR_CLAUSE,
            NaturalTypes.WHEN_ANY_CLAUSE,
            NaturalTypes.WHEN_ALL_CLAUSE,
            NaturalTypes.WHEN_NONE_CLAUSE,
        )

        private val DECIDE_ON_CLAUSE_TYPES = TokenSet.create(
            NaturalTypes.DECIDE_ON_VALUE_CLAUSE,
            NaturalTypes.DECIDE_ON_ANY_CLAUSE,
            NaturalTypes.DECIDE_ON_ALL_CLAUSE,
            NaturalTypes.DECIDE_ON_NONE_CLAUSE,
        )

        // WHEN/VALUE clause types whose STATEMENT children should be indented
        private val WHEN_CLAUSE_TYPES = TokenSet.create(
            NaturalTypes.WHEN_FOR_CLAUSE,
            NaturalTypes.WHEN_ANY_CLAUSE,
            NaturalTypes.WHEN_ALL_CLAUSE,
            NaturalTypes.WHEN_NONE_CLAUSE,
            NaturalTypes.DECIDE_ON_VALUE_CLAUSE,
            NaturalTypes.DECIDE_ON_ANY_CLAUSE,
            NaturalTypes.DECIDE_ON_ALL_CLAUSE,
            NaturalTypes.DECIDE_ON_NONE_CLAUSE,
        )
    }

    override fun getIndent(): Indent = indent

    override fun buildChildren(): List<Block> {
        // DATA_AREA_BLOCK builds children hierarchically by level number, so that level 2 sits
        // under level 1, level 3 sits under level 2, etc. Each nesting step contributes one
        // NormalIndent — so we get the user's configured indent unit (tab or spaces) per level,
        // never a mix of types.
        if (node.elementType == NaturalTypes.DATA_AREA_BLOCK) {
            return buildDataAreaChildren()
        }

        val children = mutableListOf<Block>()
        var child: ASTNode? = node.firstChildNode
        while (child != null) {
            if (child.elementType != TokenType.WHITE_SPACE) {
                children.add(
                    NaturalBlock(
                        child,
                        computeChildIndent(child),
                        null,
                        null,
                        spacingBuilder
                    )
                )
            }
            child = child.treeNext
        }
        return children
    }

    private fun buildDataAreaChildren(): List<Block> {
        val nodes = mutableListOf<ASTNode>()
        var c: ASTNode? = node.firstChildNode
        while (c != null) {
            if (c.elementType != TokenType.WHITE_SPACE) nodes.add(c)
            c = c.treeNext
        }
        return buildLevelGroup(nodes, 0, nodes.size, 1)
    }

    /**
     * Walks [nodes] from [from] (inclusive) to [to] (exclusive) and emits blocks at [currentLevel].
     * Variable declarations whose level is higher than [currentLevel] are collected into a
     * synthetic wrapper block that adds one NormalIndent and recursively groups its own children.
     */
    private fun buildLevelGroup(nodes: List<ASTNode>, from: Int, to: Int, currentLevel: Int): List<Block> {
        val result = mutableListOf<Block>()
        var i = from
        while (i < to) {
            val n = nodes[i]
            val level = if (n.elementType == NaturalTypes.VARIABLE_DECL) variableDeclLevel(n) else null

            if (level == null || level <= currentLevel) {
                // Non-decl (scope keyword, comment) or same/shallower level: emit directly.
                result.add(NaturalBlock(n, computeChildIndent(n), null, null, spacingBuilder))
                i++
                continue
            }

            // Deeper level: collect this and following deeper nodes into a synthetic wrapper.
            var end = i
            while (end < to) {
                val e = nodes[end]
                if (e.elementType == NaturalTypes.VARIABLE_DECL) {
                    val l = variableDeclLevel(e) ?: 1
                    if (l <= currentLevel) break
                }
                end++
            }
            val groupRange = TextRange(nodes[i].startOffset, nodes[end - 1].textRange.endOffset)
            val sub = buildLevelGroup(nodes, i, end, currentLevel + 1)
            result.add(SyntheticGroupBlock(groupRange, sub, Indent.getNormalIndent(), spacingBuilder))
            i = end
        }
        return result
    }

    private fun computeChildIndent(child: ASTNode): Indent {
        val parentType: IElementType = node.elementType
        val childType: IElementType = child.elementType

        // * comments are only valid at column 0 in Natural — never indent them
        if (childType == NaturalTypes.LINE_COMMENT && child.text.startsWith("*")) {
            return Indent.getAbsoluteNoneIndent()
        }

        // /* comments follow the same indentation as statements in their surrounding block
        val isStatementLike = childType == NaturalTypes.STATEMENT ||
            (childType == NaturalTypes.LINE_COMMENT && child.text.startsWith("/*"))

        // Indent statement bodies inside container blocks
        if (CONTAINER_BLOCK_TYPES.contains(parentType) && isStatementLike) {
            return Indent.getNormalIndent()
        }

        // Indent WHEN/VALUE clauses inside DECIDE blocks
        if (parentType == NaturalTypes.DECIDE_FOR_BLOCK && DECIDE_FOR_CLAUSE_TYPES.contains(childType)) {
            return Indent.getNormalIndent()
        }
        if (parentType == NaturalTypes.DECIDE_ON_BLOCK && DECIDE_ON_CLAUSE_TYPES.contains(childType)) {
            return Indent.getNormalIndent()
        }

        // Indent STATEMENT children inside WHEN/VALUE clauses
        if (WHEN_CLAUSE_TYPES.contains(parentType) && isStatementLike) {
            return Indent.getNormalIndent()
        }

        // DATA_AREA_BLOCK (LOCAL, PARAMETER, etc.) must stay at the left margin
        if (parentType == NaturalTypes.DEFINE_DATA_PHASE && childType == NaturalTypes.DATA_AREA_BLOCK) {
            return Indent.getNoneIndent()
        }

        // Variable-decl indenting inside DATA_AREA_BLOCK is handled by buildDataAreaChildren
        // via nested synthetic blocks — each level adds one NormalIndent.

        return Indent.getNoneIndent()
    }

    private fun variableDeclLevel(variableDecl: ASTNode): Int? {
        var first: ASTNode? = variableDecl.firstChildNode
        while (first != null && first.elementType == TokenType.WHITE_SPACE) {
            first = first.treeNext
        }
        if (first == null) return null
        if (first.elementType != NaturalTypes.LEVEL_NUMBER && first.elementType != NaturalTypes.NUMBER) return null
        return first.text.trim().toIntOrNull()
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? =
        spacingBuilder.getSpacing(this, child1, child2)

    override fun isLeaf(): Boolean = node.firstChildNode == null

    // Called by the IDE on Enter to decide the indent of a freshly inserted child.
    // Must mirror computeChildIndent — without this, AbstractBlock falls back to
    // heuristics that infer the new indent from the previous sibling, which drifts
    // one tab right inside DEFINE DATA and at the top level.
    override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
        val parentType: IElementType = node.elementType
        val indent = when {
            CONTAINER_BLOCK_TYPES.contains(parentType) -> Indent.getNormalIndent()
            parentType == NaturalTypes.DECIDE_FOR_BLOCK -> Indent.getNormalIndent()
            parentType == NaturalTypes.DECIDE_ON_BLOCK -> Indent.getNormalIndent()
            WHEN_CLAUSE_TYPES.contains(parentType) -> Indent.getNormalIndent()
            // DATA_AREA_BLOCK is grouped hierarchically; the real per-level indent is set
            // when the user types the level number and the formatter rewraps.
            parentType == NaturalTypes.DATA_AREA_BLOCK -> Indent.getNoneIndent()
            else -> Indent.getNoneIndent()
        }
        return ChildAttributes(indent, null)
    }
}

/**
 * Synthetic block used to introduce one level of NormalIndent without a backing ASTNode.
 * Used by [NaturalBlock] to nest deeper variable declarations under shallower ones,
 * so each level contributes one normal indent in whatever tab/space style the user has
 * configured.
 */
private class SyntheticGroupBlock(
    private val textRange: TextRange,
    private val subBlocks: List<Block>,
    private val indent: Indent,
    private val spacingBuilder: SpacingBuilder
) : Block {
    override fun getTextRange(): TextRange = textRange
    override fun getSubBlocks(): List<Block> = subBlocks
    override fun getWrap(): Wrap? = null
    override fun getIndent(): Indent = indent
    override fun getAlignment(): Alignment? = null
    override fun getSpacing(child1: Block?, child2: Block): Spacing? =
        spacingBuilder.getSpacing(this, child1, child2)
    override fun getChildAttributes(newChildIndex: Int): ChildAttributes =
        ChildAttributes(Indent.getNoneIndent(), null)
    override fun isIncomplete(): Boolean = false
    override fun isLeaf(): Boolean = false
}
