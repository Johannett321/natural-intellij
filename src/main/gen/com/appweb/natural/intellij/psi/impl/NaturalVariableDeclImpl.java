// This is a generated file. Not intended for manual editing.
package com.appweb.natural.intellij.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.appweb.natural.intellij.psi.NaturalTypes.*;
import com.appweb.natural.intellij.psi.NaturalVariableDeclMixin;
import com.appweb.natural.intellij.psi.*;

public class NaturalVariableDeclImpl extends NaturalVariableDeclMixin implements NaturalVariableDecl {

  public NaturalVariableDeclImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitVariableDecl(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NaturalVisitor) accept((NaturalVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NaturalDataType getDataType() {
    return findChildByClass(NaturalDataType.class);
  }

  @Override
  @NotNull
  public List<NaturalVariableModifier> getVariableModifierList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalVariableModifier.class);
  }

}
