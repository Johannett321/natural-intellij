// This is a generated file. Not intended for manual editing.
package com.appweb.natural.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NaturalHistogramStatement extends PsiElement {

  @Nullable
  NaturalCipherClause getCipherClause();

  @Nullable
  NaturalExpression getExpression();

  @Nullable
  NaturalPasswordClause getPasswordClause();

  @Nullable
  NaturalRangeSpecifications getRangeSpecifications();

  @NotNull
  List<NaturalStatement> getStatementList();

  @Nullable
  NaturalWhereClause getWhereClause();

}
