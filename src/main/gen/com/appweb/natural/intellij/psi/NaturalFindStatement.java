// This is a generated file. Not intended for manual editing.
package com.appweb.natural.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NaturalFindStatement extends PsiElement {

  @Nullable
  NaturalCipherClause getCipherClause();

  @NotNull
  List<NaturalCoupledClause> getCoupledClauseList();

  @Nullable
  NaturalExpression getExpression();

  @Nullable
  NaturalFindCriteria getFindCriteria();

  @Nullable
  NaturalPasswordClause getPasswordClause();

  @Nullable
  NaturalRetainClause getRetainClause();

  @Nullable
  NaturalSortedByClause getSortedByClause();

  @Nullable
  NaturalWhereClause getWhereClause();

}
