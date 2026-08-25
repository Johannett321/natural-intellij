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

public class NaturalDecideForBlockImpl extends ASTWrapperPsiElement implements NaturalDecideForBlock {

  public NaturalDecideForBlockImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitDecideForBlock(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NaturalVisitor) accept((NaturalVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NaturalWhenAllClause getWhenAllClause() {
    return findChildByClass(NaturalWhenAllClause.class);
  }

  @Override
  @Nullable
  public NaturalWhenAnyClause getWhenAnyClause() {
    return findChildByClass(NaturalWhenAnyClause.class);
  }

  @Override
  @NotNull
  public List<NaturalWhenForClause> getWhenForClauseList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalWhenForClause.class);
  }

  @Override
  @Nullable
  public NaturalWhenNoneClause getWhenNoneClause() {
    return findChildByClass(NaturalWhenNoneClause.class);
  }

}
