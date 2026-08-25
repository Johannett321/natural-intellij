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

public class NaturalSortBlockImpl extends ASTWrapperPsiElement implements NaturalSortBlock {

  public NaturalSortBlockImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitSortBlock(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NaturalVisitor) accept((NaturalVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NaturalSortGiveClause getSortGiveClause() {
    return findChildByClass(NaturalSortGiveClause.class);
  }

  @Override
  @NotNull
  public List<NaturalSortKey> getSortKeyList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalSortKey.class);
  }

  @Override
  @Nullable
  public NaturalSortUsingClause getSortUsingClause() {
    return findChildByClass(NaturalSortUsingClause.class);
  }

  @Override
  @NotNull
  public List<NaturalStatement> getStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalStatement.class);
  }

}
