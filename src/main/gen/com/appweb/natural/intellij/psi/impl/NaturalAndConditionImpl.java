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

public class NaturalAndConditionImpl extends ASTWrapperPsiElement implements NaturalAndCondition {

  public NaturalAndConditionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitAndCondition(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NaturalVisitor) accept((NaturalVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<NaturalAddExpr> getAddExprList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalAddExpr.class);
  }

  @Override
  @NotNull
  public List<NaturalAndCondition> getAndConditionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalAndCondition.class);
  }

  @Override
  @NotNull
  public List<NaturalCompOp> getCompOpList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalCompOp.class);
  }

  @Override
  @NotNull
  public List<NaturalNotCondition> getNotConditionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalNotCondition.class);
  }

}
