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

public class NaturalReadBlockImpl extends ASTWrapperPsiElement implements NaturalReadBlock {

  public NaturalReadBlockImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitReadBlock(this);
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
  @Nullable
  public NaturalExpression getExpression() {
    return findChildByClass(NaturalExpression.class);
  }

  @Override
  @Nullable
  public NaturalInSharedHold getInSharedHold() {
    return findChildByClass(NaturalInSharedHold.class);
  }

  @Override
  @Nullable
  public NaturalMultiFetchClause getMultiFetchClause() {
    return findChildByClass(NaturalMultiFetchClause.class);
  }

  @Override
  @Nullable
  public NaturalPasswordClause getPasswordClause() {
    return findChildByClass(NaturalPasswordClause.class);
  }

  @Override
  @Nullable
  public NaturalRangeSpecifications getRangeSpecifications() {
    return findChildByClass(NaturalRangeSpecifications.class);
  }

  @Override
  @Nullable
  public NaturalSkipRecordsInHold getSkipRecordsInHold() {
    return findChildByClass(NaturalSkipRecordsInHold.class);
  }

  @Override
  @Nullable
  public NaturalStartingWithIsn getStartingWithIsn() {
    return findChildByClass(NaturalStartingWithIsn.class);
  }

  @Override
  @NotNull
  public List<NaturalStatement> getStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalStatement.class);
  }

  @Override
  @Nullable
  public NaturalWhereClause getWhereClause() {
    return findChildByClass(NaturalWhereClause.class);
  }

}
