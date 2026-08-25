// This is a generated file. Not intended for manual editing.
package com.appweb.natural.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NaturalSortBlock extends PsiElement {

  @Nullable
  NaturalSortGiveClause getSortGiveClause();

  @NotNull
  List<NaturalSortKey> getSortKeyList();

  @Nullable
  NaturalSortUsingClause getSortUsingClause();

  @NotNull
  List<NaturalStatement> getStatementList();

}
