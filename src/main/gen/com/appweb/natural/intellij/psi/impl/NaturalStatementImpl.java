// This is a generated file. Not intended for manual editing.
package com.appweb.natural.intellij.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.appweb.natural.intellij.psi.NaturalTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.appweb.natural.intellij.psi.*;

public class NaturalStatementImpl extends ASTWrapperPsiElement implements NaturalStatement {

  public NaturalStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NaturalVisitor) accept((NaturalVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NaturalAssignmentStatement getAssignmentStatement() {
    return findChildByClass(NaturalAssignmentStatement.class);
  }

  @Override
  @Nullable
  public NaturalAtBreakBlock getAtBreakBlock() {
    return findChildByClass(NaturalAtBreakBlock.class);
  }

  @Override
  @Nullable
  public NaturalAtEndOfDataBlock getAtEndOfDataBlock() {
    return findChildByClass(NaturalAtEndOfDataBlock.class);
  }

  @Override
  @Nullable
  public NaturalAtEndOfPageBlock getAtEndOfPageBlock() {
    return findChildByClass(NaturalAtEndOfPageBlock.class);
  }

  @Override
  @Nullable
  public NaturalAtStartOfDataBlock getAtStartOfDataBlock() {
    return findChildByClass(NaturalAtStartOfDataBlock.class);
  }

  @Override
  @Nullable
  public NaturalAtTopPageBlock getAtTopPageBlock() {
    return findChildByClass(NaturalAtTopPageBlock.class);
  }

  @Override
  @Nullable
  public NaturalCallnatStatement getCallnatStatement() {
    return findChildByClass(NaturalCallnatStatement.class);
  }

  @Override
  @NotNull
  public List<NaturalDataType> getDataTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalDataType.class);
  }

  @Override
  @Nullable
  public NaturalDecideForBlock getDecideForBlock() {
    return findChildByClass(NaturalDecideForBlock.class);
  }

  @Override
  @Nullable
  public NaturalDecideOnBlock getDecideOnBlock() {
    return findChildByClass(NaturalDecideOnBlock.class);
  }

  @Override
  @Nullable
  public NaturalDefineSubroutineBlock getDefineSubroutineBlock() {
    return findChildByClass(NaturalDefineSubroutineBlock.class);
  }

  @Override
  @NotNull
  public List<NaturalExpression> getExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalExpression.class);
  }

  @Override
  @Nullable
  public NaturalFindBlock getFindBlock() {
    return findChildByClass(NaturalFindBlock.class);
  }

  @Override
  @Nullable
  public NaturalFindStatement getFindStatement() {
    return findChildByClass(NaturalFindStatement.class);
  }

  @Override
  @Nullable
  public NaturalForBlock getForBlock() {
    return findChildByClass(NaturalForBlock.class);
  }

  @Override
  @Nullable
  public NaturalIfBlock getIfBlock() {
    return findChildByClass(NaturalIfBlock.class);
  }

  @Override
  @Nullable
  public NaturalIfNoRecordsFoundClause getIfNoRecordsFoundClause() {
    return findChildByClass(NaturalIfNoRecordsFoundClause.class);
  }

  @Override
  @Nullable
  public NaturalOnErrorBlock getOnErrorBlock() {
    return findChildByClass(NaturalOnErrorBlock.class);
  }

  @Override
  @Nullable
  public NaturalReadBlock getReadBlock() {
    return findChildByClass(NaturalReadBlock.class);
  }

  @Override
  @Nullable
  public NaturalReadWorkBlock getReadWorkBlock() {
    return findChildByClass(NaturalReadWorkBlock.class);
  }

  @Override
  @Nullable
  public NaturalRepeatBlock getRepeatBlock() {
    return findChildByClass(NaturalRepeatBlock.class);
  }

  @Override
  @Nullable
  public NaturalSetTimeStatement getSetTimeStatement() {
    return findChildByClass(NaturalSetTimeStatement.class);
  }

  @Override
  @Nullable
  public NaturalSimpleStatements getSimpleStatements() {
    return findChildByClass(NaturalSimpleStatements.class);
  }

  @Override
  @Nullable
  public NaturalSortBlock getSortBlock() {
    return findChildByClass(NaturalSortBlock.class);
  }

  @Override
  @NotNull
  public List<NaturalUnaryExpr> getUnaryExprList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalUnaryExpr.class);
  }

}
