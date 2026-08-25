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

public class NaturalRangeSpecOption3Impl extends ASTWrapperPsiElement implements NaturalRangeSpecOption3 {

  public NaturalRangeSpecOption3Impl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitRangeSpecOption3(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NaturalVisitor) accept((NaturalVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NaturalExpression getExpression() {
    return findChildByClass(NaturalExpression.class);
  }

  @Override
  @Nullable
  public NaturalRangeSpecOption3A getRangeSpecOption3A() {
    return findChildByClass(NaturalRangeSpecOption3A.class);
  }

  @Override
  @Nullable
  public NaturalRangeSpecOption3B getRangeSpecOption3B() {
    return findChildByClass(NaturalRangeSpecOption3B.class);
  }

  @Override
  @Nullable
  public NaturalRangeSpecOption3C getRangeSpecOption3C() {
    return findChildByClass(NaturalRangeSpecOption3C.class);
  }

  @Override
  @NotNull
  public NaturalSequenceDirection getSequenceDirection() {
    return findNotNullChildByClass(NaturalSequenceDirection.class);
  }

}
