// This is a generated file. Not intended for manual editing.
package com.appweb.natural.intellij.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.appweb.natural.intellij.psi.NaturalTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.appweb.natural.intellij.psi.*;

public class NaturalSimpleStatementsImpl extends ASTWrapperPsiElement implements NaturalSimpleStatements {

  public NaturalSimpleStatementsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NaturalVisitor visitor) {
    visitor.visitSimpleStatements(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NaturalVisitor) accept((NaturalVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NaturalAcceptStatement getAcceptStatement() {
    return findChildByClass(NaturalAcceptStatement.class);
  }

  @Override
  @Nullable
  public NaturalAddStatement getAddStatement() {
    return findChildByClass(NaturalAddStatement.class);
  }

  @Override
  @Nullable
  public NaturalAssignStatement getAssignStatement() {
    return findChildByClass(NaturalAssignStatement.class);
  }

  @Override
  @Nullable
  public NaturalBackoutStatement getBackoutStatement() {
    return findChildByClass(NaturalBackoutStatement.class);
  }

  @Override
  @Nullable
  public NaturalCallStatement getCallStatement() {
    return findChildByClass(NaturalCallStatement.class);
  }

  @Override
  @Nullable
  public NaturalCallnatStatement getCallnatStatement() {
    return findChildByClass(NaturalCallnatStatement.class);
  }

  @Override
  @Nullable
  public NaturalCloseWorkStatement getCloseWorkStatement() {
    return findChildByClass(NaturalCloseWorkStatement.class);
  }

  @Override
  @Nullable
  public NaturalCompressStatement getCompressStatement() {
    return findChildByClass(NaturalCompressStatement.class);
  }

  @Override
  @Nullable
  public NaturalComputeStatement getComputeStatement() {
    return findChildByClass(NaturalComputeStatement.class);
  }

  @Override
  @Nullable
  public NaturalControlStatement getControlStatement() {
    return findChildByClass(NaturalControlStatement.class);
  }

  @Override
  @NotNull
  public List<NaturalDataType> getDataTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalDataType.class);
  }

  @Override
  @Nullable
  public NaturalDefinePrinterStatement getDefinePrinterStatement() {
    return findChildByClass(NaturalDefinePrinterStatement.class);
  }

  @Override
  @Nullable
  public NaturalDefineWindowStatement getDefineWindowStatement() {
    return findChildByClass(NaturalDefineWindowStatement.class);
  }

  @Override
  @Nullable
  public NaturalDefineWorkFileStatement getDefineWorkFileStatement() {
    return findChildByClass(NaturalDefineWorkFileStatement.class);
  }

  @Override
  @Nullable
  public NaturalDeleteStatement getDeleteStatement() {
    return findChildByClass(NaturalDeleteStatement.class);
  }

  @Override
  @Nullable
  public NaturalDisplayStatement getDisplayStatement() {
    return findChildByClass(NaturalDisplayStatement.class);
  }

  @Override
  @Nullable
  public NaturalDivideStatement getDivideStatement() {
    return findChildByClass(NaturalDivideStatement.class);
  }

  @Override
  @Nullable
  public NaturalDownloadStatement getDownloadStatement() {
    return findChildByClass(NaturalDownloadStatement.class);
  }

  @Override
  @Nullable
  public NaturalEjectStatement getEjectStatement() {
    return findChildByClass(NaturalEjectStatement.class);
  }

  @Override
  @Nullable
  public NaturalEndAllStatement getEndAllStatement() {
    return findChildByClass(NaturalEndAllStatement.class);
  }

  @Override
  @Nullable
  public NaturalEndTransactionStatement getEndTransactionStatement() {
    return findChildByClass(NaturalEndTransactionStatement.class);
  }

  @Override
  @Nullable
  public NaturalEscapeStatement getEscapeStatement() {
    return findChildByClass(NaturalEscapeStatement.class);
  }

  @Override
  @Nullable
  public NaturalExamineStatement getExamineStatement() {
    return findChildByClass(NaturalExamineStatement.class);
  }

  @Override
  @Nullable
  public NaturalExpandStatement getExpandStatement() {
    return findChildByClass(NaturalExpandStatement.class);
  }

  @Override
  @NotNull
  public List<NaturalExpression> getExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalExpression.class);
  }

  @Override
  @Nullable
  public NaturalFetchStatement getFetchStatement() {
    return findChildByClass(NaturalFetchStatement.class);
  }

  @Override
  @Nullable
  public NaturalFormatStatement getFormatStatement() {
    return findChildByClass(NaturalFormatStatement.class);
  }

  @Override
  @Nullable
  public NaturalGetStatement getGetStatement() {
    return findChildByClass(NaturalGetStatement.class);
  }

  @Override
  @Nullable
  public NaturalHistogramStatement getHistogramStatement() {
    return findChildByClass(NaturalHistogramStatement.class);
  }

  @Override
  @Nullable
  public NaturalIgnoreStatement getIgnoreStatement() {
    return findChildByClass(NaturalIgnoreStatement.class);
  }

  @Override
  @Nullable
  public NaturalIncdicStatement getIncdicStatement() {
    return findChildByClass(NaturalIncdicStatement.class);
  }

  @Override
  @Nullable
  public NaturalIncludeStatement getIncludeStatement() {
    return findChildByClass(NaturalIncludeStatement.class);
  }

  @Override
  @Nullable
  public NaturalInputStatement getInputStatement() {
    return findChildByClass(NaturalInputStatement.class);
  }

  @Override
  @Nullable
  public NaturalMarkStatement getMarkStatement() {
    return findChildByClass(NaturalMarkStatement.class);
  }

  @Override
  @Nullable
  public NaturalMoveStatement getMoveStatement() {
    return findChildByClass(NaturalMoveStatement.class);
  }

  @Override
  @Nullable
  public NaturalMultiplyStatement getMultiplyStatement() {
    return findChildByClass(NaturalMultiplyStatement.class);
  }

  @Override
  @Nullable
  public NaturalNewpageStatement getNewpageStatement() {
    return findChildByClass(NaturalNewpageStatement.class);
  }

  @Override
  @Nullable
  public NaturalOptionsStatement getOptionsStatement() {
    return findChildByClass(NaturalOptionsStatement.class);
  }

  @Override
  @Nullable
  public NaturalPerformStatement getPerformStatement() {
    return findChildByClass(NaturalPerformStatement.class);
  }

  @Override
  @Nullable
  public NaturalPrintStatement getPrintStatement() {
    return findChildByClass(NaturalPrintStatement.class);
  }

  @Override
  @Nullable
  public NaturalProcessCommandStatement getProcessCommandStatement() {
    return findChildByClass(NaturalProcessCommandStatement.class);
  }

  @Override
  @Nullable
  public NaturalReduceStatement getReduceStatement() {
    return findChildByClass(NaturalReduceStatement.class);
  }

  @Override
  @Nullable
  public NaturalReinputStatement getReinputStatement() {
    return findChildByClass(NaturalReinputStatement.class);
  }

  @Override
  @Nullable
  public NaturalRejectStatement getRejectStatement() {
    return findChildByClass(NaturalRejectStatement.class);
  }

  @Override
  @Nullable
  public NaturalReleaseStatement getReleaseStatement() {
    return findChildByClass(NaturalReleaseStatement.class);
  }

  @Override
  @Nullable
  public NaturalRequestDocumentStatement getRequestDocumentStatement() {
    return findChildByClass(NaturalRequestDocumentStatement.class);
  }

  @Override
  @Nullable
  public NaturalResetStatement getResetStatement() {
    return findChildByClass(NaturalResetStatement.class);
  }

  @Override
  @Nullable
  public NaturalResizeStatement getResizeStatement() {
    return findChildByClass(NaturalResizeStatement.class);
  }

  @Override
  @Nullable
  public NaturalRetryStatement getRetryStatement() {
    return findChildByClass(NaturalRetryStatement.class);
  }

  @Override
  @Nullable
  public NaturalRunStatement getRunStatement() {
    return findChildByClass(NaturalRunStatement.class);
  }

  @Override
  @Nullable
  public NaturalSeparateStatement getSeparateStatement() {
    return findChildByClass(NaturalSeparateStatement.class);
  }

  @Override
  @Nullable
  public NaturalSetControlStatement getSetControlStatement() {
    return findChildByClass(NaturalSetControlStatement.class);
  }

  @Override
  @Nullable
  public NaturalSetKeyStatement getSetKeyStatement() {
    return findChildByClass(NaturalSetKeyStatement.class);
  }

  @Override
  @Nullable
  public NaturalSkipStatement getSkipStatement() {
    return findChildByClass(NaturalSkipStatement.class);
  }

  @Override
  @Nullable
  public NaturalStackStatement getStackStatement() {
    return findChildByClass(NaturalStackStatement.class);
  }

  @Override
  @Nullable
  public NaturalStopStatement getStopStatement() {
    return findChildByClass(NaturalStopStatement.class);
  }

  @Override
  @Nullable
  public NaturalStoreStatement getStoreStatement() {
    return findChildByClass(NaturalStoreStatement.class);
  }

  @Override
  @Nullable
  public NaturalSubtractStatement getSubtractStatement() {
    return findChildByClass(NaturalSubtractStatement.class);
  }

  @Override
  @Nullable
  public NaturalTerminateStatement getTerminateStatement() {
    return findChildByClass(NaturalTerminateStatement.class);
  }

  @Override
  @Nullable
  public NaturalTranslateStatement getTranslateStatement() {
    return findChildByClass(NaturalTranslateStatement.class);
  }

  @Override
  @NotNull
  public List<NaturalUnaryExpr> getUnaryExprList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NaturalUnaryExpr.class);
  }

  @Override
  @Nullable
  public NaturalUpdateStatement getUpdateStatement() {
    return findChildByClass(NaturalUpdateStatement.class);
  }

  @Override
  @Nullable
  public NaturalWriteWorkFileStatement getWriteWorkFileStatement() {
    return findChildByClass(NaturalWriteWorkFileStatement.class);
  }

}
