// This is a generated file. Not intended for manual editing.
package com.appweb.natural.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NaturalPrimary extends PsiElement {

  @Nullable
  NaturalCondition getCondition();

  @Nullable
  NaturalDataType getDataType();

  @Nullable
  NaturalExpression getExpression();

  @Nullable
  NaturalFunctionCall getFunctionCall();

  @Nullable
  NaturalMaskExpression getMaskExpression();

  @Nullable
  NaturalPrimary getPrimary();

  @NotNull
  List<NaturalSubscript> getSubscriptList();

  @Nullable
  NaturalSystemVarRef getSystemVarRef();

  @Nullable
  NaturalVariableRef getVariableRef();

}
