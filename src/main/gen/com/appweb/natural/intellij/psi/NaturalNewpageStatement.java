// This is a generated file. Not intended for manual editing.
package com.appweb.natural.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NaturalNewpageStatement extends PsiElement {

  @NotNull
  List<NaturalDataType> getDataTypeList();

  @NotNull
  List<NaturalExpression> getExpressionList();

  @NotNull
  List<NaturalUnaryExpr> getUnaryExprList();

}
