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

public class NaturalWriteWorkFileStatementImpl extends ASTWrapperPsiElement implements NaturalWriteWorkFileStatement {

  public NaturalWriteWorkFileStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitWriteWorkFileStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NaturalVisitor) accept((NaturalVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<NaturalDataType> getDataTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalDataType.class);
  }

  @Override
  @NotNull
  public List<NaturalExpression> getExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalExpression.class);
  }

  @Override
  @NotNull
  public List<NaturalUnaryExpr> getUnaryExprList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalUnaryExpr.class);
  }

}
