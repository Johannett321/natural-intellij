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

public class NaturalDecideOnBlockImpl extends ASTWrapperPsiElement implements NaturalDecideOnBlock {

  public NaturalDecideOnBlockImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitDecideOnBlock(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NaturalVisitor) accept((NaturalVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NaturalDecideOnAllClause getDecideOnAllClause() {
    return findChildByClass(NaturalDecideOnAllClause.class);
  }

  @Override
  @Nullable
  public NaturalDecideOnAnyClause getDecideOnAnyClause() {
    return findChildByClass(NaturalDecideOnAnyClause.class);
  }

  @Override
  @Nullable
  public NaturalDecideOnNoneClause getDecideOnNoneClause() {
    return findChildByClass(NaturalDecideOnNoneClause.class);
  }

  @Override
  @NotNull
  public List<NaturalDecideOnValueClause> getDecideOnValueClauseList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalDecideOnValueClause.class);
  }

  @Override
  @Nullable
  public NaturalExpression getExpression() {
    return findChildByClass(NaturalExpression.class);
  }

}
