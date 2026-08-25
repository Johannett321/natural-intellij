// This is a generated file. Not intended for manual editing.
package com.appweb.natural.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NaturalAssignmentStatement extends PsiElement {

  @Nullable
  NaturalAssignmentStatement getAssignmentStatement();

  @NotNull
  List<NaturalDataType> getDataTypeList();

  @Nullable
  NaturalExpression getExpression();

  @Nullable
  NaturalSystemVarRef getSystemVarRef();

  @Nullable
  NaturalVariableRef getVariableRef();

}
