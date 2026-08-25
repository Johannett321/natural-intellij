// This is a generated file. Not intended for manual editing.
package com.appweb.natural.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NaturalFindBlock extends PsiElement {

  @Nullable
  NaturalCipherClause getCipherClause();

  @NotNull
  List<NaturalCoupledClause> getCoupledClauseList();

  @NotNull
  List<NaturalExpression> getExpressionList();

  @Nullable
  NaturalFindCriteria getFindCriteria();

  @Nullable
  NaturalInSharedHold getInSharedHold();

  @Nullable
  NaturalMultiFetchClause getMultiFetchClause();

  @Nullable
  NaturalPasswordClause getPasswordClause();

  @Nullable
  NaturalRetainClause getRetainClause();

  @Nullable
  NaturalSkipRecordsInHold getSkipRecordsInHold();

  @Nullable
  NaturalSortedByClause getSortedByClause();

  @Nullable
  NaturalStartingWithIsn getStartingWithIsn();

  @NotNull
  List<NaturalStatement> getStatementList();

  @Nullable
  NaturalWhereClause getWhereClause();

}
