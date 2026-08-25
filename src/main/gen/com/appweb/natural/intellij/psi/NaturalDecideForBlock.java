// This is a generated file. Not intended for manual editing.
package com.appweb.natural.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NaturalDecideForBlock extends PsiElement {

  @Nullable
  NaturalWhenAllClause getWhenAllClause();

  @Nullable
  NaturalWhenAnyClause getWhenAnyClause();

  @NotNull
  List<NaturalWhenForClause> getWhenForClauseList();

  @Nullable
  NaturalWhenNoneClause getWhenNoneClause();

}
