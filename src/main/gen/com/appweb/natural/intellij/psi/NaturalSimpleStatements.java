// This is a generated file. Not intended for manual editing.
package com.appweb.natural.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NaturalSimpleStatements extends PsiElement {

  @Nullable
  NaturalAcceptStatement getAcceptStatement();

  @Nullable
  NaturalAddStatement getAddStatement();

  @Nullable
  NaturalAssignStatement getAssignStatement();

  @Nullable
  NaturalBackoutStatement getBackoutStatement();

  @Nullable
  NaturalCallStatement getCallStatement();

  @Nullable
  NaturalCallnatStatement getCallnatStatement();

  @Nullable
  NaturalCloseWorkStatement getCloseWorkStatement();

  @Nullable
  NaturalCompressStatement getCompressStatement();

  @Nullable
  NaturalComputeStatement getComputeStatement();

  @Nullable
  NaturalControlStatement getControlStatement();

  @NotNull
  List<NaturalDataType> getDataTypeList();

  @Nullable
  NaturalDefinePrinterStatement getDefinePrinterStatement();

  @Nullable
  NaturalDefineWindowStatement getDefineWindowStatement();

  @Nullable
  NaturalDefineWorkFileStatement getDefineWorkFileStatement();

  @Nullable
  NaturalDeleteStatement getDeleteStatement();

  @Nullable
  NaturalDisplayStatement getDisplayStatement();

  @Nullable
  NaturalDivideStatement getDivideStatement();

  @Nullable
  NaturalDownloadStatement getDownloadStatement();

  @Nullable
  NaturalEjectStatement getEjectStatement();

  @Nullable
  NaturalEndAllStatement getEndAllStatement();

  @Nullable
  NaturalEndTransactionStatement getEndTransactionStatement();

  @Nullable
  NaturalEscapeStatement getEscapeStatement();

  @Nullable
  NaturalExamineStatement getExamineStatement();

  @Nullable
  NaturalExpandStatement getExpandStatement();

  @NotNull
  List<NaturalExpression> getExpressionList();

  @Nullable
  NaturalFetchStatement getFetchStatement();

  @Nullable
  NaturalFormatStatement getFormatStatement();

  @Nullable
  NaturalGetStatement getGetStatement();

  @Nullable
  NaturalHistogramStatement getHistogramStatement();

  @Nullable
  NaturalIgnoreStatement getIgnoreStatement();

  @Nullable
  NaturalIncdicStatement getIncdicStatement();

  @Nullable
  NaturalIncludeStatement getIncludeStatement();

  @Nullable
  NaturalInputStatement getInputStatement();

  @Nullable
  NaturalMarkStatement getMarkStatement();

  @Nullable
  NaturalMoveStatement getMoveStatement();

  @Nullable
  NaturalMultiplyStatement getMultiplyStatement();

  @Nullable
  NaturalNewpageStatement getNewpageStatement();

  @Nullable
  NaturalOptionsStatement getOptionsStatement();

  @Nullable
  NaturalPerformStatement getPerformStatement();

  @Nullable
  NaturalPrintStatement getPrintStatement();

  @Nullable
  NaturalProcessCommandStatement getProcessCommandStatement();

  @Nullable
  NaturalReduceStatement getReduceStatement();

  @Nullable
  NaturalReinputStatement getReinputStatement();

  @Nullable
  NaturalRejectStatement getRejectStatement();

  @Nullable
  NaturalReleaseStatement getReleaseStatement();

  @Nullable
  NaturalRequestDocumentStatement getRequestDocumentStatement();

  @Nullable
  NaturalResetStatement getResetStatement();

  @Nullable
  NaturalResizeStatement getResizeStatement();

  @Nullable
  NaturalRetryStatement getRetryStatement();

  @Nullable
  NaturalRunStatement getRunStatement();

  @Nullable
  NaturalSeparateStatement getSeparateStatement();

  @Nullable
  NaturalSetControlStatement getSetControlStatement();

  @Nullable
  NaturalSetKeyStatement getSetKeyStatement();

  @Nullable
  NaturalSkipStatement getSkipStatement();

  @Nullable
  NaturalStackStatement getStackStatement();

  @Nullable
  NaturalStopStatement getStopStatement();

  @Nullable
  NaturalStoreStatement getStoreStatement();

  @Nullable
  NaturalSubtractStatement getSubtractStatement();

  @Nullable
  NaturalTerminateStatement getTerminateStatement();

  @Nullable
  NaturalTranslateStatement getTranslateStatement();

  @NotNull
  List<NaturalUnaryExpr> getUnaryExprList();

  @Nullable
  NaturalUpdateStatement getUpdateStatement();

  @Nullable
  NaturalWriteWorkFileStatement getWriteWorkFileStatement();

}
