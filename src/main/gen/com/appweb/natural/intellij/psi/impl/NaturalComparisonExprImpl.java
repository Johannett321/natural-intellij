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

public class NaturalComparisonExprImpl extends ASTWrapperPsiElement implements NaturalComparisonExpr {

  public NaturalComparisonExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitComparisonExpr(this);
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
  @Nullable
  public NaturalCompOp getCompOp() {
    return findChildByClass(NaturalCompOp.class);
  }

  @Override
  @Nullable
  public NaturalVariableRef getVariableRef() {
    return findChildByClass(NaturalVariableRef.class);
  }

}
