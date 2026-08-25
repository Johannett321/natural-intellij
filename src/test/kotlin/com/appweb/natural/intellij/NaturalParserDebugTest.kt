package com.appweb.natural.intellij

import com.appweb.natural.intellij.language.filetypes.NaturalProgramFileType
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase

/**
 * Targeted debug test for specific parsing scenarios.
 */
class NaturalParserDebugTest : BasePlatformTestCase() {

    private fun parseAndGetErrors(content: String): List<Pair<Int, String>> {
        val psiFile = myFixture.configureByText(NaturalProgramFileType, content)
        return PsiTreeUtil.collectElementsOfType(psiFile, PsiErrorElement::class.java).map { err ->
            val offset = err.textOffset
            val before = content.take(offset)
            val line = before.count { it == '\n' } + 1
            val col = offset - (before.lastIndexOf('\n') + 1) + 1
            val lineStart = before.lastIndexOf('\n') + 1
            val lineEnd = content.indexOf('\n', offset).takeIf { it >= 0 } ?: content.length
            val context = content.substring(lineStart, lineEnd).trim()
            Pair(line, "L$line:C$col [$context] - ${err.errorDescription.take(80)}")
        }
    }

    /**
     * Minimal test: WRITE with trailing slash, then assignment.
     */
    fun testWriteSlashThenAssignment() {
        // First test: even simpler - WRITE then assignment directly
        val src1 = """
DEFINE DATA LOCAL
  1 A (A8)
  1 B (A8)
END-DEFINE
WRITE 'x'
A := B
END
""".trimIndent()
        val errors1 = parseAndGetErrors(src1)
        errors1.forEach { println("NO_SLASH_ERROR: ${it.second}") }

        // Test: WRITE / (just a slash)
        val srcWriteSlash = """
DEFINE DATA LOCAL
  1 A (A8)
  1 B (A8)
END-DEFINE
WRITE /
A := B
END
""".trimIndent()
        val errWriteSlash = parseAndGetErrors(srcWriteSlash)
        errWriteSlash.forEach { println("WRITE_SLASH_ERROR: ${it.second}") }

        // Test: WRITE 'x' / (on same line)
        val srcSameLine = """
DEFINE DATA LOCAL
  1 A (A8)
  1 B (A8)
END-DEFINE
WRITE 'x' /
A := B
END
""".trimIndent()
        val errSameLine = parseAndGetErrors(srcSameLine)
        errSameLine.forEach { println("WRITE_SAMELINE_ERROR: ${it.second}") }

        val src2 = """
DEFINE DATA LOCAL
  1 A (A8)
  1 B (A8)
END-DEFINE
WRITE 'x'
  /
A := B
END
""".trimIndent()
        val errors2 = parseAndGetErrors(src2)
        errors2.forEach { println("WITH_SLASH_ERROR: ${it.second}") }

        // Test 3: assignment without WRITE at all
        val src3 = """
DEFINE DATA LOCAL
  1 A (A8)
  1 B (A8)
END-DEFINE
A := B
END
""".trimIndent()
        val errors3 = parseAndGetErrors(src3)
        errors3.forEach { println("ASSIGN_ONLY_ERROR: ${it.second}") }

        assertTrue("Expected no errors in src1 but got: ${errors1.map { it.second }}", errors1.isEmpty())
        assertTrue("Expected no errors in src2 but got: ${errors2.map { it.second }}", errors2.isEmpty())
        assertTrue("Expected no errors in src3 but got: ${errors3.map { it.second }}", errors3.isEmpty())
    }

    /**
     * Test that WRITE statement followed by an assignment statement on the next line
     * is parsed correctly without consuming the assignment variable as a write arg.
     */
    fun testWriteFollowedByAssignment() {
        val src = """
DEFINE DATA LOCAL
  1 DATE-A8 (A8)
  1 DATE-EXPIRY-A8 (A8)
  1 MSG (A30)
END-DEFINE
WRITE 'Dates:' MSG
  / 'test' DATE-A8
  /
* comment
DATE-A8 := DATE-EXPIRY-A8
END
""".trimIndent()

        val errors = parseAndGetErrors(src)
        errors.forEach { println("ERROR: ${it.second}") }
        assertTrue("Expected no errors but got: ${errors.map { it.second }}", errors.isEmpty())
    }

    /**
     * Inspect PSI tree for failing case.
     */
    fun testInspectPsiTree() {
        val src = """
DEFINE DATA LOCAL
  1 A (A8)
  1 B (A8)
END-DEFINE
WRITE 'x' /
A := B
END
""".trimIndent()
        val psiFile = myFixture.configureByText(NaturalProgramFileType, src)

        fun printTree(node: com.intellij.lang.ASTNode, indent: String = "") {
            val text = node.text.replace("\n", "\\n").take(40)
            println("$indent${node.elementType}  '$text'")
            node.getChildren(null).forEach { printTree(it, "$indent  ") }
        }
        printTree(psiFile.node)
    }

    /**
     * Test assignment statement directly (baseline).
     */
    fun testSimpleAssignment() {
        val src = """
DEFINE DATA LOCAL
  1 A (A8)
  1 B (A8)
END-DEFINE
A := B
END
""".trimIndent()

        val errors = parseAndGetErrors(src)
        errors.forEach { println("ERROR: ${it.second}") }
        assertTrue("Expected no errors but got: ${errors.map { it.second }}", errors.isEmpty())
    }

    /**
     * A multi-line WRITE whose final argument is a bare `/`, followed by a comment and then an
     * assignment. The trailing slash previously caused the parser to keep extending the WRITE
     * argument list across the comment and swallow the assignment target.
     */
    fun testWriteWithTrailingSlashBeforeComment() {
        val src = """
DEFINE DATA LOCAL
  1 DATE-A8 (A8)
  1 DATE-EXPIRY-A8 (A8)
  1 PARM-ATTACHMENT (A10)
  1 DATE-LETTER (A8)
  1 DATE-DEADLINE (A8)
  1 DATE-EXPIRY (A8)
END-DEFINE
WRITE 'Dates used by this program:'
  / 'Letter dated' DATE-LETTER
  / 'Deadline    ' DATE-DEADLINE
  / 'Expires     ' DATE-EXPIRY
  / 'Attachment  ' PARM-ATTACHMENT
  /
* Dates compared below:
DATE-A8 := DATE-EXPIRY-A8
END
""".trimIndent()

        val errors = parseAndGetErrors(src)
        errors.forEach { println("ERROR: ${it.second}") }
        assertTrue("Expected no errors but got: ${errors.map { it.second }}", errors.isEmpty())
    }
}
