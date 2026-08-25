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

public class NaturalFindStatementImpl extends ASTWrapperPsiElement implements NaturalFindStatement {

  public NaturalFindStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitFindStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NaturalVisitor) accept((NaturalVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NaturalCipherClause getCipherClause() {
    return findChildByClass(NaturalCipherClause.class);
  }

  @Override
  @NotNull
  public List<NaturalCoupledClause> getCoupledClauseList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalCoupledClause.class);
  }

  @Override
  @Nullable
  public NaturalExpression getExpression() {
    return findChildByClass(NaturalExpression.class);
  }

  @Override
  @Nullable
  public NaturalFindCriteria getFindCriteria() {
    return findChildByClass(NaturalFindCriteria.class);
  }

  @Override
  @Nullable
  public NaturalPasswordClause getPasswordClause() {
    return findChildByClass(NaturalPasswordClause.class);
  }

  @Override
  @Nullable
  public NaturalRetainClause getRetainClause() {
    return findChildByClass(NaturalRetainClause.class);
  }

  @Override
  @Nullable
  public NaturalSortedByClause getSortedByClause() {
    return findChildByClass(NaturalSortedByClause.class);
  }

  @Override
  @Nullable
  public NaturalWhereClause getWhereClause() {
    return findChildByClass(NaturalWhereClause.class);
  }

}
