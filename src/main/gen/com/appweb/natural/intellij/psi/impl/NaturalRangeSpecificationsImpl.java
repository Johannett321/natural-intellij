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

public class NaturalRangeSpecificationsImpl extends ASTWrapperPsiElement implements NaturalRangeSpecifications {

  public NaturalRangeSpecificationsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitRangeSpecifications(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NaturalVisitor) accept((NaturalVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NaturalRangeSpecOption1 getRangeSpecOption1() {
    return findChildByClass(NaturalRangeSpecOption1.class);
  }

  @Override
  @Nullable
  public NaturalRangeSpecOption2 getRangeSpecOption2() {
    return findChildByClass(NaturalRangeSpecOption2.class);
  }

  @Override
  @Nullable
  public NaturalRangeSpecOption3 getRangeSpecOption3() {
    return findChildByClass(NaturalRangeSpecOption3.class);
  }

}
