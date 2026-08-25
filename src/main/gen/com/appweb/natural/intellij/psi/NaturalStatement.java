// This is a generated file. Not intended for manual editing.
package com.appweb.natural.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NaturalStatement extends PsiElement {

  @Nullable
  NaturalAssignmentStatement getAssignmentStatement();

  @Nullable
  NaturalAtBreakBlock getAtBreakBlock();

  @Nullable
  NaturalAtEndOfDataBlock getAtEndOfDataBlock();

  @Nullable
  NaturalAtEndOfPageBlock getAtEndOfPageBlock();

  @Nullable
  NaturalAtStartOfDataBlock getAtStartOfDataBlock();

  @Nullable
  NaturalAtTopPageBlock getAtTopPageBlock();

  @Nullable
  NaturalCallnatStatement getCallnatStatement();

  @NotNull
  List<NaturalDataType> getDataTypeList();

  @Nullable
  NaturalDecideForBlock getDecideForBlock();

  @Nullable
  NaturalDecideOnBlock getDecideOnBlock();

  @Nullable
  NaturalDefineSubroutineBlock getDefineSubroutineBlock();

  @NotNull
  List<NaturalExpression> getExpressionList();

  @Nullable
  NaturalFindBlock getFindBlock();

  @Nullable
  NaturalFindStatement getFindStatement();

  @Nullable
  NaturalForBlock getForBlock();

  @Nullable
  NaturalIfBlock getIfBlock();

  @Nullable
  NaturalIfNoRecordsFoundClause getIfNoRecordsFoundClause();

  @Nullable
  NaturalOnErrorBlock getOnErrorBlock();

  @Nullable
  NaturalReadBlock getReadBlock();

  @Nullable
  NaturalReadWorkBlock getReadWorkBlock();

  @Nullable
  NaturalRepeatBlock getRepeatBlock();

  @Nullable
  NaturalSetTimeStatement getSetTimeStatement();

  @Nullable
  NaturalSimpleStatements getSimpleStatements();

  @Nullable
  NaturalSortBlock getSortBlock();

  @NotNull
  List<NaturalUnaryExpr> getUnaryExprList();

}
