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

public class NaturalStoreStatementImpl extends ASTWrapperPsiElement implements NaturalStoreStatement {

  public NaturalStoreStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitStoreStatement(this);
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
  public NaturalPasswordClause getPasswordClause() {
    return findChildByClass(NaturalPasswordClause.class);
  }

}
