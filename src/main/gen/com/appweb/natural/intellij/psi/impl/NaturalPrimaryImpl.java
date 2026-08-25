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

public class NaturalPrimaryImpl extends ASTWrapperPsiElement implements NaturalPrimary {

  public NaturalPrimaryImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitPrimary(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NaturalVisitor) accept((NaturalVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NaturalCondition getCondition() {
    return findChildByClass(NaturalCondition.class);
  }

  @Override
  @Nullable
  public NaturalDataType getDataType() {
    return findChildByClass(NaturalDataType.class);
  }

  @Override
  @Nullable
  public NaturalExpression getExpression() {
    return findChildByClass(NaturalExpression.class);
  }

  @Override
  @Nullable
  public NaturalFunctionCall getFunctionCall() {
    return findChildByClass(NaturalFunctionCall.class);
  }

  @Override
  @Nullable
  public NaturalMaskExpression getMaskExpression() {
    return findChildByClass(NaturalMaskExpression.class);
  }

  @Override
  @Nullable
  public NaturalPrimary getPrimary() {
    return findChildByClass(NaturalPrimary.class);
  }

  @Override
  @NotNull
  public List<NaturalSubscript> getSubscriptList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalSubscript.class);
  }

  @Override
  @Nullable
  public NaturalSystemVarRef getSystemVarRef() {
    return findChildByClass(NaturalSystemVarRef.class);
  }

  @Override
  @Nullable
  public NaturalVariableRef getVariableRef() {
    return findChildByClass(NaturalVariableRef.class);
  }

}
