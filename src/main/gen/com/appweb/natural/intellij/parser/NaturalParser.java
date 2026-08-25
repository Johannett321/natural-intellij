// This is a generated file. Not intended for manual editing.
package com.appweb.natural.intellij.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.appweb.natural.intellij.psi.NaturalTypes.*;
import static com.appweb.natural.intellij.parser.NaturalParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class NaturalParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType root_, PsiBuilder builder_) {
    parseLight(root_, builder_);
    return builder_.getTreeBuilt();
  }

  public void parseLight(IElementType root_, PsiBuilder builder_) {
    boolean result_;
    builder_ = adapt_builder_(root_, builder_, this, null);
    Marker marker_ = enter_section_(builder_, 0, _COLLAPSE_, null);
    result_ = parse_root_(root_, builder_);
    exit_section_(builder_, 0, marker_, root_, result_, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType root_, PsiBuilder builder_) {
    return parse_root_(root_, builder_, 0);
  }

  static boolean parse_root_(IElementType root_, PsiBuilder builder_, int level_) {
    return naturalFile(builder_, level_ + 1);
  }

  /* ********************************************************** */
  // KW_ACCEPT (KW_IF condition | condition)?
  public static boolean acceptStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "acceptStatement")) return false;
    if (!nextTokenIs(builder_, KW_ACCEPT)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ACCEPT_STATEMENT, null);
    result_ = consumeToken(builder_, KW_ACCEPT);
    pinned_ = result_; // pin = 1
    result_ = result_ && acceptStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_IF condition | condition)?
  private static boolean acceptStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "acceptStatement_1")) return false;
    acceptStatement_1_0(builder_, level_ + 1);
    return true;
  }

  // KW_IF condition | condition
  private static boolean acceptStatement_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "acceptStatement_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = acceptStatement_1_0_0(builder_, level_ + 1);
    if (!result_) result_ = condition(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_IF condition
  private static boolean acceptStatement_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "acceptStatement_1_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_IF);
    result_ = result_ && condition(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // mulExpr ((PLUS | MINUS) mulExpr)*
  public static boolean addExpr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "addExpr")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ADD_EXPR, "<add expr>");
    result_ = mulExpr(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && addExpr_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // ((PLUS | MINUS) mulExpr)*
  private static boolean addExpr_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "addExpr_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!addExpr_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "addExpr_1", pos_)) break;
    }
    return true;
  }

  // (PLUS | MINUS) mulExpr
  private static boolean addExpr_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "addExpr_1_0")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = addExpr_1_0_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && mulExpr(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // PLUS | MINUS
  private static boolean addExpr_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "addExpr_1_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, PLUS);
    if (!result_) result_ = consumeToken(builder_, MINUS);
    return result_;
  }

  /* ********************************************************** */
  // KW_ADD (!KW_TO !KW_GIVING !statement expression)+ (KW_TO expression)? (KW_GIVING expression)?
  public static boolean addStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "addStatement")) return false;
    if (!nextTokenIs(builder_, KW_ADD)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ADD_STATEMENT, null);
    result_ = consumeToken(builder_, KW_ADD);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, addStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, addStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && addStatement_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (!KW_TO !KW_GIVING !statement expression)+
  private static boolean addStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "addStatement_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = addStatement_1_0(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!addStatement_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "addStatement_1", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !KW_TO !KW_GIVING !statement expression
  private static boolean addStatement_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "addStatement_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = addStatement_1_0_0(builder_, level_ + 1);
    result_ = result_ && addStatement_1_0_1(builder_, level_ + 1);
    result_ = result_ && addStatement_1_0_2(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !KW_TO
  private static boolean addStatement_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "addStatement_1_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, KW_TO);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !KW_GIVING
  private static boolean addStatement_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "addStatement_1_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, KW_GIVING);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !statement
  private static boolean addStatement_1_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "addStatement_1_0_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (KW_TO expression)?
  private static boolean addStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "addStatement_2")) return false;
    addStatement_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_TO expression
  private static boolean addStatement_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "addStatement_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_TO);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_GIVING expression)?
  private static boolean addStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "addStatement_3")) return false;
    addStatement_3_0(builder_, level_ + 1);
    return true;
  }

  // KW_GIVING expression
  private static boolean addStatement_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "addStatement_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_GIVING);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // notCondition (KW_AND (notCondition | compOpRhs))*
  public static boolean andCondition(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "andCondition")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, AND_CONDITION, "<and condition>");
    result_ = notCondition(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && andCondition_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_AND (notCondition | compOpRhs))*
  private static boolean andCondition_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "andCondition_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!andCondition_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "andCondition_1", pos_)) break;
    }
    return true;
  }

  // KW_AND (notCondition | compOpRhs)
  private static boolean andCondition_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "andCondition_1_0")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = consumeToken(builder_, KW_AND);
    pinned_ = result_; // pin = 1
    result_ = result_ && andCondition_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // notCondition | compOpRhs
  private static boolean andCondition_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "andCondition_1_0_1")) return false;
    boolean result_;
    result_ = notCondition(builder_, level_ + 1);
    if (!result_) result_ = compOpRhs(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // MINUS? (NUMBER | IDENTIFIER | STAR) ((PLUS | MINUS) NUMBER)?
  static boolean arrayBound(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arrayBound")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = arrayBound_0(builder_, level_ + 1);
    result_ = result_ && arrayBound_1(builder_, level_ + 1);
    result_ = result_ && arrayBound_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // MINUS?
  private static boolean arrayBound_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arrayBound_0")) return false;
    consumeToken(builder_, MINUS);
    return true;
  }

  // NUMBER | IDENTIFIER | STAR
  private static boolean arrayBound_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arrayBound_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, STAR);
    return result_;
  }

  // ((PLUS | MINUS) NUMBER)?
  private static boolean arrayBound_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arrayBound_2")) return false;
    arrayBound_2_0(builder_, level_ + 1);
    return true;
  }

  // (PLUS | MINUS) NUMBER
  private static boolean arrayBound_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arrayBound_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = arrayBound_2_0_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, NUMBER);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // PLUS | MINUS
  private static boolean arrayBound_2_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arrayBound_2_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, PLUS);
    if (!result_) result_ = consumeToken(builder_, MINUS);
    return result_;
  }

  /* ********************************************************** */
  // arrayBound (COLON arrayBound)?
  static boolean arrayDimInType(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arrayDimInType")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = arrayBound(builder_, level_ + 1);
    result_ = result_ && arrayDimInType_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (COLON arrayBound)?
  private static boolean arrayDimInType_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arrayDimInType_1")) return false;
    arrayDimInType_1_0(builder_, level_ + 1);
    return true;
  }

  // COLON arrayBound
  private static boolean arrayDimInType_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arrayDimInType_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COLON);
    result_ = result_ && arrayBound(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // expression (":" (expression | STAR))?
  public static boolean arrayDimension(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arrayDimension")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ARRAY_DIMENSION, "<array dimension>");
    result_ = expression(builder_, level_ + 1);
    result_ = result_ && arrayDimension_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (":" (expression | STAR))?
  private static boolean arrayDimension_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arrayDimension_1")) return false;
    arrayDimension_1_0(builder_, level_ + 1);
    return true;
  }

  // ":" (expression | STAR)
  private static boolean arrayDimension_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arrayDimension_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COLON);
    result_ = result_ && arrayDimension_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // expression | STAR
  private static boolean arrayDimension_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arrayDimension_1_0_1")) return false;
    boolean result_;
    result_ = expression(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, STAR);
    return result_;
  }

  /* ********************************************************** */
  // "(" arrayDimension ("," arrayDimension)* ")"
  public static boolean arraySpec(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arraySpec")) return false;
    if (!nextTokenIs(builder_, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && arrayDimension(builder_, level_ + 1);
    result_ = result_ && arraySpec_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, ARRAY_SPEC, result_);
    return result_;
  }

  // ("," arrayDimension)*
  private static boolean arraySpec_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arraySpec_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!arraySpec_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "arraySpec_2", pos_)) break;
    }
    return true;
  }

  // "," arrayDimension
  private static boolean arraySpec_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arraySpec_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && arrayDimension(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_ASSIGN expression (ASSIGN_OP | EQ_OP) expression
  public static boolean assignStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignStatement")) return false;
    if (!nextTokenIs(builder_, KW_ASSIGN)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ASSIGN_STATEMENT, null);
    result_ = consumeToken(builder_, KW_ASSIGN);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, expression(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, assignStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && expression(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // ASSIGN_OP | EQ_OP
  private static boolean assignStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignStatement_2")) return false;
    boolean result_;
    result_ = consumeToken(builder_, ASSIGN_OP);
    if (!result_) result_ = consumeToken(builder_, EQ_OP);
    return result_;
  }

  /* ********************************************************** */
  // (systemVarRef | variableRef) ASSIGN_OP (assignmentStatement | sessionParameterSpec | expression)
  public static boolean assignmentStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignmentStatement")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ASSIGNMENT_STATEMENT, "<assignment statement>");
    result_ = assignmentStatement_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ASSIGN_OP);
    pinned_ = result_; // pin = 2
    result_ = result_ && assignmentStatement_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // systemVarRef | variableRef
  private static boolean assignmentStatement_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignmentStatement_0")) return false;
    boolean result_;
    result_ = systemVarRef(builder_, level_ + 1);
    if (!result_) result_ = variableRef(builder_, level_ + 1);
    return result_;
  }

  // assignmentStatement | sessionParameterSpec | expression
  private static boolean assignmentStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignmentStatement_2")) return false;
    boolean result_;
    result_ = assignmentStatement(builder_, level_ + 1);
    if (!result_) result_ = sessionParameterSpec(builder_, level_ + 1);
    if (!result_) result_ = expression(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // KW_AT KW_BREAK KW_OF? variableRef (SLASH NUMBER SLASH)? statement* (KW_END_BREAK | KW_END_ALL)?
  public static boolean atBreakBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atBreakBlock")) return false;
    if (!nextTokenIs(builder_, KW_AT)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, AT_BREAK_BLOCK, null);
    result_ = consumeTokens(builder_, 2, KW_AT, KW_BREAK);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, atBreakBlock_2(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, variableRef(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, atBreakBlock_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, atBreakBlock_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && atBreakBlock_6(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_OF?
  private static boolean atBreakBlock_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atBreakBlock_2")) return false;
    consumeToken(builder_, KW_OF);
    return true;
  }

  // (SLASH NUMBER SLASH)?
  private static boolean atBreakBlock_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atBreakBlock_4")) return false;
    atBreakBlock_4_0(builder_, level_ + 1);
    return true;
  }

  // SLASH NUMBER SLASH
  private static boolean atBreakBlock_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atBreakBlock_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, SLASH, NUMBER, SLASH);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // statement*
  private static boolean atBreakBlock_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atBreakBlock_5")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "atBreakBlock_5", pos_)) break;
    }
    return true;
  }

  // (KW_END_BREAK | KW_END_ALL)?
  private static boolean atBreakBlock_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atBreakBlock_6")) return false;
    atBreakBlock_6_0(builder_, level_ + 1);
    return true;
  }

  // KW_END_BREAK | KW_END_ALL
  private static boolean atBreakBlock_6_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atBreakBlock_6_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_END_BREAK);
    if (!result_) result_ = consumeToken(builder_, KW_END_ALL);
    return result_;
  }

  /* ********************************************************** */
  // KW_AT? KW_END KW_OF? (KW_DATA | KW_FILE) statement* (KW_END_ENDDATA | KW_END_ENDFILE)
  public static boolean atEndOfDataBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfDataBlock")) return false;
    if (!nextTokenIs(builder_, "<at end of data block>", KW_AT, KW_END)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, AT_END_OF_DATA_BLOCK, "<at end of data block>");
    result_ = atEndOfDataBlock_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_END);
    result_ = result_ && atEndOfDataBlock_2(builder_, level_ + 1);
    result_ = result_ && atEndOfDataBlock_3(builder_, level_ + 1);
    pinned_ = result_; // pin = 4
    result_ = result_ && report_error_(builder_, atEndOfDataBlock_4(builder_, level_ + 1));
    result_ = pinned_ && atEndOfDataBlock_5(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_AT?
  private static boolean atEndOfDataBlock_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfDataBlock_0")) return false;
    consumeToken(builder_, KW_AT);
    return true;
  }

  // KW_OF?
  private static boolean atEndOfDataBlock_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfDataBlock_2")) return false;
    consumeToken(builder_, KW_OF);
    return true;
  }

  // KW_DATA | KW_FILE
  private static boolean atEndOfDataBlock_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfDataBlock_3")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_DATA);
    if (!result_) result_ = consumeToken(builder_, KW_FILE);
    return result_;
  }

  // statement*
  private static boolean atEndOfDataBlock_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfDataBlock_4")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "atEndOfDataBlock_4", pos_)) break;
    }
    return true;
  }

  // KW_END_ENDDATA | KW_END_ENDFILE
  private static boolean atEndOfDataBlock_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfDataBlock_5")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_END_ENDDATA);
    if (!result_) result_ = consumeToken(builder_, KW_END_ENDFILE);
    return result_;
  }

  /* ********************************************************** */
  // KW_AT? KW_END KW_OF? KW_PAGE (LPAREN (NUMBER | IDENTIFIER DOT?) RPAREN)? statement* KW_END_ENDPAGE
  public static boolean atEndOfPageBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfPageBlock")) return false;
    if (!nextTokenIs(builder_, "<at end of page block>", KW_AT, KW_END)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, AT_END_OF_PAGE_BLOCK, "<at end of page block>");
    result_ = atEndOfPageBlock_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_END);
    result_ = result_ && atEndOfPageBlock_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_PAGE);
    pinned_ = result_; // pin = 4
    result_ = result_ && report_error_(builder_, atEndOfPageBlock_4(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, atEndOfPageBlock_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && consumeToken(builder_, KW_END_ENDPAGE) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_AT?
  private static boolean atEndOfPageBlock_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfPageBlock_0")) return false;
    consumeToken(builder_, KW_AT);
    return true;
  }

  // KW_OF?
  private static boolean atEndOfPageBlock_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfPageBlock_2")) return false;
    consumeToken(builder_, KW_OF);
    return true;
  }

  // (LPAREN (NUMBER | IDENTIFIER DOT?) RPAREN)?
  private static boolean atEndOfPageBlock_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfPageBlock_4")) return false;
    atEndOfPageBlock_4_0(builder_, level_ + 1);
    return true;
  }

  // LPAREN (NUMBER | IDENTIFIER DOT?) RPAREN
  private static boolean atEndOfPageBlock_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfPageBlock_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && atEndOfPageBlock_4_0_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // NUMBER | IDENTIFIER DOT?
  private static boolean atEndOfPageBlock_4_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfPageBlock_4_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = atEndOfPageBlock_4_0_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IDENTIFIER DOT?
  private static boolean atEndOfPageBlock_4_0_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfPageBlock_4_0_1_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IDENTIFIER);
    result_ = result_ && atEndOfPageBlock_4_0_1_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // DOT?
  private static boolean atEndOfPageBlock_4_0_1_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfPageBlock_4_0_1_1_1")) return false;
    consumeToken(builder_, DOT);
    return true;
  }

  // statement*
  private static boolean atEndOfPageBlock_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atEndOfPageBlock_5")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "atEndOfPageBlock_5", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_AT? KW_START KW_OF? KW_DATA statementRef? statement* KW_END_START
  public static boolean atStartOfDataBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atStartOfDataBlock")) return false;
    if (!nextTokenIs(builder_, "<at start of data block>", KW_AT, KW_START)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, AT_START_OF_DATA_BLOCK, "<at start of data block>");
    result_ = atStartOfDataBlock_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_START);
    result_ = result_ && atStartOfDataBlock_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_DATA);
    pinned_ = result_; // pin = 4
    result_ = result_ && report_error_(builder_, atStartOfDataBlock_4(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, atStartOfDataBlock_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && consumeToken(builder_, KW_END_START) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_AT?
  private static boolean atStartOfDataBlock_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atStartOfDataBlock_0")) return false;
    consumeToken(builder_, KW_AT);
    return true;
  }

  // KW_OF?
  private static boolean atStartOfDataBlock_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atStartOfDataBlock_2")) return false;
    consumeToken(builder_, KW_OF);
    return true;
  }

  // statementRef?
  private static boolean atStartOfDataBlock_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atStartOfDataBlock_4")) return false;
    statementRef(builder_, level_ + 1);
    return true;
  }

  // statement*
  private static boolean atStartOfDataBlock_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atStartOfDataBlock_5")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "atStartOfDataBlock_5", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_AT? KW_TOP KW_OF? KW_PAGE (LPAREN NUMBER RPAREN)? statement* KW_END_TOPPAGE
  public static boolean atTopPageBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atTopPageBlock")) return false;
    if (!nextTokenIs(builder_, "<at top page block>", KW_AT, KW_TOP)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, AT_TOP_PAGE_BLOCK, "<at top page block>");
    result_ = atTopPageBlock_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_TOP);
    result_ = result_ && atTopPageBlock_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_PAGE);
    pinned_ = result_; // pin = 4
    result_ = result_ && report_error_(builder_, atTopPageBlock_4(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, atTopPageBlock_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && consumeToken(builder_, KW_END_TOPPAGE) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_AT?
  private static boolean atTopPageBlock_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atTopPageBlock_0")) return false;
    consumeToken(builder_, KW_AT);
    return true;
  }

  // KW_OF?
  private static boolean atTopPageBlock_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atTopPageBlock_2")) return false;
    consumeToken(builder_, KW_OF);
    return true;
  }

  // (LPAREN NUMBER RPAREN)?
  private static boolean atTopPageBlock_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atTopPageBlock_4")) return false;
    atTopPageBlock_4_0(builder_, level_ + 1);
    return true;
  }

  // LPAREN NUMBER RPAREN
  private static boolean atTopPageBlock_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atTopPageBlock_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, LPAREN, NUMBER, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // statement*
  private static boolean atTopPageBlock_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "atTopPageBlock_5")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "atTopPageBlock_5", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_BACKOUT KW_TRANSACTION?
  public static boolean backoutStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "backoutStatement")) return false;
    if (!nextTokenIs(builder_, KW_BACKOUT)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, BACKOUT_STATEMENT, null);
    result_ = consumeToken(builder_, KW_BACKOUT);
    pinned_ = result_; // pin = 1
    result_ = result_ && backoutStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_TRANSACTION?
  private static boolean backoutStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "backoutStatement_1")) return false;
    consumeToken(builder_, KW_TRANSACTION);
    return true;
  }

  /* ********************************************************** */
  // (IDENTIFIER | keywordAsVarPrefix | KW_FIND | KW_READ | KW_REPEAT | KW_FOR | KW_PERFORM | KW_GET | KW_SORT | KW_STORE | KW_HISTOGRAM | KW_GT | KW_LT | KW_GE | KW_LE | KW_EQ | KW_NE) DOT
  static boolean blockLabel(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "blockLabel")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = blockLabel_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, DOT);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IDENTIFIER | keywordAsVarPrefix | KW_FIND | KW_READ | KW_REPEAT | KW_FOR | KW_PERFORM | KW_GET | KW_SORT | KW_STORE | KW_HISTOGRAM | KW_GT | KW_LT | KW_GE | KW_LE | KW_EQ | KW_NE
  private static boolean blockLabel_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "blockLabel_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = keywordAsVarPrefix(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, KW_FIND);
    if (!result_) result_ = consumeToken(builder_, KW_READ);
    if (!result_) result_ = consumeToken(builder_, KW_REPEAT);
    if (!result_) result_ = consumeToken(builder_, KW_FOR);
    if (!result_) result_ = consumeToken(builder_, KW_PERFORM);
    if (!result_) result_ = consumeToken(builder_, KW_GET);
    if (!result_) result_ = consumeToken(builder_, KW_SORT);
    if (!result_) result_ = consumeToken(builder_, KW_STORE);
    if (!result_) result_ = consumeToken(builder_, KW_HISTOGRAM);
    if (!result_) result_ = consumeToken(builder_, KW_GT);
    if (!result_) result_ = consumeToken(builder_, KW_LT);
    if (!result_) result_ = consumeToken(builder_, KW_GE);
    if (!result_) result_ = consumeToken(builder_, KW_LE);
    if (!result_) result_ = consumeToken(builder_, KW_EQ);
    if (!result_) result_ = consumeToken(builder_, KW_NE);
    return result_;
  }

  /* ********************************************************** */
  // KW_BREAK KW_OF? expression
  public static boolean breakCondition(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "breakCondition")) return false;
    if (!nextTokenIs(builder_, KW_BREAK)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, BREAK_CONDITION, null);
    result_ = consumeToken(builder_, KW_BREAK);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, breakCondition_1(builder_, level_ + 1));
    result_ = pinned_ && expression(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_OF?
  private static boolean breakCondition_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "breakCondition_1")) return false;
    consumeToken(builder_, KW_OF);
    return true;
  }

  /* ********************************************************** */
  // !statement expression
  static boolean callArg(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "callArg")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = callArg_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean callArg_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "callArg_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // KW_CALL expression KW_USING? callArg*
  public static boolean callStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "callStatement")) return false;
    if (!nextTokenIs(builder_, KW_CALL)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, CALL_STATEMENT, null);
    result_ = consumeToken(builder_, KW_CALL);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, expression(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, callStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && callStatement_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_USING?
  private static boolean callStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "callStatement_2")) return false;
    consumeToken(builder_, KW_USING);
    return true;
  }

  // callArg*
  private static boolean callStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "callStatement_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!callArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "callStatement_3", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !statement expression
  static boolean callnatParam(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "callnatParam")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = callnatParam_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean callnatParam_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "callnatParam_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // KW_CALLNAT stringOrIdentifier (KW_USING? callnatParam+)? (KW_GIVING expression)?
  public static boolean callnatStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "callnatStatement")) return false;
    if (!nextTokenIs(builder_, KW_CALLNAT)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, CALLNAT_STATEMENT, null);
    result_ = consumeToken(builder_, KW_CALLNAT);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, stringOrIdentifier(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, callnatStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && callnatStatement_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_USING? callnatParam+)?
  private static boolean callnatStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "callnatStatement_2")) return false;
    callnatStatement_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_USING? callnatParam+
  private static boolean callnatStatement_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "callnatStatement_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = callnatStatement_2_0_0(builder_, level_ + 1);
    result_ = result_ && callnatStatement_2_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_USING?
  private static boolean callnatStatement_2_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "callnatStatement_2_0_0")) return false;
    consumeToken(builder_, KW_USING);
    return true;
  }

  // callnatParam+
  private static boolean callnatStatement_2_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "callnatStatement_2_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = callnatParam(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!callnatParam(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "callnatStatement_2_0_1", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_GIVING expression)?
  private static boolean callnatStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "callnatStatement_3")) return false;
    callnatStatement_3_0(builder_, level_ + 1);
    return true;
  }

  // KW_GIVING expression
  private static boolean callnatStatement_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "callnatStatement_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_GIVING);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_CIPHER EQ_OP expression
  public static boolean cipherClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "cipherClause")) return false;
    if (!nextTokenIs(builder_, KW_CIPHER)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, CIPHER_CLAUSE, null);
    result_ = consumeTokens(builder_, 1, KW_CIPHER, EQ_OP);
    pinned_ = result_; // pin = 1
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // KW_CLOSE KW_WORK? KW_FILE? expression
  public static boolean closeWorkStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "closeWorkStatement")) return false;
    if (!nextTokenIs(builder_, KW_CLOSE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, CLOSE_WORK_STATEMENT, null);
    result_ = consumeToken(builder_, KW_CLOSE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, closeWorkStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, closeWorkStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && expression(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_WORK?
  private static boolean closeWorkStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "closeWorkStatement_1")) return false;
    consumeToken(builder_, KW_WORK);
    return true;
  }

  // KW_FILE?
  private static boolean closeWorkStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "closeWorkStatement_2")) return false;
    consumeToken(builder_, KW_FILE);
    return true;
  }

  /* ********************************************************** */
  // LE_OP | KW_EQUALS (KW_TO)? | KW_LESS KW_THAN | KW_GREATER KW_THAN
  //   | EQ_OP | NEQ_OP | LT_OP | GT_OP | GE_OP
  //   | (KW_GT | KW_LT | KW_GE | KW_LE | KW_EQ | KW_NE) !DOT
  //   | KW_NOT (EQ_OP | NEQ_OP | LT_OP | GT_OP | LE_OP | GE_OP | KW_EQUAL | KW_EQUALS | KW_LESS | KW_GREATER)
  public static boolean compOp(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOp")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMP_OP, "<comp op>");
    result_ = consumeToken(builder_, LE_OP);
    if (!result_) result_ = compOp_1(builder_, level_ + 1);
    if (!result_) result_ = parseTokens(builder_, 0, KW_LESS, KW_THAN);
    if (!result_) result_ = parseTokens(builder_, 0, KW_GREATER, KW_THAN);
    if (!result_) result_ = consumeToken(builder_, EQ_OP);
    if (!result_) result_ = consumeToken(builder_, NEQ_OP);
    if (!result_) result_ = consumeToken(builder_, LT_OP);
    if (!result_) result_ = consumeToken(builder_, GT_OP);
    if (!result_) result_ = consumeToken(builder_, GE_OP);
    if (!result_) result_ = compOp_9(builder_, level_ + 1);
    if (!result_) result_ = compOp_10(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_EQUALS (KW_TO)?
  private static boolean compOp_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOp_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_EQUALS);
    result_ = result_ && compOp_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_TO)?
  private static boolean compOp_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOp_1_1")) return false;
    consumeToken(builder_, KW_TO);
    return true;
  }

  // (KW_GT | KW_LT | KW_GE | KW_LE | KW_EQ | KW_NE) !DOT
  private static boolean compOp_9(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOp_9")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = compOp_9_0(builder_, level_ + 1);
    result_ = result_ && compOp_9_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_GT | KW_LT | KW_GE | KW_LE | KW_EQ | KW_NE
  private static boolean compOp_9_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOp_9_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_GT);
    if (!result_) result_ = consumeToken(builder_, KW_LT);
    if (!result_) result_ = consumeToken(builder_, KW_GE);
    if (!result_) result_ = consumeToken(builder_, KW_LE);
    if (!result_) result_ = consumeToken(builder_, KW_EQ);
    if (!result_) result_ = consumeToken(builder_, KW_NE);
    return result_;
  }

  // !DOT
  private static boolean compOp_9_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOp_9_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, DOT);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_NOT (EQ_OP | NEQ_OP | LT_OP | GT_OP | LE_OP | GE_OP | KW_EQUAL | KW_EQUALS | KW_LESS | KW_GREATER)
  private static boolean compOp_10(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOp_10")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_NOT);
    result_ = result_ && compOp_10_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // EQ_OP | NEQ_OP | LT_OP | GT_OP | LE_OP | GE_OP | KW_EQUAL | KW_EQUALS | KW_LESS | KW_GREATER
  private static boolean compOp_10_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOp_10_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, EQ_OP);
    if (!result_) result_ = consumeToken(builder_, NEQ_OP);
    if (!result_) result_ = consumeToken(builder_, LT_OP);
    if (!result_) result_ = consumeToken(builder_, GT_OP);
    if (!result_) result_ = consumeToken(builder_, LE_OP);
    if (!result_) result_ = consumeToken(builder_, GE_OP);
    if (!result_) result_ = consumeToken(builder_, KW_EQUAL);
    if (!result_) result_ = consumeToken(builder_, KW_EQUALS);
    if (!result_) result_ = consumeToken(builder_, KW_LESS);
    if (!result_) result_ = consumeToken(builder_, KW_GREATER);
    return result_;
  }

  /* ********************************************************** */
  // compOp addExpr (KW_THRU addExpr)? (KW_BUT KW_NOT addExpr (KW_THRU addExpr)?)? (KW_AND (andCondition | compOpRhs))*
  static boolean compOpRhs(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOpRhs")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = compOp(builder_, level_ + 1);
    result_ = result_ && addExpr(builder_, level_ + 1);
    result_ = result_ && compOpRhs_2(builder_, level_ + 1);
    result_ = result_ && compOpRhs_3(builder_, level_ + 1);
    result_ = result_ && compOpRhs_4(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_THRU addExpr)?
  private static boolean compOpRhs_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOpRhs_2")) return false;
    compOpRhs_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_THRU addExpr
  private static boolean compOpRhs_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOpRhs_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_THRU);
    result_ = result_ && addExpr(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_BUT KW_NOT addExpr (KW_THRU addExpr)?)?
  private static boolean compOpRhs_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOpRhs_3")) return false;
    compOpRhs_3_0(builder_, level_ + 1);
    return true;
  }

  // KW_BUT KW_NOT addExpr (KW_THRU addExpr)?
  private static boolean compOpRhs_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOpRhs_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_BUT, KW_NOT);
    result_ = result_ && addExpr(builder_, level_ + 1);
    result_ = result_ && compOpRhs_3_0_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_THRU addExpr)?
  private static boolean compOpRhs_3_0_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOpRhs_3_0_3")) return false;
    compOpRhs_3_0_3_0(builder_, level_ + 1);
    return true;
  }

  // KW_THRU addExpr
  private static boolean compOpRhs_3_0_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOpRhs_3_0_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_THRU);
    result_ = result_ && addExpr(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_AND (andCondition | compOpRhs))*
  private static boolean compOpRhs_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOpRhs_4")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!compOpRhs_4_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "compOpRhs_4", pos_)) break;
    }
    return true;
  }

  // KW_AND (andCondition | compOpRhs)
  private static boolean compOpRhs_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOpRhs_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_AND);
    result_ = result_ && compOpRhs_4_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // andCondition | compOpRhs
  private static boolean compOpRhs_4_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compOpRhs_4_0_1")) return false;
    boolean result_;
    result_ = andCondition(builder_, level_ + 1);
    if (!result_) result_ = compOpRhs(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // addExpr ((compOp addExpr (KW_THRU addExpr)? (KW_BUT KW_NOT addExpr (KW_THRU addExpr)?)? | notModifiedCheck | notUniqueCheck) | KW_MODIFIED | KW_UNIQUE | KW_SPECIFIED | typeCheck)?
  public static boolean comparisonExpr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comparisonExpr")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMPARISON_EXPR, "<comparison expr>");
    result_ = addExpr(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && comparisonExpr_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // ((compOp addExpr (KW_THRU addExpr)? (KW_BUT KW_NOT addExpr (KW_THRU addExpr)?)? | notModifiedCheck | notUniqueCheck) | KW_MODIFIED | KW_UNIQUE | KW_SPECIFIED | typeCheck)?
  private static boolean comparisonExpr_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comparisonExpr_1")) return false;
    comparisonExpr_1_0(builder_, level_ + 1);
    return true;
  }

  // (compOp addExpr (KW_THRU addExpr)? (KW_BUT KW_NOT addExpr (KW_THRU addExpr)?)? | notModifiedCheck | notUniqueCheck) | KW_MODIFIED | KW_UNIQUE | KW_SPECIFIED | typeCheck
  private static boolean comparisonExpr_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comparisonExpr_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = comparisonExpr_1_0_0(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, KW_MODIFIED);
    if (!result_) result_ = consumeToken(builder_, KW_UNIQUE);
    if (!result_) result_ = consumeToken(builder_, KW_SPECIFIED);
    if (!result_) result_ = typeCheck(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // compOp addExpr (KW_THRU addExpr)? (KW_BUT KW_NOT addExpr (KW_THRU addExpr)?)? | notModifiedCheck | notUniqueCheck
  private static boolean comparisonExpr_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comparisonExpr_1_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = comparisonExpr_1_0_0_0(builder_, level_ + 1);
    if (!result_) result_ = notModifiedCheck(builder_, level_ + 1);
    if (!result_) result_ = notUniqueCheck(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // compOp addExpr (KW_THRU addExpr)? (KW_BUT KW_NOT addExpr (KW_THRU addExpr)?)?
  private static boolean comparisonExpr_1_0_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comparisonExpr_1_0_0_0")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = compOp(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, addExpr(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, comparisonExpr_1_0_0_0_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && comparisonExpr_1_0_0_0_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_THRU addExpr)?
  private static boolean comparisonExpr_1_0_0_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comparisonExpr_1_0_0_0_2")) return false;
    comparisonExpr_1_0_0_0_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_THRU addExpr
  private static boolean comparisonExpr_1_0_0_0_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comparisonExpr_1_0_0_0_2_0")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = consumeToken(builder_, KW_THRU);
    pinned_ = result_; // pin = 1
    result_ = result_ && addExpr(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_BUT KW_NOT addExpr (KW_THRU addExpr)?)?
  private static boolean comparisonExpr_1_0_0_0_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comparisonExpr_1_0_0_0_3")) return false;
    comparisonExpr_1_0_0_0_3_0(builder_, level_ + 1);
    return true;
  }

  // KW_BUT KW_NOT addExpr (KW_THRU addExpr)?
  private static boolean comparisonExpr_1_0_0_0_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comparisonExpr_1_0_0_0_3_0")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = consumeTokens(builder_, 1, KW_BUT, KW_NOT);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, addExpr(builder_, level_ + 1));
    result_ = pinned_ && comparisonExpr_1_0_0_0_3_0_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_THRU addExpr)?
  private static boolean comparisonExpr_1_0_0_0_3_0_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comparisonExpr_1_0_0_0_3_0_3")) return false;
    comparisonExpr_1_0_0_0_3_0_3_0(builder_, level_ + 1);
    return true;
  }

  // KW_THRU addExpr
  private static boolean comparisonExpr_1_0_0_0_3_0_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comparisonExpr_1_0_0_0_3_0_3_0")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = consumeToken(builder_, KW_THRU);
    pinned_ = result_; // pin = 1
    result_ = result_ && addExpr(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // !statement (KW_WITH (KW_ALL KW_SPACES | KW_DELIMITER expression? | expression) | KW_LEAVING KW_NO KW_SPACE?)
  static boolean compressOption(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compressOption")) return false;
    if (!nextTokenIs(builder_, "", KW_LEAVING, KW_WITH)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = compressOption_0(builder_, level_ + 1);
    result_ = result_ && compressOption_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean compressOption_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compressOption_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_WITH (KW_ALL KW_SPACES | KW_DELIMITER expression? | expression) | KW_LEAVING KW_NO KW_SPACE?
  private static boolean compressOption_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compressOption_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = compressOption_1_0(builder_, level_ + 1);
    if (!result_) result_ = compressOption_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_WITH (KW_ALL KW_SPACES | KW_DELIMITER expression? | expression)
  private static boolean compressOption_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compressOption_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_WITH);
    result_ = result_ && compressOption_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_ALL KW_SPACES | KW_DELIMITER expression? | expression
  private static boolean compressOption_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compressOption_1_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = parseTokens(builder_, 0, KW_ALL, KW_SPACES);
    if (!result_) result_ = compressOption_1_0_1_1(builder_, level_ + 1);
    if (!result_) result_ = expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_DELIMITER expression?
  private static boolean compressOption_1_0_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compressOption_1_0_1_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_DELIMITER);
    result_ = result_ && compressOption_1_0_1_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // expression?
  private static boolean compressOption_1_0_1_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compressOption_1_0_1_1_1")) return false;
    expression(builder_, level_ + 1);
    return true;
  }

  // KW_LEAVING KW_NO KW_SPACE?
  private static boolean compressOption_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compressOption_1_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_LEAVING, KW_NO);
    result_ = result_ && compressOption_1_1_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_SPACE?
  private static boolean compressOption_1_1_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compressOption_1_1_2")) return false;
    consumeToken(builder_, KW_SPACE);
    return true;
  }

  /* ********************************************************** */
  // KW_COMPRESS KW_FULL? expression+ (KW_INTO | KW_TO) expression compressOption*
  public static boolean compressStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compressStatement")) return false;
    if (!nextTokenIs(builder_, KW_COMPRESS)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMPRESS_STATEMENT, null);
    result_ = consumeToken(builder_, KW_COMPRESS);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, compressStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, compressStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, compressStatement_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, expression(builder_, level_ + 1)) && result_;
    result_ = pinned_ && compressStatement_5(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_FULL?
  private static boolean compressStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compressStatement_1")) return false;
    consumeToken(builder_, KW_FULL);
    return true;
  }

  // expression+
  private static boolean compressStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compressStatement_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!expression(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "compressStatement_2", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_INTO | KW_TO
  private static boolean compressStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compressStatement_3")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_INTO);
    if (!result_) result_ = consumeToken(builder_, KW_TO);
    return result_;
  }

  // compressOption*
  private static boolean compressStatement_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compressStatement_5")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!compressOption(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "compressStatement_5", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_COMPUTE KW_ROUNDED? expression (ASSIGN_OP | EQ_OP) expression
  public static boolean computeStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "computeStatement")) return false;
    if (!nextTokenIs(builder_, KW_COMPUTE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMPUTE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_COMPUTE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, computeStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, expression(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, computeStatement_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && expression(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_ROUNDED?
  private static boolean computeStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "computeStatement_1")) return false;
    consumeToken(builder_, KW_ROUNDED);
    return true;
  }

  // ASSIGN_OP | EQ_OP
  private static boolean computeStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "computeStatement_3")) return false;
    boolean result_;
    result_ = consumeToken(builder_, ASSIGN_OP);
    if (!result_) result_ = consumeToken(builder_, EQ_OP);
    return result_;
  }

  /* ********************************************************** */
  // orCondition
  public static boolean condition(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "condition")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, CONDITION, "<condition>");
    result_ = orCondition(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // KW_CONTROL writeArg*
  public static boolean controlStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "controlStatement")) return false;
    if (!nextTokenIs(builder_, KW_CONTROL)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, CONTROL_STATEMENT, null);
    result_ = consumeToken(builder_, KW_CONTROL);
    pinned_ = result_; // pin = 1
    result_ = result_ && controlStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // writeArg*
  private static boolean controlStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "controlStatement_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "controlStatement_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (KW_AND | KW_OR)? KW_COUPLED KW_TO? KW_FILE? identifier
  //                   (KW_VIA descriptor (EQ_OP | KW_EQUAL KW_TO?)? descriptor)?
  //                   KW_WITH? findCriteria?
  public static boolean coupledClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "coupledClause")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COUPLED_CLAUSE, "<coupled clause>");
    result_ = coupledClause_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_COUPLED);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, coupledClause_2(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, coupledClause_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, identifier(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, coupledClause_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, coupledClause_6(builder_, level_ + 1)) && result_;
    result_ = pinned_ && coupledClause_7(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_AND | KW_OR)?
  private static boolean coupledClause_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "coupledClause_0")) return false;
    coupledClause_0_0(builder_, level_ + 1);
    return true;
  }

  // KW_AND | KW_OR
  private static boolean coupledClause_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "coupledClause_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_AND);
    if (!result_) result_ = consumeToken(builder_, KW_OR);
    return result_;
  }

  // KW_TO?
  private static boolean coupledClause_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "coupledClause_2")) return false;
    consumeToken(builder_, KW_TO);
    return true;
  }

  // KW_FILE?
  private static boolean coupledClause_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "coupledClause_3")) return false;
    consumeToken(builder_, KW_FILE);
    return true;
  }

  // (KW_VIA descriptor (EQ_OP | KW_EQUAL KW_TO?)? descriptor)?
  private static boolean coupledClause_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "coupledClause_5")) return false;
    coupledClause_5_0(builder_, level_ + 1);
    return true;
  }

  // KW_VIA descriptor (EQ_OP | KW_EQUAL KW_TO?)? descriptor
  private static boolean coupledClause_5_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "coupledClause_5_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_VIA);
    result_ = result_ && descriptor(builder_, level_ + 1);
    result_ = result_ && coupledClause_5_0_2(builder_, level_ + 1);
    result_ = result_ && descriptor(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (EQ_OP | KW_EQUAL KW_TO?)?
  private static boolean coupledClause_5_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "coupledClause_5_0_2")) return false;
    coupledClause_5_0_2_0(builder_, level_ + 1);
    return true;
  }

  // EQ_OP | KW_EQUAL KW_TO?
  private static boolean coupledClause_5_0_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "coupledClause_5_0_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, EQ_OP);
    if (!result_) result_ = coupledClause_5_0_2_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_EQUAL KW_TO?
  private static boolean coupledClause_5_0_2_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "coupledClause_5_0_2_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_EQUAL);
    result_ = result_ && coupledClause_5_0_2_0_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_TO?
  private static boolean coupledClause_5_0_2_0_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "coupledClause_5_0_2_0_1_1")) return false;
    consumeToken(builder_, KW_TO);
    return true;
  }

  // KW_WITH?
  private static boolean coupledClause_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "coupledClause_6")) return false;
    consumeToken(builder_, KW_WITH);
    return true;
  }

  // findCriteria?
  private static boolean coupledClause_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "coupledClause_7")) return false;
    findCriteria(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // scopeKeyword (KW_USING identifier | variableDecl*)
  public static boolean dataAreaBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dataAreaBlock")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DATA_AREA_BLOCK, "<data area block>");
    result_ = scopeKeyword(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && dataAreaBlock_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_USING identifier | variableDecl*
  private static boolean dataAreaBlock_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dataAreaBlock_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = dataAreaBlock_1_0(builder_, level_ + 1);
    if (!result_) result_ = dataAreaBlock_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_USING identifier
  private static boolean dataAreaBlock_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dataAreaBlock_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_USING);
    result_ = result_ && identifier(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // variableDecl*
  private static boolean dataAreaBlock_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dataAreaBlock_1_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!variableDecl(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "dataAreaBlock_1_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // DT_ALPHANUMERIC
  //   | DT_BINARY
  //   | DT_CV
  //   | DT_DATE
  //   | DT_FLOAT
  //   | DT_INT
  //   | DT_LOG
  //   | DT_NUMBER
  //   | DT_OBJECT
  //   | DT_PACK
  //   | DT_TIME
  //   | DT_UNICODE
  public static boolean dataType(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dataType")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DATA_TYPE, "<data type>");
    result_ = consumeToken(builder_, DT_ALPHANUMERIC);
    if (!result_) result_ = consumeToken(builder_, DT_BINARY);
    if (!result_) result_ = consumeToken(builder_, DT_CV);
    if (!result_) result_ = consumeToken(builder_, DT_DATE);
    if (!result_) result_ = consumeToken(builder_, DT_FLOAT);
    if (!result_) result_ = consumeToken(builder_, DT_INT);
    if (!result_) result_ = consumeToken(builder_, DT_LOG);
    if (!result_) result_ = consumeToken(builder_, DT_NUMBER);
    if (!result_) result_ = consumeToken(builder_, DT_OBJECT);
    if (!result_) result_ = consumeToken(builder_, DT_PACK);
    if (!result_) result_ = consumeToken(builder_, DT_TIME);
    if (!result_) result_ = consumeToken(builder_, DT_UNICODE);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // dataTypeSpec | viewSpec | shorthandType
  static boolean dataTypeOrViewSpec(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dataTypeOrViewSpec")) return false;
    boolean result_;
    result_ = dataTypeSpec(builder_, level_ + 1);
    if (!result_) result_ = viewSpec(builder_, level_ + 1);
    if (!result_) result_ = shorthandType(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // LPAREN dataType (DOT NUMBER)? (SLASH arrayDimInType (COMMA arrayDimInType)*)? RPAREN
  static boolean dataTypeSpec(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dataTypeSpec")) return false;
    if (!nextTokenIs(builder_, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && dataType(builder_, level_ + 1);
    result_ = result_ && dataTypeSpec_2(builder_, level_ + 1);
    result_ = result_ && dataTypeSpec_3(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (DOT NUMBER)?
  private static boolean dataTypeSpec_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dataTypeSpec_2")) return false;
    dataTypeSpec_2_0(builder_, level_ + 1);
    return true;
  }

  // DOT NUMBER
  private static boolean dataTypeSpec_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dataTypeSpec_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, DOT, NUMBER);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (SLASH arrayDimInType (COMMA arrayDimInType)*)?
  private static boolean dataTypeSpec_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dataTypeSpec_3")) return false;
    dataTypeSpec_3_0(builder_, level_ + 1);
    return true;
  }

  // SLASH arrayDimInType (COMMA arrayDimInType)*
  private static boolean dataTypeSpec_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dataTypeSpec_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, SLASH);
    result_ = result_ && arrayDimInType(builder_, level_ + 1);
    result_ = result_ && dataTypeSpec_3_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (COMMA arrayDimInType)*
  private static boolean dataTypeSpec_3_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dataTypeSpec_3_0_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!dataTypeSpec_3_0_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "dataTypeSpec_3_0_2", pos_)) break;
    }
    return true;
  }

  // COMMA arrayDimInType
  private static boolean dataTypeSpec_3_0_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dataTypeSpec_3_0_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && arrayDimInType(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_DECIDE KW_FOR (KW_FIRST | KW_EVERY) KW_CONDITION
  //                    whenForClause+
  //                    whenAnyClause?
  //                    whenAllClause?
  //                    whenNoneClause
  //                    KW_END_DECIDE
  public static boolean decideForBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideForBlock")) return false;
    if (!nextTokenIs(builder_, KW_DECIDE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DECIDE_FOR_BLOCK, null);
    result_ = consumeTokens(builder_, 2, KW_DECIDE, KW_FOR);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, decideForBlock_2(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, consumeToken(builder_, KW_CONDITION)) && result_;
    result_ = pinned_ && report_error_(builder_, decideForBlock_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, decideForBlock_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, decideForBlock_6(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, whenNoneClause(builder_, level_ + 1)) && result_;
    result_ = pinned_ && consumeToken(builder_, KW_END_DECIDE) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_FIRST | KW_EVERY
  private static boolean decideForBlock_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideForBlock_2")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_FIRST);
    if (!result_) result_ = consumeToken(builder_, KW_EVERY);
    return result_;
  }

  // whenForClause+
  private static boolean decideForBlock_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideForBlock_4")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = whenForClause(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!whenForClause(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "decideForBlock_4", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // whenAnyClause?
  private static boolean decideForBlock_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideForBlock_5")) return false;
    whenAnyClause(builder_, level_ + 1);
    return true;
  }

  // whenAllClause?
  private static boolean decideForBlock_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideForBlock_6")) return false;
    whenAllClause(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // KW_ALL  (KW_VALUE | KW_VALUES)? statement*
  public static boolean decideOnAllClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnAllClause")) return false;
    if (!nextTokenIs(builder_, KW_ALL)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DECIDE_ON_ALL_CLAUSE, null);
    result_ = consumeToken(builder_, KW_ALL);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, decideOnAllClause_1(builder_, level_ + 1));
    result_ = pinned_ && decideOnAllClause_2(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_VALUE | KW_VALUES)?
  private static boolean decideOnAllClause_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnAllClause_1")) return false;
    decideOnAllClause_1_0(builder_, level_ + 1);
    return true;
  }

  // KW_VALUE | KW_VALUES
  private static boolean decideOnAllClause_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnAllClause_1_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_VALUE);
    if (!result_) result_ = consumeToken(builder_, KW_VALUES);
    return result_;
  }

  // statement*
  private static boolean decideOnAllClause_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnAllClause_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "decideOnAllClause_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_ANY  (KW_VALUE | KW_VALUES)? statement*
  public static boolean decideOnAnyClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnAnyClause")) return false;
    if (!nextTokenIs(builder_, KW_ANY)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DECIDE_ON_ANY_CLAUSE, null);
    result_ = consumeToken(builder_, KW_ANY);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, decideOnAnyClause_1(builder_, level_ + 1));
    result_ = pinned_ && decideOnAnyClause_2(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_VALUE | KW_VALUES)?
  private static boolean decideOnAnyClause_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnAnyClause_1")) return false;
    decideOnAnyClause_1_0(builder_, level_ + 1);
    return true;
  }

  // KW_VALUE | KW_VALUES
  private static boolean decideOnAnyClause_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnAnyClause_1_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_VALUE);
    if (!result_) result_ = consumeToken(builder_, KW_VALUES);
    return result_;
  }

  // statement*
  private static boolean decideOnAnyClause_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnAnyClause_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "decideOnAnyClause_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_DECIDE KW_ON (KW_FIRST | KW_EVERY)? (KW_VALUE | KW_VALUES)? KW_OF? expression
  //                   decideOnValueClause+
  //                   decideOnAnyClause?
  //                   decideOnAllClause?
  //                   decideOnNoneClause
  //                   KW_END_DECIDE
  public static boolean decideOnBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnBlock")) return false;
    if (!nextTokenIs(builder_, KW_DECIDE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DECIDE_ON_BLOCK, null);
    result_ = consumeTokens(builder_, 2, KW_DECIDE, KW_ON);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, decideOnBlock_2(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, decideOnBlock_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, decideOnBlock_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, expression(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, decideOnBlock_6(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, decideOnBlock_7(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, decideOnBlock_8(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, decideOnNoneClause(builder_, level_ + 1)) && result_;
    result_ = pinned_ && consumeToken(builder_, KW_END_DECIDE) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_FIRST | KW_EVERY)?
  private static boolean decideOnBlock_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnBlock_2")) return false;
    decideOnBlock_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_FIRST | KW_EVERY
  private static boolean decideOnBlock_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnBlock_2_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_FIRST);
    if (!result_) result_ = consumeToken(builder_, KW_EVERY);
    return result_;
  }

  // (KW_VALUE | KW_VALUES)?
  private static boolean decideOnBlock_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnBlock_3")) return false;
    decideOnBlock_3_0(builder_, level_ + 1);
    return true;
  }

  // KW_VALUE | KW_VALUES
  private static boolean decideOnBlock_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnBlock_3_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_VALUE);
    if (!result_) result_ = consumeToken(builder_, KW_VALUES);
    return result_;
  }

  // KW_OF?
  private static boolean decideOnBlock_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnBlock_4")) return false;
    consumeToken(builder_, KW_OF);
    return true;
  }

  // decideOnValueClause+
  private static boolean decideOnBlock_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnBlock_6")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = decideOnValueClause(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!decideOnValueClause(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "decideOnBlock_6", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // decideOnAnyClause?
  private static boolean decideOnBlock_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnBlock_7")) return false;
    decideOnAnyClause(builder_, level_ + 1);
    return true;
  }

  // decideOnAllClause?
  private static boolean decideOnBlock_8(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnBlock_8")) return false;
    decideOnAllClause(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // KW_NONE (KW_VALUE | KW_VALUES)? statement*
  public static boolean decideOnNoneClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnNoneClause")) return false;
    if (!nextTokenIs(builder_, KW_NONE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DECIDE_ON_NONE_CLAUSE, null);
    result_ = consumeToken(builder_, KW_NONE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, decideOnNoneClause_1(builder_, level_ + 1));
    result_ = pinned_ && decideOnNoneClause_2(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_VALUE | KW_VALUES)?
  private static boolean decideOnNoneClause_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnNoneClause_1")) return false;
    decideOnNoneClause_1_0(builder_, level_ + 1);
    return true;
  }

  // KW_VALUE | KW_VALUES
  private static boolean decideOnNoneClause_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnNoneClause_1_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_VALUE);
    if (!result_) result_ = consumeToken(builder_, KW_VALUES);
    return result_;
  }

  // statement*
  private static boolean decideOnNoneClause_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnNoneClause_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "decideOnNoneClause_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (KW_VALUE | KW_VALUES) decideOnValueItem (COMMA decideOnValueItem)* statement*
  public static boolean decideOnValueClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnValueClause")) return false;
    if (!nextTokenIs(builder_, "<decide on value clause>", KW_VALUE, KW_VALUES)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DECIDE_ON_VALUE_CLAUSE, "<decide on value clause>");
    result_ = decideOnValueClause_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, decideOnValueItem(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, decideOnValueClause_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && decideOnValueClause_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_VALUE | KW_VALUES
  private static boolean decideOnValueClause_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnValueClause_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_VALUE);
    if (!result_) result_ = consumeToken(builder_, KW_VALUES);
    return result_;
  }

  // (COMMA decideOnValueItem)*
  private static boolean decideOnValueClause_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnValueClause_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!decideOnValueClause_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "decideOnValueClause_2", pos_)) break;
    }
    return true;
  }

  // COMMA decideOnValueItem
  private static boolean decideOnValueClause_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnValueClause_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && decideOnValueItem(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // statement*
  private static boolean decideOnValueClause_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnValueClause_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "decideOnValueClause_3", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // expression (COLON expression)?
  static boolean decideOnValueItem(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnValueItem")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1);
    result_ = result_ && decideOnValueItem_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (COLON expression)?
  private static boolean decideOnValueItem_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnValueItem_1")) return false;
    decideOnValueItem_1_0(builder_, level_ + 1);
    return true;
  }

  // COLON expression
  private static boolean decideOnValueItem_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decideOnValueItem_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COLON);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // LPAREN (sessionParameter | IDENTIFIER) EQ_OP editMaskValue RPAREN
  static boolean defineDataEditMask(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "defineDataEditMask")) return false;
    if (!nextTokenIs(builder_, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && defineDataEditMask_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, EQ_OP);
    result_ = result_ && editMaskValue(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // sessionParameter | IDENTIFIER
  private static boolean defineDataEditMask_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "defineDataEditMask_1")) return false;
    boolean result_;
    result_ = sessionParameter(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    return result_;
  }

  /* ********************************************************** */
  // KW_DEFINE_DATA dataAreaBlock* KW_END_DEFINE
  public static boolean defineDataPhase(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "defineDataPhase")) return false;
    if (!nextTokenIs(builder_, KW_DEFINE_DATA)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DEFINE_DATA_PHASE, null);
    result_ = consumeToken(builder_, KW_DEFINE_DATA);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, defineDataPhase_1(builder_, level_ + 1));
    result_ = pinned_ && consumeToken(builder_, KW_END_DEFINE) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // dataAreaBlock*
  private static boolean defineDataPhase_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "defineDataPhase_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!dataAreaBlock(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "defineDataPhase_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_DEFINE_PRINTER writeArg*
  public static boolean definePrinterStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "definePrinterStatement")) return false;
    if (!nextTokenIs(builder_, KW_DEFINE_PRINTER)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DEFINE_PRINTER_STATEMENT, null);
    result_ = consumeToken(builder_, KW_DEFINE_PRINTER);
    pinned_ = result_; // pin = 1
    result_ = result_ && definePrinterStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // writeArg*
  private static boolean definePrinterStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "definePrinterStatement_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "definePrinterStatement_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_DEFINE KW_SUBROUTINE? subroutineName statement* KW_END_SUBROUTINE
  public static boolean defineSubroutineBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "defineSubroutineBlock")) return false;
    if (!nextTokenIs(builder_, KW_DEFINE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DEFINE_SUBROUTINE_BLOCK, null);
    result_ = consumeToken(builder_, KW_DEFINE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, defineSubroutineBlock_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, subroutineName(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, defineSubroutineBlock_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && consumeToken(builder_, KW_END_SUBROUTINE) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_SUBROUTINE?
  private static boolean defineSubroutineBlock_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "defineSubroutineBlock_1")) return false;
    consumeToken(builder_, KW_SUBROUTINE);
    return true;
  }

  // statement*
  private static boolean defineSubroutineBlock_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "defineSubroutineBlock_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "defineSubroutineBlock_3", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_DEFINE_WINDOW writeArg*
  public static boolean defineWindowStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "defineWindowStatement")) return false;
    if (!nextTokenIs(builder_, KW_DEFINE_WINDOW)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DEFINE_WINDOW_STATEMENT, null);
    result_ = consumeToken(builder_, KW_DEFINE_WINDOW);
    pinned_ = result_; // pin = 1
    result_ = result_ && defineWindowStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // writeArg*
  private static boolean defineWindowStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "defineWindowStatement_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "defineWindowStatement_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_DEFINE_WORK_FILE writeArg*
  public static boolean defineWorkFileStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "defineWorkFileStatement")) return false;
    if (!nextTokenIs(builder_, KW_DEFINE_WORK_FILE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DEFINE_WORK_FILE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_DEFINE_WORK_FILE);
    pinned_ = result_; // pin = 1
    result_ = result_ && defineWorkFileStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // writeArg*
  private static boolean defineWorkFileStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "defineWorkFileStatement_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "defineWorkFileStatement_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_DELETE KW_RECORD? KW_IN? KW_STATEMENT? statementRef?
  public static boolean deleteStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "deleteStatement")) return false;
    if (!nextTokenIs(builder_, KW_DELETE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DELETE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_DELETE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, deleteStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, deleteStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, deleteStatement_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && deleteStatement_4(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_RECORD?
  private static boolean deleteStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "deleteStatement_1")) return false;
    consumeToken(builder_, KW_RECORD);
    return true;
  }

  // KW_IN?
  private static boolean deleteStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "deleteStatement_2")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // KW_STATEMENT?
  private static boolean deleteStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "deleteStatement_3")) return false;
    consumeToken(builder_, KW_STATEMENT);
    return true;
  }

  // statementRef?
  private static boolean deleteStatement_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "deleteStatement_4")) return false;
    statementRef(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // identifier | STRING_LITERAL
  static boolean descriptor(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "descriptor")) return false;
    boolean result_;
    result_ = identifier(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, STRING_LITERAL);
    return result_;
  }

  /* ********************************************************** */
  // KW_DISPLAY writeArg*
  public static boolean displayStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "displayStatement")) return false;
    if (!nextTokenIs(builder_, KW_DISPLAY)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DISPLAY_STATEMENT, null);
    result_ = consumeToken(builder_, KW_DISPLAY);
    pinned_ = result_; // pin = 1
    result_ = result_ && displayStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // writeArg*
  private static boolean displayStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "displayStatement_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "displayStatement_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_DIVIDE KW_ROUNDED? expression KW_INTO? expression (KW_GIVING expression)? (KW_REMAINDER expression)?
  public static boolean divideStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "divideStatement")) return false;
    if (!nextTokenIs(builder_, KW_DIVIDE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DIVIDE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_DIVIDE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, divideStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, expression(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, divideStatement_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, expression(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, divideStatement_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && divideStatement_6(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_ROUNDED?
  private static boolean divideStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "divideStatement_1")) return false;
    consumeToken(builder_, KW_ROUNDED);
    return true;
  }

  // KW_INTO?
  private static boolean divideStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "divideStatement_3")) return false;
    consumeToken(builder_, KW_INTO);
    return true;
  }

  // (KW_GIVING expression)?
  private static boolean divideStatement_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "divideStatement_5")) return false;
    divideStatement_5_0(builder_, level_ + 1);
    return true;
  }

  // KW_GIVING expression
  private static boolean divideStatement_5_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "divideStatement_5_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_GIVING);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_REMAINDER expression)?
  private static boolean divideStatement_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "divideStatement_6")) return false;
    divideStatement_6_0(builder_, level_ + 1);
    return true;
  }

  // KW_REMAINDER expression
  private static boolean divideStatement_6_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "divideStatement_6_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_REMAINDER);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_DOWNLOAD SP_PC? KW_FILE? expression*
  public static boolean downloadStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "downloadStatement")) return false;
    if (!nextTokenIs(builder_, KW_DOWNLOAD)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DOWNLOAD_STATEMENT, null);
    result_ = consumeToken(builder_, KW_DOWNLOAD);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, downloadStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, downloadStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && downloadStatement_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // SP_PC?
  private static boolean downloadStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "downloadStatement_1")) return false;
    consumeToken(builder_, SP_PC);
    return true;
  }

  // KW_FILE?
  private static boolean downloadStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "downloadStatement_2")) return false;
    consumeToken(builder_, KW_FILE);
    return true;
  }

  // expression*
  private static boolean downloadStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "downloadStatement_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!expression(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "downloadStatement_3", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_ON | KW_OFF | KW_AT | GE_OP | LE_OP | STRING_LITERAL | IDENTIFIER | NUMBER | DOT | COLON | PLUS | MINUS | STAR | SLASH | NEQ_OP | EQ_OP | CARET | PERCENT | LT_OP | GT_OP | editMaskNestedParen | dataType | sessionParameter
  static boolean editMaskAtom(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "editMaskAtom")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_ON);
    if (!result_) result_ = consumeToken(builder_, KW_OFF);
    if (!result_) result_ = consumeToken(builder_, KW_AT);
    if (!result_) result_ = consumeToken(builder_, GE_OP);
    if (!result_) result_ = consumeToken(builder_, LE_OP);
    if (!result_) result_ = consumeToken(builder_, STRING_LITERAL);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, DOT);
    if (!result_) result_ = consumeToken(builder_, COLON);
    if (!result_) result_ = consumeToken(builder_, PLUS);
    if (!result_) result_ = consumeToken(builder_, MINUS);
    if (!result_) result_ = consumeToken(builder_, STAR);
    if (!result_) result_ = consumeToken(builder_, SLASH);
    if (!result_) result_ = consumeToken(builder_, NEQ_OP);
    if (!result_) result_ = consumeToken(builder_, EQ_OP);
    if (!result_) result_ = consumeToken(builder_, CARET);
    if (!result_) result_ = consumeToken(builder_, PERCENT);
    if (!result_) result_ = consumeToken(builder_, LT_OP);
    if (!result_) result_ = consumeToken(builder_, GT_OP);
    if (!result_) result_ = editMaskNestedParen(builder_, level_ + 1);
    if (!result_) result_ = dataType(builder_, level_ + 1);
    if (!result_) result_ = sessionParameter(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // COMMA !sessionParameter
  static boolean editMaskComma(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "editMaskComma")) return false;
    if (!nextTokenIs(builder_, COMMA)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && editMaskComma_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !sessionParameter
  private static boolean editMaskComma_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "editMaskComma_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !sessionParameter(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // LPAREN (editMaskAtom | editMaskComma)* RPAREN
  static boolean editMaskNestedParen(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "editMaskNestedParen")) return false;
    if (!nextTokenIs(builder_, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && editMaskNestedParen_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (editMaskAtom | editMaskComma)*
  private static boolean editMaskNestedParen_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "editMaskNestedParen_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!editMaskNestedParen_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "editMaskNestedParen_1", pos_)) break;
    }
    return true;
  }

  // editMaskAtom | editMaskComma
  private static boolean editMaskNestedParen_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "editMaskNestedParen_1_0")) return false;
    boolean result_;
    result_ = editMaskAtom(builder_, level_ + 1);
    if (!result_) result_ = editMaskComma(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // (editMaskAtom | editMaskComma)+
  static boolean editMaskValue(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "editMaskValue")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = editMaskValue_0(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!editMaskValue_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "editMaskValue", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // editMaskAtom | editMaskComma
  private static boolean editMaskValue_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "editMaskValue_0")) return false;
    boolean result_;
    result_ = editMaskAtom(builder_, level_ + 1);
    if (!result_) result_ = editMaskComma(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // KW_EJECT (LPAREN (NUMBER | IDENTIFIER) RPAREN)?
  //                          (KW_IF KW_LESS KW_THAN? expression KW_LINES? KW_LEFT?)?
  //                          writeArg*
  public static boolean ejectStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ejectStatement")) return false;
    if (!nextTokenIs(builder_, KW_EJECT)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, EJECT_STATEMENT, null);
    result_ = consumeToken(builder_, KW_EJECT);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, ejectStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, ejectStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && ejectStatement_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (LPAREN (NUMBER | IDENTIFIER) RPAREN)?
  private static boolean ejectStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ejectStatement_1")) return false;
    ejectStatement_1_0(builder_, level_ + 1);
    return true;
  }

  // LPAREN (NUMBER | IDENTIFIER) RPAREN
  private static boolean ejectStatement_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ejectStatement_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && ejectStatement_1_0_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // NUMBER | IDENTIFIER
  private static boolean ejectStatement_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ejectStatement_1_0_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    return result_;
  }

  // (KW_IF KW_LESS KW_THAN? expression KW_LINES? KW_LEFT?)?
  private static boolean ejectStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ejectStatement_2")) return false;
    ejectStatement_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_IF KW_LESS KW_THAN? expression KW_LINES? KW_LEFT?
  private static boolean ejectStatement_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ejectStatement_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_IF, KW_LESS);
    result_ = result_ && ejectStatement_2_0_2(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && ejectStatement_2_0_4(builder_, level_ + 1);
    result_ = result_ && ejectStatement_2_0_5(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_THAN?
  private static boolean ejectStatement_2_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ejectStatement_2_0_2")) return false;
    consumeToken(builder_, KW_THAN);
    return true;
  }

  // KW_LINES?
  private static boolean ejectStatement_2_0_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ejectStatement_2_0_4")) return false;
    consumeToken(builder_, KW_LINES);
    return true;
  }

  // KW_LEFT?
  private static boolean ejectStatement_2_0_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ejectStatement_2_0_5")) return false;
    consumeToken(builder_, KW_LEFT);
    return true;
  }

  // writeArg*
  private static boolean ejectStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ejectStatement_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "ejectStatement_3", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_END_ALL
  public static boolean endAllStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "endAllStatement")) return false;
    if (!nextTokenIs(builder_, KW_END_ALL)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_END_ALL);
    exit_section_(builder_, marker_, END_ALL_STATEMENT, result_);
    return result_;
  }

  /* ********************************************************** */
  // (KW_END_TRANSACTION | KW_END KW_TRANSACTION) writeArg*
  public static boolean endTransactionStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "endTransactionStatement")) return false;
    if (!nextTokenIs(builder_, "<end transaction statement>", KW_END, KW_END_TRANSACTION)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, END_TRANSACTION_STATEMENT, "<end transaction statement>");
    result_ = endTransactionStatement_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && endTransactionStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_END_TRANSACTION | KW_END KW_TRANSACTION
  private static boolean endTransactionStatement_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "endTransactionStatement_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_END_TRANSACTION);
    if (!result_) result_ = parseTokens(builder_, 0, KW_END, KW_TRANSACTION);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // writeArg*
  private static boolean endTransactionStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "endTransactionStatement_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "endTransactionStatement_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // EQ_OP | KW_EQUAL KW_TO? | KW_STARTING KW_FROM | KW_FROM
  public static boolean equalOrStartingFromClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "equalOrStartingFromClause")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, EQUAL_OR_STARTING_FROM_CLAUSE, "<equal or starting from clause>");
    result_ = consumeToken(builder_, EQ_OP);
    if (!result_) result_ = equalOrStartingFromClause_1(builder_, level_ + 1);
    if (!result_) result_ = parseTokens(builder_, 0, KW_STARTING, KW_FROM);
    if (!result_) result_ = consumeToken(builder_, KW_FROM);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_EQUAL KW_TO?
  private static boolean equalOrStartingFromClause_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "equalOrStartingFromClause_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_EQUAL);
    result_ = result_ && equalOrStartingFromClause_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_TO?
  private static boolean equalOrStartingFromClause_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "equalOrStartingFromClause_1_1")) return false;
    consumeToken(builder_, KW_TO);
    return true;
  }

  /* ********************************************************** */
  // KW_ESCAPE (KW_BOTTOM | KW_TOP | KW_ROUTINE | KW_MODULE)? KW_IMMEDIATE? (LPAREN (blockLabel | NUMBER | IDENTIFIER) RPAREN | blockLabel)?
  public static boolean escapeStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "escapeStatement")) return false;
    if (!nextTokenIs(builder_, KW_ESCAPE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ESCAPE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_ESCAPE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, escapeStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, escapeStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && escapeStatement_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_BOTTOM | KW_TOP | KW_ROUTINE | KW_MODULE)?
  private static boolean escapeStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "escapeStatement_1")) return false;
    escapeStatement_1_0(builder_, level_ + 1);
    return true;
  }

  // KW_BOTTOM | KW_TOP | KW_ROUTINE | KW_MODULE
  private static boolean escapeStatement_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "escapeStatement_1_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_BOTTOM);
    if (!result_) result_ = consumeToken(builder_, KW_TOP);
    if (!result_) result_ = consumeToken(builder_, KW_ROUTINE);
    if (!result_) result_ = consumeToken(builder_, KW_MODULE);
    return result_;
  }

  // KW_IMMEDIATE?
  private static boolean escapeStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "escapeStatement_2")) return false;
    consumeToken(builder_, KW_IMMEDIATE);
    return true;
  }

  // (LPAREN (blockLabel | NUMBER | IDENTIFIER) RPAREN | blockLabel)?
  private static boolean escapeStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "escapeStatement_3")) return false;
    escapeStatement_3_0(builder_, level_ + 1);
    return true;
  }

  // LPAREN (blockLabel | NUMBER | IDENTIFIER) RPAREN | blockLabel
  private static boolean escapeStatement_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "escapeStatement_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = escapeStatement_3_0_0(builder_, level_ + 1);
    if (!result_) result_ = blockLabel(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // LPAREN (blockLabel | NUMBER | IDENTIFIER) RPAREN
  private static boolean escapeStatement_3_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "escapeStatement_3_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && escapeStatement_3_0_0_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // blockLabel | NUMBER | IDENTIFIER
  private static boolean escapeStatement_3_0_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "escapeStatement_3_0_0_1")) return false;
    boolean result_;
    result_ = blockLabel(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    return result_;
  }

  /* ********************************************************** */
  // KW_FOR KW_FULL? KW_PATTERN? expression examineOption*
  //                       | KW_AND? KW_TRANSLATE KW_INTO (KW_UPPER | KW_LOWER) KW_CASE?
  //                       | examineOption+
  //                       | expression examineOption*
  static boolean examineBody(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineBody")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = examineBody_0(builder_, level_ + 1);
    if (!result_) result_ = examineBody_1(builder_, level_ + 1);
    if (!result_) result_ = examineBody_2(builder_, level_ + 1);
    if (!result_) result_ = examineBody_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_FOR KW_FULL? KW_PATTERN? expression examineOption*
  private static boolean examineBody_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineBody_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_FOR);
    result_ = result_ && examineBody_0_1(builder_, level_ + 1);
    result_ = result_ && examineBody_0_2(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && examineBody_0_4(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_FULL?
  private static boolean examineBody_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineBody_0_1")) return false;
    consumeToken(builder_, KW_FULL);
    return true;
  }

  // KW_PATTERN?
  private static boolean examineBody_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineBody_0_2")) return false;
    consumeToken(builder_, KW_PATTERN);
    return true;
  }

  // examineOption*
  private static boolean examineBody_0_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineBody_0_4")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!examineOption(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "examineBody_0_4", pos_)) break;
    }
    return true;
  }

  // KW_AND? KW_TRANSLATE KW_INTO (KW_UPPER | KW_LOWER) KW_CASE?
  private static boolean examineBody_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineBody_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = examineBody_1_0(builder_, level_ + 1);
    result_ = result_ && consumeTokens(builder_, 0, KW_TRANSLATE, KW_INTO);
    result_ = result_ && examineBody_1_3(builder_, level_ + 1);
    result_ = result_ && examineBody_1_4(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_AND?
  private static boolean examineBody_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineBody_1_0")) return false;
    consumeToken(builder_, KW_AND);
    return true;
  }

  // KW_UPPER | KW_LOWER
  private static boolean examineBody_1_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineBody_1_3")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_UPPER);
    if (!result_) result_ = consumeToken(builder_, KW_LOWER);
    return result_;
  }

  // KW_CASE?
  private static boolean examineBody_1_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineBody_1_4")) return false;
    consumeToken(builder_, KW_CASE);
    return true;
  }

  // examineOption+
  private static boolean examineBody_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineBody_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = examineOption(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!examineOption(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "examineBody_2", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // expression examineOption*
  private static boolean examineBody_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineBody_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1);
    result_ = result_ && examineBody_3_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // examineOption*
  private static boolean examineBody_3_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineBody_3_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!examineOption(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "examineBody_3_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_GIVING (KW_POSITION | KW_LENGTH | KW_INDEX | KW_NUMBER) KW_IN? (!statement !KW_INDEX expression)? (!statement !KW_INDEX expression)?
  //                         | KW_GIVING identifier
  //                         | KW_REPLACE KW_WITH? expression
  //                         | KW_WITH (KW_DELIMITER | KW_DELIMITERS) expression?
  //                         | KW_DELETE
  //                         | KW_INDEX KW_IN? (!statement expression)?
  //                         | KW_NUMBER KW_IN? (!statement expression)?
  //                         | KW_POSITION KW_IN? (!statement expression)?
  //                         | KW_LENGTH KW_IN? (!statement expression)?
  //                         | KW_AND KW_TRANSLATE KW_INTO (KW_UPPER | KW_LOWER) IDENTIFIER?
  static boolean examineOption(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = examineOption_0(builder_, level_ + 1);
    if (!result_) result_ = examineOption_1(builder_, level_ + 1);
    if (!result_) result_ = examineOption_2(builder_, level_ + 1);
    if (!result_) result_ = examineOption_3(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, KW_DELETE);
    if (!result_) result_ = examineOption_5(builder_, level_ + 1);
    if (!result_) result_ = examineOption_6(builder_, level_ + 1);
    if (!result_) result_ = examineOption_7(builder_, level_ + 1);
    if (!result_) result_ = examineOption_8(builder_, level_ + 1);
    if (!result_) result_ = examineOption_9(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_GIVING (KW_POSITION | KW_LENGTH | KW_INDEX | KW_NUMBER) KW_IN? (!statement !KW_INDEX expression)? (!statement !KW_INDEX expression)?
  private static boolean examineOption_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_GIVING);
    result_ = result_ && examineOption_0_1(builder_, level_ + 1);
    result_ = result_ && examineOption_0_2(builder_, level_ + 1);
    result_ = result_ && examineOption_0_3(builder_, level_ + 1);
    result_ = result_ && examineOption_0_4(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_POSITION | KW_LENGTH | KW_INDEX | KW_NUMBER
  private static boolean examineOption_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_0_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_POSITION);
    if (!result_) result_ = consumeToken(builder_, KW_LENGTH);
    if (!result_) result_ = consumeToken(builder_, KW_INDEX);
    if (!result_) result_ = consumeToken(builder_, KW_NUMBER);
    return result_;
  }

  // KW_IN?
  private static boolean examineOption_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_0_2")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // (!statement !KW_INDEX expression)?
  private static boolean examineOption_0_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_0_3")) return false;
    examineOption_0_3_0(builder_, level_ + 1);
    return true;
  }

  // !statement !KW_INDEX expression
  private static boolean examineOption_0_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_0_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = examineOption_0_3_0_0(builder_, level_ + 1);
    result_ = result_ && examineOption_0_3_0_1(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean examineOption_0_3_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_0_3_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !KW_INDEX
  private static boolean examineOption_0_3_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_0_3_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, KW_INDEX);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (!statement !KW_INDEX expression)?
  private static boolean examineOption_0_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_0_4")) return false;
    examineOption_0_4_0(builder_, level_ + 1);
    return true;
  }

  // !statement !KW_INDEX expression
  private static boolean examineOption_0_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_0_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = examineOption_0_4_0_0(builder_, level_ + 1);
    result_ = result_ && examineOption_0_4_0_1(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean examineOption_0_4_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_0_4_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !KW_INDEX
  private static boolean examineOption_0_4_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_0_4_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, KW_INDEX);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_GIVING identifier
  private static boolean examineOption_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_GIVING);
    result_ = result_ && identifier(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_REPLACE KW_WITH? expression
  private static boolean examineOption_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_REPLACE);
    result_ = result_ && examineOption_2_1(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_WITH?
  private static boolean examineOption_2_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_2_1")) return false;
    consumeToken(builder_, KW_WITH);
    return true;
  }

  // KW_WITH (KW_DELIMITER | KW_DELIMITERS) expression?
  private static boolean examineOption_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_WITH);
    result_ = result_ && examineOption_3_1(builder_, level_ + 1);
    result_ = result_ && examineOption_3_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_DELIMITER | KW_DELIMITERS
  private static boolean examineOption_3_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_3_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_DELIMITER);
    if (!result_) result_ = consumeToken(builder_, KW_DELIMITERS);
    return result_;
  }

  // expression?
  private static boolean examineOption_3_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_3_2")) return false;
    expression(builder_, level_ + 1);
    return true;
  }

  // KW_INDEX KW_IN? (!statement expression)?
  private static boolean examineOption_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_5")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_INDEX);
    result_ = result_ && examineOption_5_1(builder_, level_ + 1);
    result_ = result_ && examineOption_5_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_IN?
  private static boolean examineOption_5_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_5_1")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // (!statement expression)?
  private static boolean examineOption_5_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_5_2")) return false;
    examineOption_5_2_0(builder_, level_ + 1);
    return true;
  }

  // !statement expression
  private static boolean examineOption_5_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_5_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = examineOption_5_2_0_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean examineOption_5_2_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_5_2_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_NUMBER KW_IN? (!statement expression)?
  private static boolean examineOption_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_6")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_NUMBER);
    result_ = result_ && examineOption_6_1(builder_, level_ + 1);
    result_ = result_ && examineOption_6_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_IN?
  private static boolean examineOption_6_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_6_1")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // (!statement expression)?
  private static boolean examineOption_6_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_6_2")) return false;
    examineOption_6_2_0(builder_, level_ + 1);
    return true;
  }

  // !statement expression
  private static boolean examineOption_6_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_6_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = examineOption_6_2_0_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean examineOption_6_2_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_6_2_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_POSITION KW_IN? (!statement expression)?
  private static boolean examineOption_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_7")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_POSITION);
    result_ = result_ && examineOption_7_1(builder_, level_ + 1);
    result_ = result_ && examineOption_7_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_IN?
  private static boolean examineOption_7_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_7_1")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // (!statement expression)?
  private static boolean examineOption_7_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_7_2")) return false;
    examineOption_7_2_0(builder_, level_ + 1);
    return true;
  }

  // !statement expression
  private static boolean examineOption_7_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_7_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = examineOption_7_2_0_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean examineOption_7_2_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_7_2_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_LENGTH KW_IN? (!statement expression)?
  private static boolean examineOption_8(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_8")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_LENGTH);
    result_ = result_ && examineOption_8_1(builder_, level_ + 1);
    result_ = result_ && examineOption_8_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_IN?
  private static boolean examineOption_8_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_8_1")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // (!statement expression)?
  private static boolean examineOption_8_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_8_2")) return false;
    examineOption_8_2_0(builder_, level_ + 1);
    return true;
  }

  // !statement expression
  private static boolean examineOption_8_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_8_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = examineOption_8_2_0_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean examineOption_8_2_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_8_2_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_AND KW_TRANSLATE KW_INTO (KW_UPPER | KW_LOWER) IDENTIFIER?
  private static boolean examineOption_9(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_9")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_AND, KW_TRANSLATE, KW_INTO);
    result_ = result_ && examineOption_9_3(builder_, level_ + 1);
    result_ = result_ && examineOption_9_4(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_UPPER | KW_LOWER
  private static boolean examineOption_9_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_9_3")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_UPPER);
    if (!result_) result_ = consumeToken(builder_, KW_LOWER);
    return result_;
  }

  // IDENTIFIER?
  private static boolean examineOption_9_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineOption_9_4")) return false;
    consumeToken(builder_, IDENTIFIER);
    return true;
  }

  /* ********************************************************** */
  // KW_EXAMINE (KW_DIRECTION (KW_BACKWARD | KW_FORWARD)?)? KW_FULL?
  //                          examineTarget
  //                          (KW_FROM expression)?
  //                          examineBody?
  public static boolean examineStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineStatement")) return false;
    if (!nextTokenIs(builder_, KW_EXAMINE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, EXAMINE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_EXAMINE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, examineStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, examineStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, examineTarget(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, examineStatement_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && examineStatement_5(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_DIRECTION (KW_BACKWARD | KW_FORWARD)?)?
  private static boolean examineStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineStatement_1")) return false;
    examineStatement_1_0(builder_, level_ + 1);
    return true;
  }

  // KW_DIRECTION (KW_BACKWARD | KW_FORWARD)?
  private static boolean examineStatement_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineStatement_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_DIRECTION);
    result_ = result_ && examineStatement_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_BACKWARD | KW_FORWARD)?
  private static boolean examineStatement_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineStatement_1_0_1")) return false;
    examineStatement_1_0_1_0(builder_, level_ + 1);
    return true;
  }

  // KW_BACKWARD | KW_FORWARD
  private static boolean examineStatement_1_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineStatement_1_0_1_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_BACKWARD);
    if (!result_) result_ = consumeToken(builder_, KW_FORWARD);
    return result_;
  }

  // KW_FULL?
  private static boolean examineStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineStatement_2")) return false;
    consumeToken(builder_, KW_FULL);
    return true;
  }

  // (KW_FROM expression)?
  private static boolean examineStatement_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineStatement_4")) return false;
    examineStatement_4_0(builder_, level_ + 1);
    return true;
  }

  // KW_FROM expression
  private static boolean examineStatement_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineStatement_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_FROM);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // examineBody?
  private static boolean examineStatement_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineStatement_5")) return false;
    examineBody(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // KW_SUBSTRING LPAREN expression (COMMA expression)* RPAREN | expression
  static boolean examineTarget(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineTarget")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = examineTarget_0(builder_, level_ + 1);
    if (!result_) result_ = expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_SUBSTRING LPAREN expression (COMMA expression)* RPAREN
  private static boolean examineTarget_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineTarget_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_SUBSTRING, LPAREN);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && examineTarget_0_3(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (COMMA expression)*
  private static boolean examineTarget_0_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineTarget_0_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!examineTarget_0_3_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "examineTarget_0_3", pos_)) break;
    }
    return true;
  }

  // COMMA expression
  private static boolean examineTarget_0_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "examineTarget_0_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_TO arraySpec | !statement expression KW_TO arraySpec
  static boolean expandBody(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expandBody")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expandBody_0(builder_, level_ + 1);
    if (!result_) result_ = expandBody_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_TO arraySpec
  private static boolean expandBody_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expandBody_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_TO);
    result_ = result_ && arraySpec(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement expression KW_TO arraySpec
  private static boolean expandBody_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expandBody_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expandBody_1_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_TO);
    result_ = result_ && arraySpec(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean expandBody_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expandBody_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // KW_EXPAND KW_ARRAY? expandBody? writeArg*
  public static boolean expandStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expandStatement")) return false;
    if (!nextTokenIs(builder_, KW_EXPAND)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, EXPAND_STATEMENT, null);
    result_ = consumeToken(builder_, KW_EXPAND);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, expandStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, expandStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && expandStatement_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_ARRAY?
  private static boolean expandStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expandStatement_1")) return false;
    consumeToken(builder_, KW_ARRAY);
    return true;
  }

  // expandBody?
  private static boolean expandStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expandStatement_2")) return false;
    expandBody(builder_, level_ + 1);
    return true;
  }

  // writeArg*
  private static boolean expandStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expandStatement_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "expandStatement_3", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // addExpr
  public static boolean expression(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expression")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, EXPRESSION, "<expression>");
    result_ = addExpr(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // KW_FETCH (!statement !SLASH expression)*
  public static boolean fetchStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fetchStatement")) return false;
    if (!nextTokenIs(builder_, KW_FETCH)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FETCH_STATEMENT, null);
    result_ = consumeToken(builder_, KW_FETCH);
    pinned_ = result_; // pin = 1
    result_ = result_ && fetchStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (!statement !SLASH expression)*
  private static boolean fetchStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fetchStatement_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!fetchStatement_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "fetchStatement_1", pos_)) break;
    }
    return true;
  }

  // !statement !SLASH expression
  private static boolean fetchStatement_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fetchStatement_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = fetchStatement_1_0_0(builder_, level_ + 1);
    result_ = result_ && fetchStatement_1_0_1(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean fetchStatement_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fetchStatement_1_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !SLASH
  private static boolean fetchStatement_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fetchStatement_1_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, SLASH);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // blockLabel? KW_FIND findModeClause
  //               multiFetchClause? KW_RECORDS? KW_IN? KW_FILE? identifier
  //               passwordClause? cipherClause?
  //               KW_WITH? (KW_LIMIT LPAREN expression RPAREN)? findCriteria?
  //               coupledClause*
  //               startingWithIsn?
  //               sortedByClause?
  //               retainClause?
  //               inSharedHold? skipRecordsInHold?
  //               whereClause?
  //               statement* (KW_END_FIND | KW_END_ALL)?
  public static boolean findBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FIND_BLOCK, "<find block>");
    result_ = findBlock_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_FIND);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, findModeClause(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, findBlock_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_6(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, identifier(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_8(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_9(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_10(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_11(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_12(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_13(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_14(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_15(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_16(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_17(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_18(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_19(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findBlock_20(builder_, level_ + 1)) && result_;
    result_ = pinned_ && findBlock_21(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // blockLabel?
  private static boolean findBlock_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_0")) return false;
    blockLabel(builder_, level_ + 1);
    return true;
  }

  // multiFetchClause?
  private static boolean findBlock_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_3")) return false;
    multiFetchClause(builder_, level_ + 1);
    return true;
  }

  // KW_RECORDS?
  private static boolean findBlock_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_4")) return false;
    consumeToken(builder_, KW_RECORDS);
    return true;
  }

  // KW_IN?
  private static boolean findBlock_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_5")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // KW_FILE?
  private static boolean findBlock_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_6")) return false;
    consumeToken(builder_, KW_FILE);
    return true;
  }

  // passwordClause?
  private static boolean findBlock_8(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_8")) return false;
    passwordClause(builder_, level_ + 1);
    return true;
  }

  // cipherClause?
  private static boolean findBlock_9(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_9")) return false;
    cipherClause(builder_, level_ + 1);
    return true;
  }

  // KW_WITH?
  private static boolean findBlock_10(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_10")) return false;
    consumeToken(builder_, KW_WITH);
    return true;
  }

  // (KW_LIMIT LPAREN expression RPAREN)?
  private static boolean findBlock_11(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_11")) return false;
    findBlock_11_0(builder_, level_ + 1);
    return true;
  }

  // KW_LIMIT LPAREN expression RPAREN
  private static boolean findBlock_11_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_11_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_LIMIT, LPAREN);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // findCriteria?
  private static boolean findBlock_12(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_12")) return false;
    findCriteria(builder_, level_ + 1);
    return true;
  }

  // coupledClause*
  private static boolean findBlock_13(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_13")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!coupledClause(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "findBlock_13", pos_)) break;
    }
    return true;
  }

  // startingWithIsn?
  private static boolean findBlock_14(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_14")) return false;
    startingWithIsn(builder_, level_ + 1);
    return true;
  }

  // sortedByClause?
  private static boolean findBlock_15(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_15")) return false;
    sortedByClause(builder_, level_ + 1);
    return true;
  }

  // retainClause?
  private static boolean findBlock_16(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_16")) return false;
    retainClause(builder_, level_ + 1);
    return true;
  }

  // inSharedHold?
  private static boolean findBlock_17(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_17")) return false;
    inSharedHold(builder_, level_ + 1);
    return true;
  }

  // skipRecordsInHold?
  private static boolean findBlock_18(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_18")) return false;
    skipRecordsInHold(builder_, level_ + 1);
    return true;
  }

  // whereClause?
  private static boolean findBlock_19(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_19")) return false;
    whereClause(builder_, level_ + 1);
    return true;
  }

  // statement*
  private static boolean findBlock_20(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_20")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "findBlock_20", pos_)) break;
    }
    return true;
  }

  // (KW_END_FIND | KW_END_ALL)?
  private static boolean findBlock_21(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_21")) return false;
    findBlock_21_0(builder_, level_ + 1);
    return true;
  }

  // KW_END_FIND | KW_END_ALL
  private static boolean findBlock_21_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findBlock_21_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_END_FIND);
    if (!result_) result_ = consumeToken(builder_, KW_END_ALL);
    return result_;
  }

  /* ********************************************************** */
  // EQ_OP | NEQ_OP | LT_OP | GT_OP | LE_OP | GE_OP
  //                  | KW_EQUAL KW_TO?
  //                  | KW_LESS KW_THAN | KW_LESS KW_EQUAL
  //                  | KW_GREATER KW_THAN | KW_GREATER KW_EQUAL
  //                  | KW_NOT (EQ_OP | KW_EQUAL | LT_OP | GT_OP | LE_OP | GE_OP)
  public static boolean findComparator(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findComparator")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FIND_COMPARATOR, "<find comparator>");
    result_ = consumeToken(builder_, EQ_OP);
    if (!result_) result_ = consumeToken(builder_, NEQ_OP);
    if (!result_) result_ = consumeToken(builder_, LT_OP);
    if (!result_) result_ = consumeToken(builder_, GT_OP);
    if (!result_) result_ = consumeToken(builder_, LE_OP);
    if (!result_) result_ = consumeToken(builder_, GE_OP);
    if (!result_) result_ = findComparator_6(builder_, level_ + 1);
    if (!result_) result_ = parseTokens(builder_, 0, KW_LESS, KW_THAN);
    if (!result_) result_ = parseTokens(builder_, 0, KW_LESS, KW_EQUAL);
    if (!result_) result_ = parseTokens(builder_, 0, KW_GREATER, KW_THAN);
    if (!result_) result_ = parseTokens(builder_, 0, KW_GREATER, KW_EQUAL);
    if (!result_) result_ = findComparator_11(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_EQUAL KW_TO?
  private static boolean findComparator_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findComparator_6")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_EQUAL);
    result_ = result_ && findComparator_6_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_TO?
  private static boolean findComparator_6_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findComparator_6_1")) return false;
    consumeToken(builder_, KW_TO);
    return true;
  }

  // KW_NOT (EQ_OP | KW_EQUAL | LT_OP | GT_OP | LE_OP | GE_OP)
  private static boolean findComparator_11(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findComparator_11")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_NOT);
    result_ = result_ && findComparator_11_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // EQ_OP | KW_EQUAL | LT_OP | GT_OP | LE_OP | GE_OP
  private static boolean findComparator_11_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findComparator_11_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, EQ_OP);
    if (!result_) result_ = consumeToken(builder_, KW_EQUAL);
    if (!result_) result_ = consumeToken(builder_, LT_OP);
    if (!result_) result_ = consumeToken(builder_, GT_OP);
    if (!result_) result_ = consumeToken(builder_, LE_OP);
    if (!result_) result_ = consumeToken(builder_, GE_OP);
    return result_;
  }

  /* ********************************************************** */
  // findCriterion ((KW_AND | KW_OR) findCriterion)*
  public static boolean findCriteria(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriteria")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FIND_CRITERIA, "<find criteria>");
    result_ = findCriterion(builder_, level_ + 1);
    result_ = result_ && findCriteria_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // ((KW_AND | KW_OR) findCriterion)*
  private static boolean findCriteria_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriteria_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!findCriteria_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "findCriteria_1", pos_)) break;
    }
    return true;
  }

  // (KW_AND | KW_OR) findCriterion
  private static boolean findCriteria_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriteria_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = findCriteria_1_0_0(builder_, level_ + 1);
    result_ = result_ && findCriterion(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_AND | KW_OR
  private static boolean findCriteria_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriteria_1_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_AND);
    if (!result_) result_ = consumeToken(builder_, KW_OR);
    return result_;
  }

  /* ********************************************************** */
  // descriptor subscript? (findComparator? expression (KW_OR findComparator expression)*)?
  //                   (KW_THRU expression (KW_BUT KW_NOT expression (KW_THRU expression)?)?)?
  public static boolean findCriterion(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriterion")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FIND_CRITERION, "<find criterion>");
    result_ = descriptor(builder_, level_ + 1);
    result_ = result_ && findCriterion_1(builder_, level_ + 1);
    result_ = result_ && findCriterion_2(builder_, level_ + 1);
    result_ = result_ && findCriterion_3(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // subscript?
  private static boolean findCriterion_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriterion_1")) return false;
    subscript(builder_, level_ + 1);
    return true;
  }

  // (findComparator? expression (KW_OR findComparator expression)*)?
  private static boolean findCriterion_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriterion_2")) return false;
    findCriterion_2_0(builder_, level_ + 1);
    return true;
  }

  // findComparator? expression (KW_OR findComparator expression)*
  private static boolean findCriterion_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriterion_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = findCriterion_2_0_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && findCriterion_2_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // findComparator?
  private static boolean findCriterion_2_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriterion_2_0_0")) return false;
    findComparator(builder_, level_ + 1);
    return true;
  }

  // (KW_OR findComparator expression)*
  private static boolean findCriterion_2_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriterion_2_0_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!findCriterion_2_0_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "findCriterion_2_0_2", pos_)) break;
    }
    return true;
  }

  // KW_OR findComparator expression
  private static boolean findCriterion_2_0_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriterion_2_0_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_OR);
    result_ = result_ && findComparator(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_THRU expression (KW_BUT KW_NOT expression (KW_THRU expression)?)?)?
  private static boolean findCriterion_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriterion_3")) return false;
    findCriterion_3_0(builder_, level_ + 1);
    return true;
  }

  // KW_THRU expression (KW_BUT KW_NOT expression (KW_THRU expression)?)?
  private static boolean findCriterion_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriterion_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_THRU);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && findCriterion_3_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_BUT KW_NOT expression (KW_THRU expression)?)?
  private static boolean findCriterion_3_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriterion_3_0_2")) return false;
    findCriterion_3_0_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_BUT KW_NOT expression (KW_THRU expression)?
  private static boolean findCriterion_3_0_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriterion_3_0_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_BUT, KW_NOT);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && findCriterion_3_0_2_0_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_THRU expression)?
  private static boolean findCriterion_3_0_2_0_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriterion_3_0_2_0_3")) return false;
    findCriterion_3_0_2_0_3_0(builder_, level_ + 1);
    return true;
  }

  // KW_THRU expression
  private static boolean findCriterion_3_0_2_0_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findCriterion_3_0_2_0_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_THRU);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // (KW_FIRST | KW_NUMBER | KW_UNIQUE) | (KW_ALL | LPAREN expression RPAREN)?
  static boolean findModeClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findModeClause")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = findModeClause_0(builder_, level_ + 1);
    if (!result_) result_ = findModeClause_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_FIRST | KW_NUMBER | KW_UNIQUE
  private static boolean findModeClause_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findModeClause_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_FIRST);
    if (!result_) result_ = consumeToken(builder_, KW_NUMBER);
    if (!result_) result_ = consumeToken(builder_, KW_UNIQUE);
    return result_;
  }

  // (KW_ALL | LPAREN expression RPAREN)?
  private static boolean findModeClause_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findModeClause_1")) return false;
    findModeClause_1_0(builder_, level_ + 1);
    return true;
  }

  // KW_ALL | LPAREN expression RPAREN
  private static boolean findModeClause_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findModeClause_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_ALL);
    if (!result_) result_ = findModeClause_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // LPAREN expression RPAREN
  private static boolean findModeClause_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findModeClause_1_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // blockLabel? KW_FIND (KW_FIRST | KW_NUMBER | KW_UNIQUE)
  //                   KW_RECORDS? KW_IN? KW_FILE? identifier
  //                   passwordClause? cipherClause?
  //                   KW_WITH? (KW_LIMIT LPAREN expression RPAREN)? findCriteria?
  //                   coupledClause*
  //                   sortedByClause?
  //                   retainClause?
  //                   whereClause?
  public static boolean findStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FIND_STATEMENT, "<find statement>");
    result_ = findStatement_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_FIND);
    result_ = result_ && findStatement_2(builder_, level_ + 1);
    pinned_ = result_; // pin = 3
    result_ = result_ && report_error_(builder_, findStatement_3(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, findStatement_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findStatement_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, identifier(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findStatement_7(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findStatement_8(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findStatement_9(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findStatement_10(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findStatement_11(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findStatement_12(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findStatement_13(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, findStatement_14(builder_, level_ + 1)) && result_;
    result_ = pinned_ && findStatement_15(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // blockLabel?
  private static boolean findStatement_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_0")) return false;
    blockLabel(builder_, level_ + 1);
    return true;
  }

  // KW_FIRST | KW_NUMBER | KW_UNIQUE
  private static boolean findStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_2")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_FIRST);
    if (!result_) result_ = consumeToken(builder_, KW_NUMBER);
    if (!result_) result_ = consumeToken(builder_, KW_UNIQUE);
    return result_;
  }

  // KW_RECORDS?
  private static boolean findStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_3")) return false;
    consumeToken(builder_, KW_RECORDS);
    return true;
  }

  // KW_IN?
  private static boolean findStatement_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_4")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // KW_FILE?
  private static boolean findStatement_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_5")) return false;
    consumeToken(builder_, KW_FILE);
    return true;
  }

  // passwordClause?
  private static boolean findStatement_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_7")) return false;
    passwordClause(builder_, level_ + 1);
    return true;
  }

  // cipherClause?
  private static boolean findStatement_8(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_8")) return false;
    cipherClause(builder_, level_ + 1);
    return true;
  }

  // KW_WITH?
  private static boolean findStatement_9(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_9")) return false;
    consumeToken(builder_, KW_WITH);
    return true;
  }

  // (KW_LIMIT LPAREN expression RPAREN)?
  private static boolean findStatement_10(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_10")) return false;
    findStatement_10_0(builder_, level_ + 1);
    return true;
  }

  // KW_LIMIT LPAREN expression RPAREN
  private static boolean findStatement_10_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_10_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_LIMIT, LPAREN);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // findCriteria?
  private static boolean findStatement_11(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_11")) return false;
    findCriteria(builder_, level_ + 1);
    return true;
  }

  // coupledClause*
  private static boolean findStatement_12(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_12")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!coupledClause(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "findStatement_12", pos_)) break;
    }
    return true;
  }

  // sortedByClause?
  private static boolean findStatement_13(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_13")) return false;
    sortedByClause(builder_, level_ + 1);
    return true;
  }

  // retainClause?
  private static boolean findStatement_14(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_14")) return false;
    retainClause(builder_, level_ + 1);
    return true;
  }

  // whereClause?
  private static boolean findStatement_15(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "findStatement_15")) return false;
    whereClause(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // blockLabel? KW_FOR variableName (EQ_OP | ASSIGN_OP | KW_FROM)? expression KW_TO? expression (KW_STEP expression | KW_MAX expression | forStepArg)? statement* (KW_END_FOR | KW_END_ALL)?
  public static boolean forBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forBlock")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FOR_BLOCK, "<for block>");
    result_ = forBlock_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_FOR);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, variableName(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, forBlock_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, expression(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, forBlock_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, expression(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, forBlock_7(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, forBlock_8(builder_, level_ + 1)) && result_;
    result_ = pinned_ && forBlock_9(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // blockLabel?
  private static boolean forBlock_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forBlock_0")) return false;
    blockLabel(builder_, level_ + 1);
    return true;
  }

  // (EQ_OP | ASSIGN_OP | KW_FROM)?
  private static boolean forBlock_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forBlock_3")) return false;
    forBlock_3_0(builder_, level_ + 1);
    return true;
  }

  // EQ_OP | ASSIGN_OP | KW_FROM
  private static boolean forBlock_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forBlock_3_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, EQ_OP);
    if (!result_) result_ = consumeToken(builder_, ASSIGN_OP);
    if (!result_) result_ = consumeToken(builder_, KW_FROM);
    return result_;
  }

  // KW_TO?
  private static boolean forBlock_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forBlock_5")) return false;
    consumeToken(builder_, KW_TO);
    return true;
  }

  // (KW_STEP expression | KW_MAX expression | forStepArg)?
  private static boolean forBlock_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forBlock_7")) return false;
    forBlock_7_0(builder_, level_ + 1);
    return true;
  }

  // KW_STEP expression | KW_MAX expression | forStepArg
  private static boolean forBlock_7_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forBlock_7_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = forBlock_7_0_0(builder_, level_ + 1);
    if (!result_) result_ = forBlock_7_0_1(builder_, level_ + 1);
    if (!result_) result_ = forStepArg(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_STEP expression
  private static boolean forBlock_7_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forBlock_7_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_STEP);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_MAX expression
  private static boolean forBlock_7_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forBlock_7_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_MAX);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // statement*
  private static boolean forBlock_8(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forBlock_8")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "forBlock_8", pos_)) break;
    }
    return true;
  }

  // (KW_END_FOR | KW_END_ALL)?
  private static boolean forBlock_9(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forBlock_9")) return false;
    forBlock_9_0(builder_, level_ + 1);
    return true;
  }

  // KW_END_FOR | KW_END_ALL
  private static boolean forBlock_9_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forBlock_9_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_END_FOR);
    if (!result_) result_ = consumeToken(builder_, KW_END_ALL);
    return result_;
  }

  /* ********************************************************** */
  // MINUS? NUMBER | PLUS NUMBER
  static boolean forStepArg(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forStepArg")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = forStepArg_0(builder_, level_ + 1);
    if (!result_) result_ = parseTokens(builder_, 0, PLUS, NUMBER);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // MINUS? NUMBER
  private static boolean forStepArg_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forStepArg_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = forStepArg_0_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, NUMBER);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // MINUS?
  private static boolean forStepArg_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "forStepArg_0_0")) return false;
    consumeToken(builder_, MINUS);
    return true;
  }

  /* ********************************************************** */
  // KW_FORMAT (LPAREN (NUMBER | IDENTIFIER) RPAREN)? (sessionParameter EQ_OP sessionParamValue)+
  public static boolean formatStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "formatStatement")) return false;
    if (!nextTokenIs(builder_, KW_FORMAT)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FORMAT_STATEMENT, null);
    result_ = consumeToken(builder_, KW_FORMAT);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, formatStatement_1(builder_, level_ + 1));
    result_ = pinned_ && formatStatement_2(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (LPAREN (NUMBER | IDENTIFIER) RPAREN)?
  private static boolean formatStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "formatStatement_1")) return false;
    formatStatement_1_0(builder_, level_ + 1);
    return true;
  }

  // LPAREN (NUMBER | IDENTIFIER) RPAREN
  private static boolean formatStatement_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "formatStatement_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && formatStatement_1_0_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // NUMBER | IDENTIFIER
  private static boolean formatStatement_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "formatStatement_1_0_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    return result_;
  }

  // (sessionParameter EQ_OP sessionParamValue)+
  private static boolean formatStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "formatStatement_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = formatStatement_2_0(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!formatStatement_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "formatStatement_2", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // sessionParameter EQ_OP sessionParamValue
  private static boolean formatStatement_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "formatStatement_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = sessionParameter(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, EQ_OP);
    result_ = result_ && sessionParamValue(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // (identifier | systemFunctionName) LPAREN expression? ("," expression)* RPAREN
  public static boolean functionCall(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "functionCall")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FUNCTION_CALL, "<function call>");
    result_ = functionCall_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, LPAREN);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, functionCall_2(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, functionCall_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && consumeToken(builder_, RPAREN) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // identifier | systemFunctionName
  private static boolean functionCall_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "functionCall_0")) return false;
    boolean result_;
    result_ = identifier(builder_, level_ + 1);
    if (!result_) result_ = systemFunctionName(builder_, level_ + 1);
    return result_;
  }

  // expression?
  private static boolean functionCall_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "functionCall_2")) return false;
    expression(builder_, level_ + 1);
    return true;
  }

  // ("," expression)*
  private static boolean functionCall_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "functionCall_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!functionCall_3_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "functionCall_3", pos_)) break;
    }
    return true;
  }

  // "," expression
  private static boolean functionCall_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "functionCall_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // !statement expression
  static boolean getArg(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getArg")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = getArg_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean getArg_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getArg_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // blockLabel? KW_GET (KW_SAME (LPAREN (blockLabel | NUMBER | IDENTIFIER) RPAREN)?
  //                                               | KW_TRANSACTION? KW_DATA? KW_VARIABLE? KW_IN? KW_FILE? identifier (KW_ISN getArg? | getArg)?)
  public static boolean getStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, GET_STATEMENT, "<get statement>");
    result_ = getStatement_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_GET);
    pinned_ = result_; // pin = 2
    result_ = result_ && getStatement_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // blockLabel?
  private static boolean getStatement_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_0")) return false;
    blockLabel(builder_, level_ + 1);
    return true;
  }

  // KW_SAME (LPAREN (blockLabel | NUMBER | IDENTIFIER) RPAREN)?
  //                                               | KW_TRANSACTION? KW_DATA? KW_VARIABLE? KW_IN? KW_FILE? identifier (KW_ISN getArg? | getArg)?
  private static boolean getStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = getStatement_2_0(builder_, level_ + 1);
    if (!result_) result_ = getStatement_2_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_SAME (LPAREN (blockLabel | NUMBER | IDENTIFIER) RPAREN)?
  private static boolean getStatement_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_SAME);
    result_ = result_ && getStatement_2_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (LPAREN (blockLabel | NUMBER | IDENTIFIER) RPAREN)?
  private static boolean getStatement_2_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2_0_1")) return false;
    getStatement_2_0_1_0(builder_, level_ + 1);
    return true;
  }

  // LPAREN (blockLabel | NUMBER | IDENTIFIER) RPAREN
  private static boolean getStatement_2_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && getStatement_2_0_1_0_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // blockLabel | NUMBER | IDENTIFIER
  private static boolean getStatement_2_0_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2_0_1_0_1")) return false;
    boolean result_;
    result_ = blockLabel(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    return result_;
  }

  // KW_TRANSACTION? KW_DATA? KW_VARIABLE? KW_IN? KW_FILE? identifier (KW_ISN getArg? | getArg)?
  private static boolean getStatement_2_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = getStatement_2_1_0(builder_, level_ + 1);
    result_ = result_ && getStatement_2_1_1(builder_, level_ + 1);
    result_ = result_ && getStatement_2_1_2(builder_, level_ + 1);
    result_ = result_ && getStatement_2_1_3(builder_, level_ + 1);
    result_ = result_ && getStatement_2_1_4(builder_, level_ + 1);
    result_ = result_ && identifier(builder_, level_ + 1);
    result_ = result_ && getStatement_2_1_6(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_TRANSACTION?
  private static boolean getStatement_2_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2_1_0")) return false;
    consumeToken(builder_, KW_TRANSACTION);
    return true;
  }

  // KW_DATA?
  private static boolean getStatement_2_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2_1_1")) return false;
    consumeToken(builder_, KW_DATA);
    return true;
  }

  // KW_VARIABLE?
  private static boolean getStatement_2_1_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2_1_2")) return false;
    consumeToken(builder_, KW_VARIABLE);
    return true;
  }

  // KW_IN?
  private static boolean getStatement_2_1_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2_1_3")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // KW_FILE?
  private static boolean getStatement_2_1_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2_1_4")) return false;
    consumeToken(builder_, KW_FILE);
    return true;
  }

  // (KW_ISN getArg? | getArg)?
  private static boolean getStatement_2_1_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2_1_6")) return false;
    getStatement_2_1_6_0(builder_, level_ + 1);
    return true;
  }

  // KW_ISN getArg? | getArg
  private static boolean getStatement_2_1_6_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2_1_6_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = getStatement_2_1_6_0_0(builder_, level_ + 1);
    if (!result_) result_ = getArg(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_ISN getArg?
  private static boolean getStatement_2_1_6_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2_1_6_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_ISN);
    result_ = result_ && getStatement_2_1_6_0_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // getArg?
  private static boolean getStatement_2_1_6_0_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "getStatement_2_1_6_0_0_1")) return false;
    getArg(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // blockLabel? KW_HISTOGRAM (KW_ALL | LPAREN expression RPAREN)? KW_IN? identifier
  //                            passwordClause? cipherClause?
  //                            rangeSpecifications?
  //                            whereClause?
  //                            statement* (KW_END_HISTOGRAM | KW_END_ALL)?
  public static boolean histogramStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "histogramStatement")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, HISTOGRAM_STATEMENT, "<histogram statement>");
    result_ = histogramStatement_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_HISTOGRAM);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, histogramStatement_2(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, histogramStatement_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, identifier(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, histogramStatement_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, histogramStatement_6(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, histogramStatement_7(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, histogramStatement_8(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, histogramStatement_9(builder_, level_ + 1)) && result_;
    result_ = pinned_ && histogramStatement_10(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // blockLabel?
  private static boolean histogramStatement_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "histogramStatement_0")) return false;
    blockLabel(builder_, level_ + 1);
    return true;
  }

  // (KW_ALL | LPAREN expression RPAREN)?
  private static boolean histogramStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "histogramStatement_2")) return false;
    histogramStatement_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_ALL | LPAREN expression RPAREN
  private static boolean histogramStatement_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "histogramStatement_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_ALL);
    if (!result_) result_ = histogramStatement_2_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // LPAREN expression RPAREN
  private static boolean histogramStatement_2_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "histogramStatement_2_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_IN?
  private static boolean histogramStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "histogramStatement_3")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // passwordClause?
  private static boolean histogramStatement_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "histogramStatement_5")) return false;
    passwordClause(builder_, level_ + 1);
    return true;
  }

  // cipherClause?
  private static boolean histogramStatement_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "histogramStatement_6")) return false;
    cipherClause(builder_, level_ + 1);
    return true;
  }

  // rangeSpecifications?
  private static boolean histogramStatement_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "histogramStatement_7")) return false;
    rangeSpecifications(builder_, level_ + 1);
    return true;
  }

  // whereClause?
  private static boolean histogramStatement_8(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "histogramStatement_8")) return false;
    whereClause(builder_, level_ + 1);
    return true;
  }

  // statement*
  private static boolean histogramStatement_9(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "histogramStatement_9")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "histogramStatement_9", pos_)) break;
    }
    return true;
  }

  // (KW_END_HISTOGRAM | KW_END_ALL)?
  private static boolean histogramStatement_10(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "histogramStatement_10")) return false;
    histogramStatement_10_0(builder_, level_ + 1);
    return true;
  }

  // KW_END_HISTOGRAM | KW_END_ALL
  private static boolean histogramStatement_10_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "histogramStatement_10_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_END_HISTOGRAM);
    if (!result_) result_ = consumeToken(builder_, KW_END_ALL);
    return result_;
  }

  /* ********************************************************** */
  // systemVariables | ((USER_VARIABLE | IDENTIFIER DOT (IDENTIFIER | KW_ISN | KW_DELETE) | IDENTIFIER | SP_MS | keywordAsVarPrefix) (LPAREN NUMBER (COLON NUMBER)? RPAREN)?)
  static boolean identifier(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "identifier")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = systemVariables(builder_, level_ + 1);
    if (!result_) result_ = identifier_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (USER_VARIABLE | IDENTIFIER DOT (IDENTIFIER | KW_ISN | KW_DELETE) | IDENTIFIER | SP_MS | keywordAsVarPrefix) (LPAREN NUMBER (COLON NUMBER)? RPAREN)?
  private static boolean identifier_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "identifier_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = identifier_1_0(builder_, level_ + 1);
    result_ = result_ && identifier_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // USER_VARIABLE | IDENTIFIER DOT (IDENTIFIER | KW_ISN | KW_DELETE) | IDENTIFIER | SP_MS | keywordAsVarPrefix
  private static boolean identifier_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "identifier_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, USER_VARIABLE);
    if (!result_) result_ = identifier_1_0_1(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, SP_MS);
    if (!result_) result_ = keywordAsVarPrefix(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IDENTIFIER DOT (IDENTIFIER | KW_ISN | KW_DELETE)
  private static boolean identifier_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "identifier_1_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, IDENTIFIER, DOT);
    result_ = result_ && identifier_1_0_1_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IDENTIFIER | KW_ISN | KW_DELETE
  private static boolean identifier_1_0_1_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "identifier_1_0_1_2")) return false;
    boolean result_;
    result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, KW_ISN);
    if (!result_) result_ = consumeToken(builder_, KW_DELETE);
    return result_;
  }

  // (LPAREN NUMBER (COLON NUMBER)? RPAREN)?
  private static boolean identifier_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "identifier_1_1")) return false;
    identifier_1_1_0(builder_, level_ + 1);
    return true;
  }

  // LPAREN NUMBER (COLON NUMBER)? RPAREN
  private static boolean identifier_1_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "identifier_1_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, LPAREN, NUMBER);
    result_ = result_ && identifier_1_1_0_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (COLON NUMBER)?
  private static boolean identifier_1_1_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "identifier_1_1_0_2")) return false;
    identifier_1_1_0_2_0(builder_, level_ + 1);
    return true;
  }

  // COLON NUMBER
  private static boolean identifier_1_1_0_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "identifier_1_1_0_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, COLON, NUMBER);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_IF condition KW_THEN? statement* (KW_ELSE statement*)? KW_END_IF
  public static boolean ifBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifBlock")) return false;
    if (!nextTokenIs(builder_, KW_IF)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, IF_BLOCK, null);
    result_ = consumeToken(builder_, KW_IF);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, condition(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, ifBlock_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, ifBlock_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, ifBlock_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && consumeToken(builder_, KW_END_IF) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_THEN?
  private static boolean ifBlock_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifBlock_2")) return false;
    consumeToken(builder_, KW_THEN);
    return true;
  }

  // statement*
  private static boolean ifBlock_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifBlock_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "ifBlock_3", pos_)) break;
    }
    return true;
  }

  // (KW_ELSE statement*)?
  private static boolean ifBlock_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifBlock_4")) return false;
    ifBlock_4_0(builder_, level_ + 1);
    return true;
  }

  // KW_ELSE statement*
  private static boolean ifBlock_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifBlock_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_ELSE);
    result_ = result_ && ifBlock_4_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // statement*
  private static boolean ifBlock_4_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifBlock_4_0_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "ifBlock_4_0_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_IF KW_NO (KW_RECORDS | KW_RECORD)? KW_FOUND? (KW_ENTER | statement*) KW_END_NOREC
  public static boolean ifNoRecordsFoundClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifNoRecordsFoundClause")) return false;
    if (!nextTokenIs(builder_, KW_IF)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, IF_NO_RECORDS_FOUND_CLAUSE, null);
    result_ = consumeTokens(builder_, 2, KW_IF, KW_NO);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, ifNoRecordsFoundClause_2(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, ifNoRecordsFoundClause_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, ifNoRecordsFoundClause_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && consumeToken(builder_, KW_END_NOREC) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_RECORDS | KW_RECORD)?
  private static boolean ifNoRecordsFoundClause_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifNoRecordsFoundClause_2")) return false;
    ifNoRecordsFoundClause_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_RECORDS | KW_RECORD
  private static boolean ifNoRecordsFoundClause_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifNoRecordsFoundClause_2_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_RECORDS);
    if (!result_) result_ = consumeToken(builder_, KW_RECORD);
    return result_;
  }

  // KW_FOUND?
  private static boolean ifNoRecordsFoundClause_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifNoRecordsFoundClause_3")) return false;
    consumeToken(builder_, KW_FOUND);
    return true;
  }

  // KW_ENTER | statement*
  private static boolean ifNoRecordsFoundClause_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifNoRecordsFoundClause_4")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_ENTER);
    if (!result_) result_ = ifNoRecordsFoundClause_4_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // statement*
  private static boolean ifNoRecordsFoundClause_4_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifNoRecordsFoundClause_4_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "ifNoRecordsFoundClause_4_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_IGNORE
  public static boolean ignoreStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ignoreStatement")) return false;
    if (!nextTokenIs(builder_, KW_IGNORE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_IGNORE);
    exit_section_(builder_, marker_, IGNORE_STATEMENT, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_IN? KW_SHARED KW_HOLD (KW_MODE EQ_OP expression)?
  public static boolean inSharedHold(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inSharedHold")) return false;
    if (!nextTokenIs(builder_, "<in shared hold>", KW_IN, KW_SHARED)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, IN_SHARED_HOLD, "<in shared hold>");
    result_ = inSharedHold_0(builder_, level_ + 1);
    result_ = result_ && consumeTokens(builder_, 0, KW_SHARED, KW_HOLD);
    result_ = result_ && inSharedHold_3(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_IN?
  private static boolean inSharedHold_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inSharedHold_0")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // (KW_MODE EQ_OP expression)?
  private static boolean inSharedHold_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inSharedHold_3")) return false;
    inSharedHold_3_0(builder_, level_ + 1);
    return true;
  }

  // KW_MODE EQ_OP expression
  private static boolean inSharedHold_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inSharedHold_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_MODE, EQ_OP);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_INCDIC (IDENTIFIER | STRING_LITERAL)?
  public static boolean incdicStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "incdicStatement")) return false;
    if (!nextTokenIs(builder_, KW_INCDIC)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, INCDIC_STATEMENT, null);
    result_ = consumeToken(builder_, KW_INCDIC);
    pinned_ = result_; // pin = 1
    result_ = result_ && incdicStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (IDENTIFIER | STRING_LITERAL)?
  private static boolean incdicStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "incdicStatement_1")) return false;
    incdicStatement_1_0(builder_, level_ + 1);
    return true;
  }

  // IDENTIFIER | STRING_LITERAL
  private static boolean incdicStatement_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "incdicStatement_1_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, STRING_LITERAL);
    return result_;
  }

  /* ********************************************************** */
  // STRING_LITERAL | USER_VARIABLE | SYSTEM_VARIABLE | NUMBER | MINUS
  static boolean includeParam(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "includeParam")) return false;
    boolean result_;
    result_ = consumeToken(builder_, STRING_LITERAL);
    if (!result_) result_ = consumeToken(builder_, USER_VARIABLE);
    if (!result_) result_ = consumeToken(builder_, SYSTEM_VARIABLE);
    if (!result_) result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, MINUS);
    return result_;
  }

  /* ********************************************************** */
  // KW_INCLUDE IDENTIFIER includeParam*
  public static boolean includeStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "includeStatement")) return false;
    if (!nextTokenIs(builder_, KW_INCLUDE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, INCLUDE_STATEMENT, null);
    result_ = consumeTokens(builder_, 1, KW_INCLUDE, IDENTIFIER);
    pinned_ = result_; // pin = 1
    result_ = result_ && includeStatement_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // includeParam*
  private static boolean includeStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "includeStatement_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!includeParam(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "includeStatement_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (NUMBER | IDENTIFIER | STAR) (COLON (NUMBER | IDENTIFIER | STAR))?
  static boolean initIndexSpec(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initIndexSpec")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = initIndexSpec_0(builder_, level_ + 1);
    result_ = result_ && initIndexSpec_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // NUMBER | IDENTIFIER | STAR
  private static boolean initIndexSpec_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initIndexSpec_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, STAR);
    return result_;
  }

  // (COLON (NUMBER | IDENTIFIER | STAR))?
  private static boolean initIndexSpec_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initIndexSpec_1")) return false;
    initIndexSpec_1_0(builder_, level_ + 1);
    return true;
  }

  // COLON (NUMBER | IDENTIFIER | STAR)
  private static boolean initIndexSpec_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initIndexSpec_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COLON);
    result_ = result_ && initIndexSpec_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // NUMBER | IDENTIFIER | STAR
  private static boolean initIndexSpec_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initIndexSpec_1_0_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, STAR);
    return result_;
  }

  /* ********************************************************** */
  // LPAREN initIndexSpec (COMMA initIndexSpec)* RPAREN initValueGroup
  static boolean initIndexedValue(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initIndexedValue")) return false;
    if (!nextTokenIs(builder_, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && initIndexSpec(builder_, level_ + 1);
    result_ = result_ && initIndexedValue_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    result_ = result_ && initValueGroup(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (COMMA initIndexSpec)*
  private static boolean initIndexedValue_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initIndexedValue_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!initIndexedValue_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "initIndexedValue_2", pos_)) break;
    }
    return true;
  }

  // COMMA initIndexSpec
  private static boolean initIndexedValue_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initIndexedValue_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && initIndexSpec(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_FULL KW_LENGTH? NUMBER? | KW_LENGTH NUMBER?
  static boolean initLengthSpec(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initLengthSpec")) return false;
    if (!nextTokenIs(builder_, "", KW_FULL, KW_LENGTH)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = initLengthSpec_0(builder_, level_ + 1);
    if (!result_) result_ = initLengthSpec_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_FULL KW_LENGTH? NUMBER?
  private static boolean initLengthSpec_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initLengthSpec_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_FULL);
    result_ = result_ && initLengthSpec_0_1(builder_, level_ + 1);
    result_ = result_ && initLengthSpec_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_LENGTH?
  private static boolean initLengthSpec_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initLengthSpec_0_1")) return false;
    consumeToken(builder_, KW_LENGTH);
    return true;
  }

  // NUMBER?
  private static boolean initLengthSpec_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initLengthSpec_0_2")) return false;
    consumeToken(builder_, NUMBER);
    return true;
  }

  // KW_LENGTH NUMBER?
  private static boolean initLengthSpec_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initLengthSpec_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_LENGTH);
    result_ = result_ && initLengthSpec_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // NUMBER?
  private static boolean initLengthSpec_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initLengthSpec_1_1")) return false;
    consumeToken(builder_, NUMBER);
    return true;
  }

  /* ********************************************************** */
  // writeParenBlock | expression
  static boolean initValue(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initValue")) return false;
    boolean result_;
    result_ = writeParenBlock(builder_, level_ + 1);
    if (!result_) result_ = expression(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // "<" initValue? ("," initValue?)* ">"
  static boolean initValueGroup(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initValueGroup")) return false;
    if (!nextTokenIs(builder_, LT_OP)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LT_OP);
    result_ = result_ && initValueGroup_1(builder_, level_ + 1);
    result_ = result_ && initValueGroup_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, GT_OP);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // initValue?
  private static boolean initValueGroup_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initValueGroup_1")) return false;
    initValue(builder_, level_ + 1);
    return true;
  }

  // ("," initValue?)*
  private static boolean initValueGroup_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initValueGroup_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!initValueGroup_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "initValueGroup_2", pos_)) break;
    }
    return true;
  }

  // "," initValue?
  private static boolean initValueGroup_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initValueGroup_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && initValueGroup_2_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // initValue?
  private static boolean initValueGroup_2_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "initValueGroup_2_0_1")) return false;
    initValue(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // KW_AND? KW_SOUND? KW_ALARM
  public static boolean inputAlarmClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputAlarmClause")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, INPUT_ALARM_CLAUSE, "<input alarm clause>");
    result_ = inputAlarmClause_0(builder_, level_ + 1);
    result_ = result_ && inputAlarmClause_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_ALARM);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_AND?
  private static boolean inputAlarmClause_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputAlarmClause_0")) return false;
    consumeToken(builder_, KW_AND);
    return true;
  }

  // KW_SOUND?
  private static boolean inputAlarmClause_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputAlarmClause_1")) return false;
    consumeToken(builder_, KW_SOUND);
    return true;
  }

  /* ********************************************************** */
  // !statement (COMMA | writeParenBlock | SLASH | expression)
  static boolean inputArg(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputArg")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = inputArg_0(builder_, level_ + 1);
    result_ = result_ && inputArg_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean inputArg_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputArg_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // COMMA | writeParenBlock | SLASH | expression
  private static boolean inputArg_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputArg_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, COMMA);
    if (!result_) result_ = writeParenBlock(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, SLASH);
    if (!result_) result_ = expression(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // KW_MARK (KW_POSITION expression KW_IN expression | inputArg)
  public static boolean inputMarkClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputMarkClause")) return false;
    if (!nextTokenIs(builder_, KW_MARK)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, INPUT_MARK_CLAUSE, null);
    result_ = consumeToken(builder_, KW_MARK);
    pinned_ = result_; // pin = 1
    result_ = result_ && inputMarkClause_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_POSITION expression KW_IN expression | inputArg
  private static boolean inputMarkClause_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputMarkClause_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = inputMarkClause_1_0(builder_, level_ + 1);
    if (!result_) result_ = inputArg(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_POSITION expression KW_IN expression
  private static boolean inputMarkClause_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputMarkClause_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_POSITION);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_IN);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_INPUT
  //                    inputWindowClause?
  //                    (KW_NO KW_ERASE)?
  //                    writeGlobalParams?
  //                    inputWithTextClause?
  //                    inputMarkClause?
  //                    inputAlarmClause?
  //                    (inputUsingMapBody | inputArg*)
  public static boolean inputStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputStatement")) return false;
    if (!nextTokenIs(builder_, KW_INPUT)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, INPUT_STATEMENT, null);
    result_ = consumeToken(builder_, KW_INPUT);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, inputStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, inputStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, inputStatement_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, inputStatement_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, inputStatement_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, inputStatement_6(builder_, level_ + 1)) && result_;
    result_ = pinned_ && inputStatement_7(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // inputWindowClause?
  private static boolean inputStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputStatement_1")) return false;
    inputWindowClause(builder_, level_ + 1);
    return true;
  }

  // (KW_NO KW_ERASE)?
  private static boolean inputStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputStatement_2")) return false;
    inputStatement_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_NO KW_ERASE
  private static boolean inputStatement_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputStatement_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_NO, KW_ERASE);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // writeGlobalParams?
  private static boolean inputStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputStatement_3")) return false;
    writeGlobalParams(builder_, level_ + 1);
    return true;
  }

  // inputWithTextClause?
  private static boolean inputStatement_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputStatement_4")) return false;
    inputWithTextClause(builder_, level_ + 1);
    return true;
  }

  // inputMarkClause?
  private static boolean inputStatement_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputStatement_5")) return false;
    inputMarkClause(builder_, level_ + 1);
    return true;
  }

  // inputAlarmClause?
  private static boolean inputStatement_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputStatement_6")) return false;
    inputAlarmClause(builder_, level_ + 1);
    return true;
  }

  // inputUsingMapBody | inputArg*
  private static boolean inputStatement_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputStatement_7")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = inputUsingMapBody(builder_, level_ + 1);
    if (!result_) result_ = inputStatement_7_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // inputArg*
  private static boolean inputStatement_7_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputStatement_7_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!inputArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "inputStatement_7_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_USING? KW_MAP expression (KW_NO KW_ERASE)? inputArg* (KW_NO KW_PARAMETER)?
  static boolean inputUsingMapBody(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputUsingMapBody")) return false;
    if (!nextTokenIs(builder_, "", KW_MAP, KW_USING)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = inputUsingMapBody_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_MAP);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && inputUsingMapBody_3(builder_, level_ + 1);
    result_ = result_ && inputUsingMapBody_4(builder_, level_ + 1);
    result_ = result_ && inputUsingMapBody_5(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_USING?
  private static boolean inputUsingMapBody_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputUsingMapBody_0")) return false;
    consumeToken(builder_, KW_USING);
    return true;
  }

  // (KW_NO KW_ERASE)?
  private static boolean inputUsingMapBody_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputUsingMapBody_3")) return false;
    inputUsingMapBody_3_0(builder_, level_ + 1);
    return true;
  }

  // KW_NO KW_ERASE
  private static boolean inputUsingMapBody_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputUsingMapBody_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_NO, KW_ERASE);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // inputArg*
  private static boolean inputUsingMapBody_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputUsingMapBody_4")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!inputArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "inputUsingMapBody_4", pos_)) break;
    }
    return true;
  }

  // (KW_NO KW_PARAMETER)?
  private static boolean inputUsingMapBody_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputUsingMapBody_5")) return false;
    inputUsingMapBody_5_0(builder_, level_ + 1);
    return true;
  }

  // KW_NO KW_PARAMETER
  private static boolean inputUsingMapBody_5_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputUsingMapBody_5_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_NO, KW_PARAMETER);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_WINDOW EQ_OP STRING_LITERAL
  public static boolean inputWindowClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputWindowClause")) return false;
    if (!nextTokenIs(builder_, KW_WINDOW)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, INPUT_WINDOW_CLAUSE, null);
    result_ = consumeTokens(builder_, 1, KW_WINDOW, EQ_OP, STRING_LITERAL);
    pinned_ = result_; // pin = 1
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // KW_WITH? KW_TEXT inputArg+
  public static boolean inputWithTextClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputWithTextClause")) return false;
    if (!nextTokenIs(builder_, "<input with text clause>", KW_TEXT, KW_WITH)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, INPUT_WITH_TEXT_CLAUSE, "<input with text clause>");
    result_ = inputWithTextClause_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_TEXT);
    pinned_ = result_; // pin = 2
    result_ = result_ && inputWithTextClause_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_WITH?
  private static boolean inputWithTextClause_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputWithTextClause_0")) return false;
    consumeToken(builder_, KW_WITH);
    return true;
  }

  // inputArg+
  private static boolean inputWithTextClause_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "inputWithTextClause_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = inputArg(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!inputArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "inputWithTextClause_2", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_STEP | KW_LENGTH | KW_NAME | KW_NUMBER | KW_TEXT | KW_TYPE | KW_SET | KW_MODE | KW_FRAMED | KW_GIVE | KW_LEFT | KW_RIGHT | KW_TOP | KW_ON | KW_OFF | KW_POSITION | KW_COMMAND
  //                              | KW_DATA | KW_AS | KW_MAX | KW_MIN | KW_COUNT | KW_SUM | KW_TOTAL | KW_THEM | KW_RECORD | KW_KEYS
  //                              | KW_START | KW_PAGE | KW_FULL | KW_ISN | KW_NE | KW_EQ | KW_GT | KW_LT | KW_GE | KW_LE
  //                              | KW_JUSTIFIED | KW_ONCE | KW_BOTTOM | KW_MAP | KW_INDEX | KW_DELETE | KW_SCAN | KW_FIRST | KW_HELP
  //                              | KW_IN | KW_KEY | KW_SAME | KW_SELECT | KW_FILE | KW_LINES | KW_RESULT
  static boolean keywordAsVarPrefix(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "keywordAsVarPrefix")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_STEP);
    if (!result_) result_ = consumeToken(builder_, KW_LENGTH);
    if (!result_) result_ = consumeToken(builder_, KW_NAME);
    if (!result_) result_ = consumeToken(builder_, KW_NUMBER);
    if (!result_) result_ = consumeToken(builder_, KW_TEXT);
    if (!result_) result_ = consumeToken(builder_, KW_TYPE);
    if (!result_) result_ = consumeToken(builder_, KW_SET);
    if (!result_) result_ = consumeToken(builder_, KW_MODE);
    if (!result_) result_ = consumeToken(builder_, KW_FRAMED);
    if (!result_) result_ = consumeToken(builder_, KW_GIVE);
    if (!result_) result_ = consumeToken(builder_, KW_LEFT);
    if (!result_) result_ = consumeToken(builder_, KW_RIGHT);
    if (!result_) result_ = consumeToken(builder_, KW_TOP);
    if (!result_) result_ = consumeToken(builder_, KW_ON);
    if (!result_) result_ = consumeToken(builder_, KW_OFF);
    if (!result_) result_ = consumeToken(builder_, KW_POSITION);
    if (!result_) result_ = consumeToken(builder_, KW_COMMAND);
    if (!result_) result_ = consumeToken(builder_, KW_DATA);
    if (!result_) result_ = consumeToken(builder_, KW_AS);
    if (!result_) result_ = consumeToken(builder_, KW_MAX);
    if (!result_) result_ = consumeToken(builder_, KW_MIN);
    if (!result_) result_ = consumeToken(builder_, KW_COUNT);
    if (!result_) result_ = consumeToken(builder_, KW_SUM);
    if (!result_) result_ = consumeToken(builder_, KW_TOTAL);
    if (!result_) result_ = consumeToken(builder_, KW_THEM);
    if (!result_) result_ = consumeToken(builder_, KW_RECORD);
    if (!result_) result_ = consumeToken(builder_, KW_KEYS);
    if (!result_) result_ = consumeToken(builder_, KW_START);
    if (!result_) result_ = consumeToken(builder_, KW_PAGE);
    if (!result_) result_ = consumeToken(builder_, KW_FULL);
    if (!result_) result_ = consumeToken(builder_, KW_ISN);
    if (!result_) result_ = consumeToken(builder_, KW_NE);
    if (!result_) result_ = consumeToken(builder_, KW_EQ);
    if (!result_) result_ = consumeToken(builder_, KW_GT);
    if (!result_) result_ = consumeToken(builder_, KW_LT);
    if (!result_) result_ = consumeToken(builder_, KW_GE);
    if (!result_) result_ = consumeToken(builder_, KW_LE);
    if (!result_) result_ = consumeToken(builder_, KW_JUSTIFIED);
    if (!result_) result_ = consumeToken(builder_, KW_ONCE);
    if (!result_) result_ = consumeToken(builder_, KW_BOTTOM);
    if (!result_) result_ = consumeToken(builder_, KW_MAP);
    if (!result_) result_ = consumeToken(builder_, KW_INDEX);
    if (!result_) result_ = consumeToken(builder_, KW_DELETE);
    if (!result_) result_ = consumeToken(builder_, KW_SCAN);
    if (!result_) result_ = consumeToken(builder_, KW_FIRST);
    if (!result_) result_ = consumeToken(builder_, KW_HELP);
    if (!result_) result_ = consumeToken(builder_, KW_IN);
    if (!result_) result_ = consumeToken(builder_, KW_KEY);
    if (!result_) result_ = consumeToken(builder_, KW_SAME);
    if (!result_) result_ = consumeToken(builder_, KW_SELECT);
    if (!result_) result_ = consumeToken(builder_, KW_FILE);
    if (!result_) result_ = consumeToken(builder_, KW_LINES);
    if (!result_) result_ = consumeToken(builder_, KW_RESULT);
    return result_;
  }

  /* ********************************************************** */
  // LT_OP | KW_LESS KW_THAN | GT_OP | KW_GREATER KW_THAN | LE_OP | KW_LESS KW_EQUAL | GE_OP | KW_GREATER KW_EQUAL
  public static boolean lessEqualOrGreaterClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "lessEqualOrGreaterClause")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, LESS_EQUAL_OR_GREATER_CLAUSE, "<less equal or greater clause>");
    result_ = consumeToken(builder_, LT_OP);
    if (!result_) result_ = parseTokens(builder_, 0, KW_LESS, KW_THAN);
    if (!result_) result_ = consumeToken(builder_, GT_OP);
    if (!result_) result_ = parseTokens(builder_, 0, KW_GREATER, KW_THAN);
    if (!result_) result_ = consumeToken(builder_, LE_OP);
    if (!result_) result_ = parseTokens(builder_, 0, KW_LESS, KW_EQUAL);
    if (!result_) result_ = consumeToken(builder_, GE_OP);
    if (!result_) result_ = parseTokens(builder_, 0, KW_GREATER, KW_EQUAL);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // STRING_LITERAL | NUMBER | (DOT NUMBER) | DATE_LITERAL | TIME_LITERAL | TIMESTAMP_LITERAL
  //   | HEX_LITERAL | UNICODE_HEX_LITERAL | KW_TRUE | KW_FALSE
  //   | dataType
  static boolean literal(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "literal")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, STRING_LITERAL);
    if (!result_) result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = literal_2(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, DATE_LITERAL);
    if (!result_) result_ = consumeToken(builder_, TIME_LITERAL);
    if (!result_) result_ = consumeToken(builder_, TIMESTAMP_LITERAL);
    if (!result_) result_ = consumeToken(builder_, HEX_LITERAL);
    if (!result_) result_ = consumeToken(builder_, UNICODE_HEX_LITERAL);
    if (!result_) result_ = consumeToken(builder_, KW_TRUE);
    if (!result_) result_ = consumeToken(builder_, KW_FALSE);
    if (!result_) result_ = dataType(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // DOT NUMBER
  private static boolean literal_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "literal_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, DOT, NUMBER);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_MARK writeArg*
  public static boolean markStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "markStatement")) return false;
    if (!nextTokenIs(builder_, KW_MARK)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, MARK_STATEMENT, null);
    result_ = consumeToken(builder_, KW_MARK);
    pinned_ = result_; // pin = 1
    result_ = result_ && markStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // writeArg*
  private static boolean markStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "markStatement_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "markStatement_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // STRING_LITERAL | IDENTIFIER | NUMBER | COMMA | DOT | PLUS | MINUS | STAR | SLASH
  static boolean maskAtom(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "maskAtom")) return false;
    boolean result_;
    result_ = consumeToken(builder_, STRING_LITERAL);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, COMMA);
    if (!result_) result_ = consumeToken(builder_, DOT);
    if (!result_) result_ = consumeToken(builder_, PLUS);
    if (!result_) result_ = consumeToken(builder_, MINUS);
    if (!result_) result_ = consumeToken(builder_, STAR);
    if (!result_) result_ = consumeToken(builder_, SLASH);
    return result_;
  }

  /* ********************************************************** */
  // KW_MASK LPAREN maskAtom+ RPAREN (IDENTIFIER !ASSIGN_OP !LPAREN !DOT)?
  public static boolean maskExpression(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "maskExpression")) return false;
    if (!nextTokenIs(builder_, KW_MASK)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, MASK_EXPRESSION, null);
    result_ = consumeTokens(builder_, 1, KW_MASK, LPAREN);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, maskExpression_2(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, consumeToken(builder_, RPAREN)) && result_;
    result_ = pinned_ && maskExpression_4(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // maskAtom+
  private static boolean maskExpression_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "maskExpression_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = maskAtom(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!maskAtom(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "maskExpression_2", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (IDENTIFIER !ASSIGN_OP !LPAREN !DOT)?
  private static boolean maskExpression_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "maskExpression_4")) return false;
    maskExpression_4_0(builder_, level_ + 1);
    return true;
  }

  // IDENTIFIER !ASSIGN_OP !LPAREN !DOT
  private static boolean maskExpression_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "maskExpression_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IDENTIFIER);
    result_ = result_ && maskExpression_4_0_1(builder_, level_ + 1);
    result_ = result_ && maskExpression_4_0_2(builder_, level_ + 1);
    result_ = result_ && maskExpression_4_0_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !ASSIGN_OP
  private static boolean maskExpression_4_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "maskExpression_4_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, ASSIGN_OP);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !LPAREN
  private static boolean maskExpression_4_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "maskExpression_4_0_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, LPAREN);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !DOT
  private static boolean maskExpression_4_0_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "maskExpression_4_0_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, DOT);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // KW_ALL expression KW_TO moveTarget+ (KW_UNTIL expression)?
  static boolean moveAllBody(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveAllBody")) return false;
    if (!nextTokenIs(builder_, KW_ALL)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = consumeToken(builder_, KW_ALL);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, expression(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, consumeToken(builder_, KW_TO)) && result_;
    result_ = pinned_ && report_error_(builder_, moveAllBody_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && moveAllBody_4(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // moveTarget+
  private static boolean moveAllBody_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveAllBody_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = moveTarget(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!moveTarget(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "moveAllBody_3", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_UNTIL expression)?
  private static boolean moveAllBody_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveAllBody_4")) return false;
    moveAllBody_4_0(builder_, level_ + 1);
    return true;
  }

  // KW_UNTIL expression
  private static boolean moveAllBody_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveAllBody_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_UNTIL);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_BY (KW_NAME | KW_POSITION) | KW_EDITED | KW_ROUNDED | (KW_LEFT | KW_RIGHT) KW_JUSTIFIED? | KW_NORMALIZED | KW_ENCODED
  static boolean moveModifier(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveModifier")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = moveModifier_0(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, KW_EDITED);
    if (!result_) result_ = consumeToken(builder_, KW_ROUNDED);
    if (!result_) result_ = moveModifier_3(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, KW_NORMALIZED);
    if (!result_) result_ = consumeToken(builder_, KW_ENCODED);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_BY (KW_NAME | KW_POSITION)
  private static boolean moveModifier_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveModifier_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_BY);
    result_ = result_ && moveModifier_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_NAME | KW_POSITION
  private static boolean moveModifier_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveModifier_0_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_NAME);
    if (!result_) result_ = consumeToken(builder_, KW_POSITION);
    return result_;
  }

  // (KW_LEFT | KW_RIGHT) KW_JUSTIFIED?
  private static boolean moveModifier_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveModifier_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = moveModifier_3_0(builder_, level_ + 1);
    result_ = result_ && moveModifier_3_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_LEFT | KW_RIGHT
  private static boolean moveModifier_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveModifier_3_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_LEFT);
    if (!result_) result_ = consumeToken(builder_, KW_RIGHT);
    return result_;
  }

  // KW_JUSTIFIED?
  private static boolean moveModifier_3_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveModifier_3_1")) return false;
    consumeToken(builder_, KW_JUSTIFIED);
    return true;
  }

  /* ********************************************************** */
  // moveModifier? (expression sessionParameterSpec? | sessionParameterSpec) KW_TO moveTarget+
  static boolean moveRegularBody(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveRegularBody")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = moveRegularBody_0(builder_, level_ + 1);
    result_ = result_ && moveRegularBody_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_TO);
    result_ = result_ && moveRegularBody_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // moveModifier?
  private static boolean moveRegularBody_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveRegularBody_0")) return false;
    moveModifier(builder_, level_ + 1);
    return true;
  }

  // expression sessionParameterSpec? | sessionParameterSpec
  private static boolean moveRegularBody_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveRegularBody_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = moveRegularBody_1_0(builder_, level_ + 1);
    if (!result_) result_ = sessionParameterSpec(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // expression sessionParameterSpec?
  private static boolean moveRegularBody_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveRegularBody_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1);
    result_ = result_ && moveRegularBody_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // sessionParameterSpec?
  private static boolean moveRegularBody_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveRegularBody_1_0_1")) return false;
    sessionParameterSpec(builder_, level_ + 1);
    return true;
  }

  // moveTarget+
  private static boolean moveRegularBody_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveRegularBody_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = moveTarget(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!moveTarget(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "moveRegularBody_3", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_MOVE (moveAllBody | moveRegularBody)
  public static boolean moveStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveStatement")) return false;
    if (!nextTokenIs(builder_, KW_MOVE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, MOVE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_MOVE);
    pinned_ = result_; // pin = 1
    result_ = result_ && moveStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // moveAllBody | moveRegularBody
  private static boolean moveStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveStatement_1")) return false;
    boolean result_;
    result_ = moveAllBody(builder_, level_ + 1);
    if (!result_) result_ = moveRegularBody(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // !statement expression writeParenBlock?
  static boolean moveTarget(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveTarget")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = moveTarget_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && moveTarget_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean moveTarget_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveTarget_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // writeParenBlock?
  private static boolean moveTarget_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "moveTarget_2")) return false;
    writeParenBlock(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // unaryExpr ((STAR | SLASH | DOUBLE_STAR) unaryExpr | SYSTEM_VARIABLE !DOT !ASSIGN_OP)*
  public static boolean mulExpr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "mulExpr")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, MUL_EXPR, "<mul expr>");
    result_ = unaryExpr(builder_, level_ + 1);
    result_ = result_ && mulExpr_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // ((STAR | SLASH | DOUBLE_STAR) unaryExpr | SYSTEM_VARIABLE !DOT !ASSIGN_OP)*
  private static boolean mulExpr_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "mulExpr_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!mulExpr_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "mulExpr_1", pos_)) break;
    }
    return true;
  }

  // (STAR | SLASH | DOUBLE_STAR) unaryExpr | SYSTEM_VARIABLE !DOT !ASSIGN_OP
  private static boolean mulExpr_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "mulExpr_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = mulExpr_1_0_0(builder_, level_ + 1);
    if (!result_) result_ = mulExpr_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (STAR | SLASH | DOUBLE_STAR) unaryExpr
  private static boolean mulExpr_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "mulExpr_1_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = mulExpr_1_0_0_0(builder_, level_ + 1);
    result_ = result_ && unaryExpr(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // STAR | SLASH | DOUBLE_STAR
  private static boolean mulExpr_1_0_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "mulExpr_1_0_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, STAR);
    if (!result_) result_ = consumeToken(builder_, SLASH);
    if (!result_) result_ = consumeToken(builder_, DOUBLE_STAR);
    return result_;
  }

  // SYSTEM_VARIABLE !DOT !ASSIGN_OP
  private static boolean mulExpr_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "mulExpr_1_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, SYSTEM_VARIABLE);
    result_ = result_ && mulExpr_1_0_1_1(builder_, level_ + 1);
    result_ = result_ && mulExpr_1_0_1_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !DOT
  private static boolean mulExpr_1_0_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "mulExpr_1_0_1_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, DOT);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !ASSIGN_OP
  private static boolean mulExpr_1_0_1_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "mulExpr_1_0_1_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, ASSIGN_OP);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // KW_MULTI_FETCH (KW_ON | KW_OFF | KW_OF? expression)
  public static boolean multiFetchClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "multiFetchClause")) return false;
    if (!nextTokenIs(builder_, KW_MULTI_FETCH)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, MULTI_FETCH_CLAUSE, null);
    result_ = consumeToken(builder_, KW_MULTI_FETCH);
    pinned_ = result_; // pin = 1
    result_ = result_ && multiFetchClause_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_ON | KW_OFF | KW_OF? expression
  private static boolean multiFetchClause_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "multiFetchClause_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_ON);
    if (!result_) result_ = consumeToken(builder_, KW_OFF);
    if (!result_) result_ = multiFetchClause_1_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_OF? expression
  private static boolean multiFetchClause_1_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "multiFetchClause_1_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = multiFetchClause_1_2_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_OF?
  private static boolean multiFetchClause_1_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "multiFetchClause_1_2_0")) return false;
    consumeToken(builder_, KW_OF);
    return true;
  }

  /* ********************************************************** */
  // KW_MULTIPLY expression KW_BY expression (KW_GIVING expression)?
  public static boolean multiplyStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "multiplyStatement")) return false;
    if (!nextTokenIs(builder_, KW_MULTIPLY)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, MULTIPLY_STATEMENT, null);
    result_ = consumeToken(builder_, KW_MULTIPLY);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, expression(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, consumeToken(builder_, KW_BY)) && result_;
    result_ = pinned_ && report_error_(builder_, expression(builder_, level_ + 1)) && result_;
    result_ = pinned_ && multiplyStatement_4(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_GIVING expression)?
  private static boolean multiplyStatement_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "multiplyStatement_4")) return false;
    multiplyStatement_4_0(builder_, level_ + 1);
    return true;
  }

  // KW_GIVING expression
  private static boolean multiplyStatement_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "multiplyStatement_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_GIVING);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // optionsStatement* defineDataPhase? normalCodeLine_* KW_END?
  static boolean naturalFile(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "naturalFile")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = naturalFile_0(builder_, level_ + 1);
    result_ = result_ && naturalFile_1(builder_, level_ + 1);
    result_ = result_ && naturalFile_2(builder_, level_ + 1);
    result_ = result_ && naturalFile_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // optionsStatement*
  private static boolean naturalFile_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "naturalFile_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!optionsStatement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "naturalFile_0", pos_)) break;
    }
    return true;
  }

  // defineDataPhase?
  private static boolean naturalFile_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "naturalFile_1")) return false;
    defineDataPhase(builder_, level_ + 1);
    return true;
  }

  // normalCodeLine_*
  private static boolean naturalFile_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "naturalFile_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!normalCodeLine_(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "naturalFile_2", pos_)) break;
    }
    return true;
  }

  // KW_END?
  private static boolean naturalFile_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "naturalFile_3")) return false;
    consumeToken(builder_, KW_END);
    return true;
  }

  /* ********************************************************** */
  // KW_NEWPAGE (LPAREN (NUMBER | IDENTIFIER | SUBST_PARAM) RPAREN | SUBST_PARAM)?
  //                          (KW_IF? KW_LESS KW_THAN? expression KW_LINES? KW_LEFT?
  //                          | IDENTIFIER KW_IF KW_TOP KW_OF? KW_PAGE
  //                          | KW_WHEN KW_LESS KW_THAN? expression KW_LINES? KW_LEFT?)?
  //                          writeArg*
  public static boolean newpageStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement")) return false;
    if (!nextTokenIs(builder_, KW_NEWPAGE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, NEWPAGE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_NEWPAGE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, newpageStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, newpageStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && newpageStatement_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (LPAREN (NUMBER | IDENTIFIER | SUBST_PARAM) RPAREN | SUBST_PARAM)?
  private static boolean newpageStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_1")) return false;
    newpageStatement_1_0(builder_, level_ + 1);
    return true;
  }

  // LPAREN (NUMBER | IDENTIFIER | SUBST_PARAM) RPAREN | SUBST_PARAM
  private static boolean newpageStatement_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = newpageStatement_1_0_0(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, SUBST_PARAM);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // LPAREN (NUMBER | IDENTIFIER | SUBST_PARAM) RPAREN
  private static boolean newpageStatement_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_1_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && newpageStatement_1_0_0_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // NUMBER | IDENTIFIER | SUBST_PARAM
  private static boolean newpageStatement_1_0_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_1_0_0_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, SUBST_PARAM);
    return result_;
  }

  // (KW_IF? KW_LESS KW_THAN? expression KW_LINES? KW_LEFT?
  //                          | IDENTIFIER KW_IF KW_TOP KW_OF? KW_PAGE
  //                          | KW_WHEN KW_LESS KW_THAN? expression KW_LINES? KW_LEFT?)?
  private static boolean newpageStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_2")) return false;
    newpageStatement_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_IF? KW_LESS KW_THAN? expression KW_LINES? KW_LEFT?
  //                          | IDENTIFIER KW_IF KW_TOP KW_OF? KW_PAGE
  //                          | KW_WHEN KW_LESS KW_THAN? expression KW_LINES? KW_LEFT?
  private static boolean newpageStatement_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = newpageStatement_2_0_0(builder_, level_ + 1);
    if (!result_) result_ = newpageStatement_2_0_1(builder_, level_ + 1);
    if (!result_) result_ = newpageStatement_2_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_IF? KW_LESS KW_THAN? expression KW_LINES? KW_LEFT?
  private static boolean newpageStatement_2_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_2_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = newpageStatement_2_0_0_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_LESS);
    result_ = result_ && newpageStatement_2_0_0_2(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && newpageStatement_2_0_0_4(builder_, level_ + 1);
    result_ = result_ && newpageStatement_2_0_0_5(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_IF?
  private static boolean newpageStatement_2_0_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_2_0_0_0")) return false;
    consumeToken(builder_, KW_IF);
    return true;
  }

  // KW_THAN?
  private static boolean newpageStatement_2_0_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_2_0_0_2")) return false;
    consumeToken(builder_, KW_THAN);
    return true;
  }

  // KW_LINES?
  private static boolean newpageStatement_2_0_0_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_2_0_0_4")) return false;
    consumeToken(builder_, KW_LINES);
    return true;
  }

  // KW_LEFT?
  private static boolean newpageStatement_2_0_0_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_2_0_0_5")) return false;
    consumeToken(builder_, KW_LEFT);
    return true;
  }

  // IDENTIFIER KW_IF KW_TOP KW_OF? KW_PAGE
  private static boolean newpageStatement_2_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_2_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, IDENTIFIER, KW_IF, KW_TOP);
    result_ = result_ && newpageStatement_2_0_1_3(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_PAGE);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_OF?
  private static boolean newpageStatement_2_0_1_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_2_0_1_3")) return false;
    consumeToken(builder_, KW_OF);
    return true;
  }

  // KW_WHEN KW_LESS KW_THAN? expression KW_LINES? KW_LEFT?
  private static boolean newpageStatement_2_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_2_0_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_WHEN, KW_LESS);
    result_ = result_ && newpageStatement_2_0_2_2(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && newpageStatement_2_0_2_4(builder_, level_ + 1);
    result_ = result_ && newpageStatement_2_0_2_5(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_THAN?
  private static boolean newpageStatement_2_0_2_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_2_0_2_2")) return false;
    consumeToken(builder_, KW_THAN);
    return true;
  }

  // KW_LINES?
  private static boolean newpageStatement_2_0_2_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_2_0_2_4")) return false;
    consumeToken(builder_, KW_LINES);
    return true;
  }

  // KW_LEFT?
  private static boolean newpageStatement_2_0_2_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_2_0_2_5")) return false;
    consumeToken(builder_, KW_LEFT);
    return true;
  }

  // writeArg*
  private static boolean newpageStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newpageStatement_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "newpageStatement_3", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // statement | LINE_COMMENT
  static boolean normalCodeLine_(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "normalCodeLine_")) return false;
    boolean result_;
    result_ = statement(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, LINE_COMMENT);
    return result_;
  }

  /* ********************************************************** */
  // KW_NOT breakCondition | KW_FIRST | KW_NOT? comparisonExpr | breakCondition
  public static boolean notCondition(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "notCondition")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, NOT_CONDITION, "<not condition>");
    result_ = notCondition_0(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, KW_FIRST);
    if (!result_) result_ = notCondition_2(builder_, level_ + 1);
    if (!result_) result_ = breakCondition(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_NOT breakCondition
  private static boolean notCondition_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "notCondition_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_NOT);
    result_ = result_ && breakCondition(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_NOT? comparisonExpr
  private static boolean notCondition_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "notCondition_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = notCondition_2_0(builder_, level_ + 1);
    result_ = result_ && comparisonExpr(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_NOT?
  private static boolean notCondition_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "notCondition_2_0")) return false;
    consumeToken(builder_, KW_NOT);
    return true;
  }

  /* ********************************************************** */
  // KW_NOT KW_MODIFIED
  static boolean notModifiedCheck(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "notModifiedCheck")) return false;
    if (!nextTokenIs(builder_, KW_NOT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_NOT, KW_MODIFIED);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_NOT KW_UNIQUE variableRef?
  static boolean notUniqueCheck(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "notUniqueCheck")) return false;
    if (!nextTokenIs(builder_, KW_NOT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_NOT, KW_UNIQUE);
    result_ = result_ && notUniqueCheck_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // variableRef?
  private static boolean notUniqueCheck_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "notUniqueCheck_2")) return false;
    variableRef(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // NUMBER | stringOrIdentifier
  static boolean numberStringOrIdentifier(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "numberStringOrIdentifier")) return false;
    boolean result_;
    result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = stringOrIdentifier(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // KW_ON_ERROR statement* KW_END_ERROR
  public static boolean onErrorBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "onErrorBlock")) return false;
    if (!nextTokenIs(builder_, KW_ON_ERROR)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ON_ERROR_BLOCK, null);
    result_ = consumeToken(builder_, KW_ON_ERROR);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, onErrorBlock_1(builder_, level_ + 1));
    result_ = pinned_ && consumeToken(builder_, KW_END_ERROR) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // statement*
  private static boolean onErrorBlock_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "onErrorBlock_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "onErrorBlock_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !statement (IDENTIFIER EQ_OP (IDENTIFIER | KW_ON | KW_OFF | NUMBER) | identifier)
  static boolean optionsArg(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "optionsArg")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = optionsArg_0(builder_, level_ + 1);
    result_ = result_ && optionsArg_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean optionsArg_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "optionsArg_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // IDENTIFIER EQ_OP (IDENTIFIER | KW_ON | KW_OFF | NUMBER) | identifier
  private static boolean optionsArg_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "optionsArg_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = optionsArg_1_0(builder_, level_ + 1);
    if (!result_) result_ = identifier(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IDENTIFIER EQ_OP (IDENTIFIER | KW_ON | KW_OFF | NUMBER)
  private static boolean optionsArg_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "optionsArg_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, IDENTIFIER, EQ_OP);
    result_ = result_ && optionsArg_1_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IDENTIFIER | KW_ON | KW_OFF | NUMBER
  private static boolean optionsArg_1_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "optionsArg_1_0_2")) return false;
    boolean result_;
    result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, KW_ON);
    if (!result_) result_ = consumeToken(builder_, KW_OFF);
    if (!result_) result_ = consumeToken(builder_, NUMBER);
    return result_;
  }

  /* ********************************************************** */
  // KW_OPTIONS optionsArg+
  public static boolean optionsStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "optionsStatement")) return false;
    if (!nextTokenIs(builder_, KW_OPTIONS)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, OPTIONS_STATEMENT, null);
    result_ = consumeToken(builder_, KW_OPTIONS);
    pinned_ = result_; // pin = 1
    result_ = result_ && optionsStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // optionsArg+
  private static boolean optionsStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "optionsStatement_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = optionsArg(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!optionsArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "optionsStatement_1", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // andCondition (KW_OR (andCondition | compOpRhs))*
  public static boolean orCondition(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "orCondition")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, OR_CONDITION, "<or condition>");
    result_ = andCondition(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && orCondition_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_OR (andCondition | compOpRhs))*
  private static boolean orCondition_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "orCondition_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!orCondition_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "orCondition_1", pos_)) break;
    }
    return true;
  }

  // KW_OR (andCondition | compOpRhs)
  private static boolean orCondition_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "orCondition_1_0")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = consumeToken(builder_, KW_OR);
    pinned_ = result_; // pin = 1
    result_ = result_ && orCondition_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // andCondition | compOpRhs
  private static boolean orCondition_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "orCondition_1_0_1")) return false;
    boolean result_;
    result_ = andCondition(builder_, level_ + 1);
    if (!result_) result_ = compOpRhs(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // KW_PASSWORD EQ_OP expression
  public static boolean passwordClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "passwordClause")) return false;
    if (!nextTokenIs(builder_, KW_PASSWORD)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PASSWORD_CLAUSE, null);
    result_ = consumeTokens(builder_, 1, KW_PASSWORD, EQ_OP);
    pinned_ = result_; // pin = 1
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // !statement expression
  static boolean performParam(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "performParam")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = performParam_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean performParam_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "performParam_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // KW_PERFORM subroutineRef performParam*
  public static boolean performStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "performStatement")) return false;
    if (!nextTokenIs(builder_, KW_PERFORM)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PERFORM_STATEMENT, null);
    result_ = consumeToken(builder_, KW_PERFORM);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, subroutineRef(builder_, level_ + 1));
    result_ = pinned_ && performStatement_2(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // performParam*
  private static boolean performStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "performStatement_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!performParam(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "performStatement_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // literal
  //   | translateSystemFunction
  //   | systemVarRef
  //   | KW_SCAN primary
  //   | variableRef
  //   | maskExpression
  //   | KW_OLD subscript subscript?
  //   | functionCall
  //   | LT_OP expression GT_OP
  //   | LPAREN condition RPAREN
  public static boolean primary(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "primary")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PRIMARY, "<primary>");
    result_ = literal(builder_, level_ + 1);
    if (!result_) result_ = translateSystemFunction(builder_, level_ + 1);
    if (!result_) result_ = systemVarRef(builder_, level_ + 1);
    if (!result_) result_ = primary_3(builder_, level_ + 1);
    if (!result_) result_ = variableRef(builder_, level_ + 1);
    if (!result_) result_ = maskExpression(builder_, level_ + 1);
    if (!result_) result_ = primary_6(builder_, level_ + 1);
    if (!result_) result_ = functionCall(builder_, level_ + 1);
    if (!result_) result_ = primary_8(builder_, level_ + 1);
    if (!result_) result_ = primary_9(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_SCAN primary
  private static boolean primary_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "primary_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_SCAN);
    result_ = result_ && primary(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_OLD subscript subscript?
  private static boolean primary_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "primary_6")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_OLD);
    result_ = result_ && subscript(builder_, level_ + 1);
    result_ = result_ && primary_6_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // subscript?
  private static boolean primary_6_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "primary_6_2")) return false;
    subscript(builder_, level_ + 1);
    return true;
  }

  // LT_OP expression GT_OP
  private static boolean primary_8(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "primary_8")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LT_OP);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, GT_OP);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // LPAREN condition RPAREN
  private static boolean primary_9(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "primary_9")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && condition(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_PRINT writeArg*
  public static boolean printStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "printStatement")) return false;
    if (!nextTokenIs(builder_, KW_PRINT)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PRINT_STATEMENT, null);
    result_ = consumeToken(builder_, KW_PRINT);
    pinned_ = result_; // pin = 1
    result_ = result_ && printStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // writeArg*
  private static boolean printStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "printStatement_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "printStatement_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_PROCESS KW_COMMAND writeArg*
  public static boolean processCommandStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "processCommandStatement")) return false;
    if (!nextTokenIs(builder_, KW_PROCESS)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PROCESS_COMMAND_STATEMENT, null);
    result_ = consumeTokens(builder_, 2, KW_PROCESS, KW_COMMAND);
    pinned_ = result_; // pin = 2
    result_ = result_ && processCommandStatement_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // writeArg*
  private static boolean processCommandStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "processCommandStatement_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "processCommandStatement_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_IN? KW_PHYSICAL? sequenceDirection
  public static boolean rangeSpecOption1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RANGE_SPEC_OPTION_1, "<range spec option 1>");
    result_ = rangeSpecOption1_0(builder_, level_ + 1);
    result_ = result_ && rangeSpecOption1_1(builder_, level_ + 1);
    result_ = result_ && sequenceDirection(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_IN?
  private static boolean rangeSpecOption1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption1_0")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // KW_PHYSICAL?
  private static boolean rangeSpecOption1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption1_1")) return false;
    consumeToken(builder_, KW_PHYSICAL);
    return true;
  }

  /* ********************************************************** */
  // (KW_BY | KW_WITH) KW_ISN (equalOrStartingFromClause expression (thruOrEndingAt expression)?)?
  public static boolean rangeSpecOption2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption2")) return false;
    if (!nextTokenIs(builder_, "<range spec option 2>", KW_BY, KW_WITH)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RANGE_SPEC_OPTION_2, "<range spec option 2>");
    result_ = rangeSpecOption2_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_ISN);
    pinned_ = result_; // pin = 2
    result_ = result_ && rangeSpecOption2_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_BY | KW_WITH
  private static boolean rangeSpecOption2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption2_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_BY);
    if (!result_) result_ = consumeToken(builder_, KW_WITH);
    return result_;
  }

  // (equalOrStartingFromClause expression (thruOrEndingAt expression)?)?
  private static boolean rangeSpecOption2_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption2_2")) return false;
    rangeSpecOption2_2_0(builder_, level_ + 1);
    return true;
  }

  // equalOrStartingFromClause expression (thruOrEndingAt expression)?
  private static boolean rangeSpecOption2_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption2_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = equalOrStartingFromClause(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && rangeSpecOption2_2_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (thruOrEndingAt expression)?
  private static boolean rangeSpecOption2_2_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption2_2_0_2")) return false;
    rangeSpecOption2_2_0_2_0(builder_, level_ + 1);
    return true;
  }

  // thruOrEndingAt expression
  private static boolean rangeSpecOption2_2_0_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption2_2_0_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = thruOrEndingAt(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_IN? KW_LOGICAL? sequenceDirection (KW_BY | KW_WITH) descriptor (rangeSpecOption3c | rangeSpecOption3b | rangeSpecOption3a | rangeSpecOption3d | rangeSpecOption3e)?
  public static boolean rangeSpecOption3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RANGE_SPEC_OPTION_3, "<range spec option 3>");
    result_ = rangeSpecOption3_0(builder_, level_ + 1);
    result_ = result_ && rangeSpecOption3_1(builder_, level_ + 1);
    result_ = result_ && sequenceDirection(builder_, level_ + 1);
    result_ = result_ && rangeSpecOption3_3(builder_, level_ + 1);
    result_ = result_ && descriptor(builder_, level_ + 1);
    result_ = result_ && rangeSpecOption3_5(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_IN?
  private static boolean rangeSpecOption3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption3_0")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // KW_LOGICAL?
  private static boolean rangeSpecOption3_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption3_1")) return false;
    consumeToken(builder_, KW_LOGICAL);
    return true;
  }

  // KW_BY | KW_WITH
  private static boolean rangeSpecOption3_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption3_3")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_BY);
    if (!result_) result_ = consumeToken(builder_, KW_WITH);
    return result_;
  }

  // (rangeSpecOption3c | rangeSpecOption3b | rangeSpecOption3a | rangeSpecOption3d | rangeSpecOption3e)?
  private static boolean rangeSpecOption3_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption3_5")) return false;
    rangeSpecOption3_5_0(builder_, level_ + 1);
    return true;
  }

  // rangeSpecOption3c | rangeSpecOption3b | rangeSpecOption3a | rangeSpecOption3d | rangeSpecOption3e
  private static boolean rangeSpecOption3_5_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption3_5_0")) return false;
    boolean result_;
    result_ = rangeSpecOption3c(builder_, level_ + 1);
    if (!result_) result_ = rangeSpecOption3b(builder_, level_ + 1);
    if (!result_) result_ = rangeSpecOption3a(builder_, level_ + 1);
    if (!result_) result_ = rangeSpecOption3d(builder_, level_ + 1);
    if (!result_) result_ = rangeSpecOption3e(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // equalOrStartingFromClause expression (thruOrEndingAt expression)?
  public static boolean rangeSpecOption3a(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption3a")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RANGE_SPEC_OPTION_3_A, "<range spec option 3 a>");
    result_ = equalOrStartingFromClause(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && rangeSpecOption3a_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (thruOrEndingAt expression)?
  private static boolean rangeSpecOption3a_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption3a_2")) return false;
    rangeSpecOption3a_2_0(builder_, level_ + 1);
    return true;
  }

  // thruOrEndingAt expression
  private static boolean rangeSpecOption3a_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption3a_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = thruOrEndingAt(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // equalOrStartingFromClause expression KW_TO expression
  public static boolean rangeSpecOption3b(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption3b")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RANGE_SPEC_OPTION_3_B, "<range spec option 3 b>");
    result_ = equalOrStartingFromClause(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_TO);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // lessEqualOrGreaterClause expression
  public static boolean rangeSpecOption3c(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption3c")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RANGE_SPEC_OPTION_3_C, "<range spec option 3 c>");
    result_ = lessEqualOrGreaterClause(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // descriptor thruOrEndingAt expression
  static boolean rangeSpecOption3d(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption3d")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = descriptor(builder_, level_ + 1);
    result_ = result_ && thruOrEndingAt(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // thruOrEndingAt expression
  static boolean rangeSpecOption3e(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecOption3e")) return false;
    if (!nextTokenIs(builder_, "", KW_ENDING, KW_THRU)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = thruOrEndingAt(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // rangeSpecOption2 | rangeSpecOption3 | rangeSpecOption1
  public static boolean rangeSpecifications(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeSpecifications")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RANGE_SPECIFICATIONS, "<range specifications>");
    result_ = rangeSpecOption2(builder_, level_ + 1);
    if (!result_) result_ = rangeSpecOption3(builder_, level_ + 1);
    if (!result_) result_ = rangeSpecOption1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // blockLabel? ((KW_READ !KW_WORK) | KW_BROWSE) (KW_ALL | LPAREN expression RPAREN)? multiFetchClause? KW_RECORDS? KW_IN? KW_FILE? identifier
  //               passwordClause? cipherClause? (KW_WITH KW_REPOSITION)? rangeSpecifications?
  //               startingWithIsn? inSharedHold? skipRecordsInHold? whereClause?
  //               statement+ (KW_END_READ | KW_END_ALL)?
  public static boolean readBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, READ_BLOCK, "<read block>");
    result_ = readBlock_0(builder_, level_ + 1);
    result_ = result_ && readBlock_1(builder_, level_ + 1);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, readBlock_2(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, readBlock_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readBlock_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readBlock_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readBlock_6(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, identifier(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readBlock_8(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readBlock_9(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readBlock_10(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readBlock_11(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readBlock_12(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readBlock_13(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readBlock_14(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readBlock_15(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readBlock_16(builder_, level_ + 1)) && result_;
    result_ = pinned_ && readBlock_17(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // blockLabel?
  private static boolean readBlock_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_0")) return false;
    blockLabel(builder_, level_ + 1);
    return true;
  }

  // (KW_READ !KW_WORK) | KW_BROWSE
  private static boolean readBlock_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = readBlock_1_0(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, KW_BROWSE);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_READ !KW_WORK
  private static boolean readBlock_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_READ);
    result_ = result_ && readBlock_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !KW_WORK
  private static boolean readBlock_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_1_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, KW_WORK);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (KW_ALL | LPAREN expression RPAREN)?
  private static boolean readBlock_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_2")) return false;
    readBlock_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_ALL | LPAREN expression RPAREN
  private static boolean readBlock_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_ALL);
    if (!result_) result_ = readBlock_2_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // LPAREN expression RPAREN
  private static boolean readBlock_2_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_2_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // multiFetchClause?
  private static boolean readBlock_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_3")) return false;
    multiFetchClause(builder_, level_ + 1);
    return true;
  }

  // KW_RECORDS?
  private static boolean readBlock_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_4")) return false;
    consumeToken(builder_, KW_RECORDS);
    return true;
  }

  // KW_IN?
  private static boolean readBlock_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_5")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // KW_FILE?
  private static boolean readBlock_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_6")) return false;
    consumeToken(builder_, KW_FILE);
    return true;
  }

  // passwordClause?
  private static boolean readBlock_8(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_8")) return false;
    passwordClause(builder_, level_ + 1);
    return true;
  }

  // cipherClause?
  private static boolean readBlock_9(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_9")) return false;
    cipherClause(builder_, level_ + 1);
    return true;
  }

  // (KW_WITH KW_REPOSITION)?
  private static boolean readBlock_10(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_10")) return false;
    readBlock_10_0(builder_, level_ + 1);
    return true;
  }

  // KW_WITH KW_REPOSITION
  private static boolean readBlock_10_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_10_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_WITH, KW_REPOSITION);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // rangeSpecifications?
  private static boolean readBlock_11(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_11")) return false;
    rangeSpecifications(builder_, level_ + 1);
    return true;
  }

  // startingWithIsn?
  private static boolean readBlock_12(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_12")) return false;
    startingWithIsn(builder_, level_ + 1);
    return true;
  }

  // inSharedHold?
  private static boolean readBlock_13(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_13")) return false;
    inSharedHold(builder_, level_ + 1);
    return true;
  }

  // skipRecordsInHold?
  private static boolean readBlock_14(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_14")) return false;
    skipRecordsInHold(builder_, level_ + 1);
    return true;
  }

  // whereClause?
  private static boolean readBlock_15(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_15")) return false;
    whereClause(builder_, level_ + 1);
    return true;
  }

  // statement+
  private static boolean readBlock_16(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_16")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = statement(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "readBlock_16", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_END_READ | KW_END_ALL)?
  private static boolean readBlock_17(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_17")) return false;
    readBlock_17_0(builder_, level_ + 1);
    return true;
  }

  // KW_END_READ | KW_END_ALL
  private static boolean readBlock_17_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readBlock_17_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_END_READ);
    if (!result_) result_ = consumeToken(builder_, KW_END_ALL);
    return result_;
  }

  /* ********************************************************** */
  // !statement !(IDENTIFIER ASSIGN_OP) !(IDENTIFIER DOT IDENTIFIER ASSIGN_OP) !(USER_VARIABLE ASSIGN_OP) expression
  static boolean readWorkArg(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkArg")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = readWorkArg_0(builder_, level_ + 1);
    result_ = result_ && readWorkArg_1(builder_, level_ + 1);
    result_ = result_ && readWorkArg_2(builder_, level_ + 1);
    result_ = result_ && readWorkArg_3(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean readWorkArg_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkArg_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !(IDENTIFIER ASSIGN_OP)
  private static boolean readWorkArg_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkArg_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !readWorkArg_1_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // IDENTIFIER ASSIGN_OP
  private static boolean readWorkArg_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkArg_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, IDENTIFIER, ASSIGN_OP);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !(IDENTIFIER DOT IDENTIFIER ASSIGN_OP)
  private static boolean readWorkArg_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkArg_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !readWorkArg_2_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // IDENTIFIER DOT IDENTIFIER ASSIGN_OP
  private static boolean readWorkArg_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkArg_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, IDENTIFIER, DOT, IDENTIFIER, ASSIGN_OP);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !(USER_VARIABLE ASSIGN_OP)
  private static boolean readWorkArg_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkArg_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !readWorkArg_3_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // USER_VARIABLE ASSIGN_OP
  private static boolean readWorkArg_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkArg_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, USER_VARIABLE, ASSIGN_OP);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // blockLabel? KW_READ KW_WORK KW_FILE? expression? KW_ONCE? readWorkSelect? statement* KW_END_WORK?
  public static boolean readWorkBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkBlock")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, READ_WORK_BLOCK, "<read work block>");
    result_ = readWorkBlock_0(builder_, level_ + 1);
    result_ = result_ && consumeTokens(builder_, 1, KW_READ, KW_WORK);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, readWorkBlock_3(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, readWorkBlock_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readWorkBlock_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readWorkBlock_6(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, readWorkBlock_7(builder_, level_ + 1)) && result_;
    result_ = pinned_ && readWorkBlock_8(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // blockLabel?
  private static boolean readWorkBlock_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkBlock_0")) return false;
    blockLabel(builder_, level_ + 1);
    return true;
  }

  // KW_FILE?
  private static boolean readWorkBlock_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkBlock_3")) return false;
    consumeToken(builder_, KW_FILE);
    return true;
  }

  // expression?
  private static boolean readWorkBlock_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkBlock_4")) return false;
    expression(builder_, level_ + 1);
    return true;
  }

  // KW_ONCE?
  private static boolean readWorkBlock_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkBlock_5")) return false;
    consumeToken(builder_, KW_ONCE);
    return true;
  }

  // readWorkSelect?
  private static boolean readWorkBlock_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkBlock_6")) return false;
    readWorkSelect(builder_, level_ + 1);
    return true;
  }

  // statement*
  private static boolean readWorkBlock_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkBlock_7")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "readWorkBlock_7", pos_)) break;
    }
    return true;
  }

  // KW_END_WORK?
  private static boolean readWorkBlock_8(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkBlock_8")) return false;
    consumeToken(builder_, KW_END_WORK);
    return true;
  }

  /* ********************************************************** */
  // (KW_RECORD? KW_VARIABLE? | KW_SELECT) readWorkArg+
  static boolean readWorkSelect(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkSelect")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = readWorkSelect_0(builder_, level_ + 1);
    result_ = result_ && readWorkSelect_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_RECORD? KW_VARIABLE? | KW_SELECT
  private static boolean readWorkSelect_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkSelect_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = readWorkSelect_0_0(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, KW_SELECT);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_RECORD? KW_VARIABLE?
  private static boolean readWorkSelect_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkSelect_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = readWorkSelect_0_0_0(builder_, level_ + 1);
    result_ = result_ && readWorkSelect_0_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_RECORD?
  private static boolean readWorkSelect_0_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkSelect_0_0_0")) return false;
    consumeToken(builder_, KW_RECORD);
    return true;
  }

  // KW_VARIABLE?
  private static boolean readWorkSelect_0_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkSelect_0_0_1")) return false;
    consumeToken(builder_, KW_VARIABLE);
    return true;
  }

  // readWorkArg+
  private static boolean readWorkSelect_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "readWorkSelect_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = readWorkArg(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!readWorkArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "readWorkSelect_1", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_REDUCE KW_ARRAY? expandBody?
  public static boolean reduceStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reduceStatement")) return false;
    if (!nextTokenIs(builder_, KW_REDUCE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, REDUCE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_REDUCE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, reduceStatement_1(builder_, level_ + 1));
    result_ = pinned_ && reduceStatement_2(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_ARRAY?
  private static boolean reduceStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reduceStatement_1")) return false;
    consumeToken(builder_, KW_ARRAY);
    return true;
  }

  // expandBody?
  private static boolean reduceStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reduceStatement_2")) return false;
    expandBody(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // !statement (COMMA | writeParenBlock | expression)
  static boolean reinputArg(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputArg")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = reinputArg_0(builder_, level_ + 1);
    result_ = result_ && reinputArg_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean reinputArg_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputArg_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // COMMA | writeParenBlock | expression
  private static boolean reinputArg_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputArg_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, COMMA);
    if (!result_) result_ = writeParenBlock(builder_, level_ + 1);
    if (!result_) result_ = expression(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // KW_MARK !statement (writeParenBlock | expression)+
  static boolean reinputMarkClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputMarkClause")) return false;
    if (!nextTokenIs(builder_, KW_MARK)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_MARK);
    result_ = result_ && reinputMarkClause_1(builder_, level_ + 1);
    result_ = result_ && reinputMarkClause_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean reinputMarkClause_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputMarkClause_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (writeParenBlock | expression)+
  private static boolean reinputMarkClause_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputMarkClause_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = reinputMarkClause_2_0(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!reinputMarkClause_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "reinputMarkClause_2", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // writeParenBlock | expression
  private static boolean reinputMarkClause_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputMarkClause_2_0")) return false;
    boolean result_;
    result_ = writeParenBlock(builder_, level_ + 1);
    if (!result_) result_ = expression(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // KW_REINPUT KW_FULL? expression? writeParenBlock? reinputArg* (KW_WITH? KW_TEXT reinputArg+)? (KW_USING KW_HELP)? reinputMarkClause? KW_ALARM?
  public static boolean reinputStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputStatement")) return false;
    if (!nextTokenIs(builder_, KW_REINPUT)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, REINPUT_STATEMENT, null);
    result_ = consumeToken(builder_, KW_REINPUT);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, reinputStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, reinputStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, reinputStatement_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, reinputStatement_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, reinputStatement_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, reinputStatement_6(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, reinputStatement_7(builder_, level_ + 1)) && result_;
    result_ = pinned_ && reinputStatement_8(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_FULL?
  private static boolean reinputStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputStatement_1")) return false;
    consumeToken(builder_, KW_FULL);
    return true;
  }

  // expression?
  private static boolean reinputStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputStatement_2")) return false;
    expression(builder_, level_ + 1);
    return true;
  }

  // writeParenBlock?
  private static boolean reinputStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputStatement_3")) return false;
    writeParenBlock(builder_, level_ + 1);
    return true;
  }

  // reinputArg*
  private static boolean reinputStatement_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputStatement_4")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!reinputArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "reinputStatement_4", pos_)) break;
    }
    return true;
  }

  // (KW_WITH? KW_TEXT reinputArg+)?
  private static boolean reinputStatement_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputStatement_5")) return false;
    reinputStatement_5_0(builder_, level_ + 1);
    return true;
  }

  // KW_WITH? KW_TEXT reinputArg+
  private static boolean reinputStatement_5_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputStatement_5_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = reinputStatement_5_0_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_TEXT);
    result_ = result_ && reinputStatement_5_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_WITH?
  private static boolean reinputStatement_5_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputStatement_5_0_0")) return false;
    consumeToken(builder_, KW_WITH);
    return true;
  }

  // reinputArg+
  private static boolean reinputStatement_5_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputStatement_5_0_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = reinputArg(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!reinputArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "reinputStatement_5_0_2", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_USING KW_HELP)?
  private static boolean reinputStatement_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputStatement_6")) return false;
    reinputStatement_6_0(builder_, level_ + 1);
    return true;
  }

  // KW_USING KW_HELP
  private static boolean reinputStatement_6_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputStatement_6_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_USING, KW_HELP);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // reinputMarkClause?
  private static boolean reinputStatement_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputStatement_7")) return false;
    reinputMarkClause(builder_, level_ + 1);
    return true;
  }

  // KW_ALARM?
  private static boolean reinputStatement_8(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "reinputStatement_8")) return false;
    consumeToken(builder_, KW_ALARM);
    return true;
  }

  /* ********************************************************** */
  // KW_REJECT (KW_IF condition)?
  public static boolean rejectStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rejectStatement")) return false;
    if (!nextTokenIs(builder_, KW_REJECT)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, REJECT_STATEMENT, null);
    result_ = consumeToken(builder_, KW_REJECT);
    pinned_ = result_; // pin = 1
    result_ = result_ && rejectStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_IF condition)?
  private static boolean rejectStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rejectStatement_1")) return false;
    rejectStatement_1_0(builder_, level_ + 1);
    return true;
  }

  // KW_IF condition
  private static boolean rejectStatement_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rejectStatement_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_IF);
    result_ = result_ && condition(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_RELEASE (KW_SET expression? | KW_STACK | KW_TRANSACTION | IDENTIFIER | STRING_LITERAL)?
  public static boolean releaseStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "releaseStatement")) return false;
    if (!nextTokenIs(builder_, KW_RELEASE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RELEASE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_RELEASE);
    pinned_ = result_; // pin = 1
    result_ = result_ && releaseStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_SET expression? | KW_STACK | KW_TRANSACTION | IDENTIFIER | STRING_LITERAL)?
  private static boolean releaseStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "releaseStatement_1")) return false;
    releaseStatement_1_0(builder_, level_ + 1);
    return true;
  }

  // KW_SET expression? | KW_STACK | KW_TRANSACTION | IDENTIFIER | STRING_LITERAL
  private static boolean releaseStatement_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "releaseStatement_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = releaseStatement_1_0_0(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, KW_STACK);
    if (!result_) result_ = consumeToken(builder_, KW_TRANSACTION);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, STRING_LITERAL);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_SET expression?
  private static boolean releaseStatement_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "releaseStatement_1_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_SET);
    result_ = result_ && releaseStatement_1_0_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // expression?
  private static boolean releaseStatement_1_0_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "releaseStatement_1_0_0_1")) return false;
    expression(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // blockLabel? KW_REPEAT repeatCondition? statement* repeatCondition? (KW_END_REPEAT | KW_END_ALL)?
  public static boolean repeatBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "repeatBlock")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, REPEAT_BLOCK, "<repeat block>");
    result_ = repeatBlock_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_REPEAT);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, repeatBlock_2(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, repeatBlock_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, repeatBlock_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && repeatBlock_5(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // blockLabel?
  private static boolean repeatBlock_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "repeatBlock_0")) return false;
    blockLabel(builder_, level_ + 1);
    return true;
  }

  // repeatCondition?
  private static boolean repeatBlock_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "repeatBlock_2")) return false;
    repeatCondition(builder_, level_ + 1);
    return true;
  }

  // statement*
  private static boolean repeatBlock_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "repeatBlock_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "repeatBlock_3", pos_)) break;
    }
    return true;
  }

  // repeatCondition?
  private static boolean repeatBlock_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "repeatBlock_4")) return false;
    repeatCondition(builder_, level_ + 1);
    return true;
  }

  // (KW_END_REPEAT | KW_END_ALL)?
  private static boolean repeatBlock_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "repeatBlock_5")) return false;
    repeatBlock_5_0(builder_, level_ + 1);
    return true;
  }

  // KW_END_REPEAT | KW_END_ALL
  private static boolean repeatBlock_5_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "repeatBlock_5_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_END_REPEAT);
    if (!result_) result_ = consumeToken(builder_, KW_END_ALL);
    return result_;
  }

  /* ********************************************************** */
  // (KW_UNTIL | KW_WHILE) condition
  public static boolean repeatCondition(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "repeatCondition")) return false;
    if (!nextTokenIs(builder_, "<repeat condition>", KW_UNTIL, KW_WHILE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, REPEAT_CONDITION, "<repeat condition>");
    result_ = repeatCondition_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && condition(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_UNTIL | KW_WHILE
  private static boolean repeatCondition_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "repeatCondition_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_UNTIL);
    if (!result_) result_ = consumeToken(builder_, KW_WHILE);
    return result_;
  }

  /* ********************************************************** */
  // KW_REQUEST KW_DOCUMENT writeArg*
  public static boolean requestDocumentStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "requestDocumentStatement")) return false;
    if (!nextTokenIs(builder_, KW_REQUEST)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, REQUEST_DOCUMENT_STATEMENT, null);
    result_ = consumeTokens(builder_, 2, KW_REQUEST, KW_DOCUMENT);
    pinned_ = result_; // pin = 2
    result_ = result_ && requestDocumentStatement_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // writeArg*
  private static boolean requestDocumentStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "requestDocumentStatement_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "requestDocumentStatement_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !statement (sessionParameter | expression)
  static boolean resetArg(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "resetArg")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = resetArg_0(builder_, level_ + 1);
    result_ = result_ && resetArg_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean resetArg_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "resetArg_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // sessionParameter | expression
  private static boolean resetArg_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "resetArg_1")) return false;
    boolean result_;
    result_ = sessionParameter(builder_, level_ + 1);
    if (!result_) result_ = expression(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // KW_RESET (KW_INITIAL)? resetArg+
  public static boolean resetStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "resetStatement")) return false;
    if (!nextTokenIs(builder_, KW_RESET)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RESET_STATEMENT, null);
    result_ = consumeToken(builder_, KW_RESET);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, resetStatement_1(builder_, level_ + 1));
    result_ = pinned_ && resetStatement_2(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_INITIAL)?
  private static boolean resetStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "resetStatement_1")) return false;
    consumeToken(builder_, KW_INITIAL);
    return true;
  }

  // resetArg+
  private static boolean resetStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "resetStatement_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = resetArg(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!resetArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "resetStatement_2", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_RESIZE KW_ARRAY? expandBody?
  public static boolean resizeStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "resizeStatement")) return false;
    if (!nextTokenIs(builder_, KW_RESIZE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RESIZE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_RESIZE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, resizeStatement_1(builder_, level_ + 1));
    result_ = pinned_ && resizeStatement_2(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_ARRAY?
  private static boolean resizeStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "resizeStatement_1")) return false;
    consumeToken(builder_, KW_ARRAY);
    return true;
  }

  // expandBody?
  private static boolean resizeStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "resizeStatement_2")) return false;
    expandBody(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // KW_RETAIN KW_AS expression
  public static boolean retainClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "retainClause")) return false;
    if (!nextTokenIs(builder_, KW_RETAIN)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RETAIN_CLAUSE, null);
    result_ = consumeTokens(builder_, 1, KW_RETAIN, KW_AS);
    pinned_ = result_; // pin = 1
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // KW_RETRY
  public static boolean retryStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "retryStatement")) return false;
    if (!nextTokenIs(builder_, KW_RETRY)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_RETRY);
    exit_section_(builder_, marker_, RETRY_STATEMENT, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_RUN expression
  public static boolean runStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "runStatement")) return false;
    if (!nextTokenIs(builder_, KW_RUN)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RUN_STATEMENT, null);
    result_ = consumeToken(builder_, KW_RUN);
    pinned_ = result_; // pin = 1
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // KW_LOCAL | KW_GLOBAL | KW_PARAMETER | KW_INDEPENDENT | KW_CONTEXT
  static boolean scopeKeyword(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "scopeKeyword")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_LOCAL);
    if (!result_) result_ = consumeToken(builder_, KW_GLOBAL);
    if (!result_) result_ = consumeToken(builder_, KW_PARAMETER);
    if (!result_) result_ = consumeToken(builder_, KW_INDEPENDENT);
    if (!result_) result_ = consumeToken(builder_, KW_CONTEXT);
    return result_;
  }

  /* ********************************************************** */
  // KW_SEPARATE expression (KW_WITH? (KW_DELIMITER | KW_DELIMITERS) expression)? ((KW_LEFT | KW_RIGHT) KW_JUSTIFIED?)? KW_INTO expression+ (KW_WITH? (KW_DELIMITER | KW_DELIMITERS) expression)? (KW_REMAINDER expression)? (KW_IGNORE)? (KW_WITH? (KW_DELIMITER | KW_DELIMITERS) expression)?
  public static boolean separateStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement")) return false;
    if (!nextTokenIs(builder_, KW_SEPARATE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SEPARATE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_SEPARATE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, expression(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, separateStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, separateStatement_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, consumeToken(builder_, KW_INTO)) && result_;
    result_ = pinned_ && report_error_(builder_, separateStatement_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, separateStatement_6(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, separateStatement_7(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, separateStatement_8(builder_, level_ + 1)) && result_;
    result_ = pinned_ && separateStatement_9(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_WITH? (KW_DELIMITER | KW_DELIMITERS) expression)?
  private static boolean separateStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_2")) return false;
    separateStatement_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_WITH? (KW_DELIMITER | KW_DELIMITERS) expression
  private static boolean separateStatement_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = separateStatement_2_0_0(builder_, level_ + 1);
    result_ = result_ && separateStatement_2_0_1(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_WITH?
  private static boolean separateStatement_2_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_2_0_0")) return false;
    consumeToken(builder_, KW_WITH);
    return true;
  }

  // KW_DELIMITER | KW_DELIMITERS
  private static boolean separateStatement_2_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_2_0_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_DELIMITER);
    if (!result_) result_ = consumeToken(builder_, KW_DELIMITERS);
    return result_;
  }

  // ((KW_LEFT | KW_RIGHT) KW_JUSTIFIED?)?
  private static boolean separateStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_3")) return false;
    separateStatement_3_0(builder_, level_ + 1);
    return true;
  }

  // (KW_LEFT | KW_RIGHT) KW_JUSTIFIED?
  private static boolean separateStatement_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = separateStatement_3_0_0(builder_, level_ + 1);
    result_ = result_ && separateStatement_3_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_LEFT | KW_RIGHT
  private static boolean separateStatement_3_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_3_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_LEFT);
    if (!result_) result_ = consumeToken(builder_, KW_RIGHT);
    return result_;
  }

  // KW_JUSTIFIED?
  private static boolean separateStatement_3_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_3_0_1")) return false;
    consumeToken(builder_, KW_JUSTIFIED);
    return true;
  }

  // expression+
  private static boolean separateStatement_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_5")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!expression(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "separateStatement_5", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_WITH? (KW_DELIMITER | KW_DELIMITERS) expression)?
  private static boolean separateStatement_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_6")) return false;
    separateStatement_6_0(builder_, level_ + 1);
    return true;
  }

  // KW_WITH? (KW_DELIMITER | KW_DELIMITERS) expression
  private static boolean separateStatement_6_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_6_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = separateStatement_6_0_0(builder_, level_ + 1);
    result_ = result_ && separateStatement_6_0_1(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_WITH?
  private static boolean separateStatement_6_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_6_0_0")) return false;
    consumeToken(builder_, KW_WITH);
    return true;
  }

  // KW_DELIMITER | KW_DELIMITERS
  private static boolean separateStatement_6_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_6_0_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_DELIMITER);
    if (!result_) result_ = consumeToken(builder_, KW_DELIMITERS);
    return result_;
  }

  // (KW_REMAINDER expression)?
  private static boolean separateStatement_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_7")) return false;
    separateStatement_7_0(builder_, level_ + 1);
    return true;
  }

  // KW_REMAINDER expression
  private static boolean separateStatement_7_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_7_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_REMAINDER);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_IGNORE)?
  private static boolean separateStatement_8(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_8")) return false;
    consumeToken(builder_, KW_IGNORE);
    return true;
  }

  // (KW_WITH? (KW_DELIMITER | KW_DELIMITERS) expression)?
  private static boolean separateStatement_9(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_9")) return false;
    separateStatement_9_0(builder_, level_ + 1);
    return true;
  }

  // KW_WITH? (KW_DELIMITER | KW_DELIMITERS) expression
  private static boolean separateStatement_9_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_9_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = separateStatement_9_0_0(builder_, level_ + 1);
    result_ = result_ && separateStatement_9_0_1(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_WITH?
  private static boolean separateStatement_9_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_9_0_0")) return false;
    consumeToken(builder_, KW_WITH);
    return true;
  }

  // KW_DELIMITER | KW_DELIMITERS
  private static boolean separateStatement_9_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "separateStatement_9_0_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_DELIMITER);
    if (!result_) result_ = consumeToken(builder_, KW_DELIMITERS);
    return result_;
  }

  /* ********************************************************** */
  // (KW_ASCENDING | KW_DESCENDING | KW_VARIABLE stringOrIdentifier | KW_DYNAMIC stringOrIdentifier)? KW_SEQUENCE?
  public static boolean sequenceDirection(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sequenceDirection")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SEQUENCE_DIRECTION, "<sequence direction>");
    result_ = sequenceDirection_0(builder_, level_ + 1);
    result_ = result_ && sequenceDirection_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (KW_ASCENDING | KW_DESCENDING | KW_VARIABLE stringOrIdentifier | KW_DYNAMIC stringOrIdentifier)?
  private static boolean sequenceDirection_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sequenceDirection_0")) return false;
    sequenceDirection_0_0(builder_, level_ + 1);
    return true;
  }

  // KW_ASCENDING | KW_DESCENDING | KW_VARIABLE stringOrIdentifier | KW_DYNAMIC stringOrIdentifier
  private static boolean sequenceDirection_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sequenceDirection_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_ASCENDING);
    if (!result_) result_ = consumeToken(builder_, KW_DESCENDING);
    if (!result_) result_ = sequenceDirection_0_0_2(builder_, level_ + 1);
    if (!result_) result_ = sequenceDirection_0_0_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_VARIABLE stringOrIdentifier
  private static boolean sequenceDirection_0_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sequenceDirection_0_0_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_VARIABLE);
    result_ = result_ && stringOrIdentifier(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_DYNAMIC stringOrIdentifier
  private static boolean sequenceDirection_0_0_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sequenceDirection_0_0_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_DYNAMIC);
    result_ = result_ && stringOrIdentifier(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_SEQUENCE?
  private static boolean sequenceDirection_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sequenceDirection_1")) return false;
    consumeToken(builder_, KW_SEQUENCE);
    return true;
  }

  /* ********************************************************** */
  // KW_ON | KW_OFF | NEQ_OP | expression
  static boolean sessionParamValue(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sessionParamValue")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_ON);
    if (!result_) result_ = consumeToken(builder_, KW_OFF);
    if (!result_) result_ = consumeToken(builder_, NEQ_OP);
    if (!result_) result_ = expression(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // SP_AD | SP_AL | SP_CD | SP_DF | SP_DL | SP_DY | SP_EM | SP_ES | SP_FC | SP_FL | SP_GC | SP_HC | SP_HW
  //   | SP_IC | SP_ICU | SP_IP | SP_IS | SP_KD | SP_LC | SP_LCU | SP_LS | SP_MC | SP_MP | SP_MS
  //   | SP_NL | SP_PC | SP_PM | SP_PS | SP_SF | SP_SG | SP_TC | SP_TCU | SP_UC | SP_ZP
  static boolean sessionParameter(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sessionParameter")) return false;
    boolean result_;
    result_ = consumeToken(builder_, SP_AD);
    if (!result_) result_ = consumeToken(builder_, SP_AL);
    if (!result_) result_ = consumeToken(builder_, SP_CD);
    if (!result_) result_ = consumeToken(builder_, SP_DF);
    if (!result_) result_ = consumeToken(builder_, SP_DL);
    if (!result_) result_ = consumeToken(builder_, SP_DY);
    if (!result_) result_ = consumeToken(builder_, SP_EM);
    if (!result_) result_ = consumeToken(builder_, SP_ES);
    if (!result_) result_ = consumeToken(builder_, SP_FC);
    if (!result_) result_ = consumeToken(builder_, SP_FL);
    if (!result_) result_ = consumeToken(builder_, SP_GC);
    if (!result_) result_ = consumeToken(builder_, SP_HC);
    if (!result_) result_ = consumeToken(builder_, SP_HW);
    if (!result_) result_ = consumeToken(builder_, SP_IC);
    if (!result_) result_ = consumeToken(builder_, SP_ICU);
    if (!result_) result_ = consumeToken(builder_, SP_IP);
    if (!result_) result_ = consumeToken(builder_, SP_IS);
    if (!result_) result_ = consumeToken(builder_, SP_KD);
    if (!result_) result_ = consumeToken(builder_, SP_LC);
    if (!result_) result_ = consumeToken(builder_, SP_LCU);
    if (!result_) result_ = consumeToken(builder_, SP_LS);
    if (!result_) result_ = consumeToken(builder_, SP_MC);
    if (!result_) result_ = consumeToken(builder_, SP_MP);
    if (!result_) result_ = consumeToken(builder_, SP_MS);
    if (!result_) result_ = consumeToken(builder_, SP_NL);
    if (!result_) result_ = consumeToken(builder_, SP_PC);
    if (!result_) result_ = consumeToken(builder_, SP_PM);
    if (!result_) result_ = consumeToken(builder_, SP_PS);
    if (!result_) result_ = consumeToken(builder_, SP_SF);
    if (!result_) result_ = consumeToken(builder_, SP_SG);
    if (!result_) result_ = consumeToken(builder_, SP_TC);
    if (!result_) result_ = consumeToken(builder_, SP_TCU);
    if (!result_) result_ = consumeToken(builder_, SP_UC);
    if (!result_) result_ = consumeToken(builder_, SP_ZP);
    return result_;
  }

  /* ********************************************************** */
  // LPAREN (sessionParameter EQ_OP editMaskValue)+ RPAREN
  static boolean sessionParameterSpec(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sessionParameterSpec")) return false;
    if (!nextTokenIs(builder_, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && sessionParameterSpec_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (sessionParameter EQ_OP editMaskValue)+
  private static boolean sessionParameterSpec_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sessionParameterSpec_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = sessionParameterSpec_1_0(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!sessionParameterSpec_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "sessionParameterSpec_1", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // sessionParameter EQ_OP editMaskValue
  private static boolean sessionParameterSpec_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sessionParameterSpec_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = sessionParameter(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, EQ_OP);
    result_ = result_ && editMaskValue(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_SET KW_CONTROL writeArg*
  public static boolean setControlStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "setControlStatement")) return false;
    if (!nextTokenIs(builder_, KW_SET)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SET_CONTROL_STATEMENT, null);
    result_ = consumeTokens(builder_, 2, KW_SET, KW_CONTROL);
    pinned_ = result_; // pin = 2
    result_ = result_ && setControlStatement_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // writeArg*
  private static boolean setControlStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "setControlStatement_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "setControlStatement_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !statement (writeParenBlock | SLASH | KW_USING | KW_MAP | KW_NO | KW_WINDOW | KW_AS | IDENTIFIER EQ_OP expression | expression)
  static boolean setKeyArg(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "setKeyArg")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = setKeyArg_0(builder_, level_ + 1);
    result_ = result_ && setKeyArg_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean setKeyArg_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "setKeyArg_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // writeParenBlock | SLASH | KW_USING | KW_MAP | KW_NO | KW_WINDOW | KW_AS | IDENTIFIER EQ_OP expression | expression
  private static boolean setKeyArg_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "setKeyArg_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = writeParenBlock(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, SLASH);
    if (!result_) result_ = consumeToken(builder_, KW_USING);
    if (!result_) result_ = consumeToken(builder_, KW_MAP);
    if (!result_) result_ = consumeToken(builder_, KW_NO);
    if (!result_) result_ = consumeToken(builder_, KW_WINDOW);
    if (!result_) result_ = consumeToken(builder_, KW_AS);
    if (!result_) result_ = setKeyArg_1_7(builder_, level_ + 1);
    if (!result_) result_ = expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IDENTIFIER EQ_OP expression
  private static boolean setKeyArg_1_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "setKeyArg_1_7")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, IDENTIFIER, EQ_OP);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_SET KW_KEY? (KW_ALL | setKeyArg+)
  public static boolean setKeyStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "setKeyStatement")) return false;
    if (!nextTokenIs(builder_, KW_SET)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SET_KEY_STATEMENT, null);
    result_ = consumeToken(builder_, KW_SET);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, setKeyStatement_1(builder_, level_ + 1));
    result_ = pinned_ && setKeyStatement_2(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_KEY?
  private static boolean setKeyStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "setKeyStatement_1")) return false;
    consumeToken(builder_, KW_KEY);
    return true;
  }

  // KW_ALL | setKeyArg+
  private static boolean setKeyStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "setKeyStatement_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_ALL);
    if (!result_) result_ = setKeyStatement_2_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // setKeyArg+
  private static boolean setKeyStatement_2_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "setKeyStatement_2_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = setKeyArg(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!setKeyArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "setKeyStatement_2_1", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // blockLabel? KW_SETTIME
  public static boolean setTimeStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "setTimeStatement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SET_TIME_STATEMENT, "<set time statement>");
    result_ = setTimeStatement_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_SETTIME);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // blockLabel?
  private static boolean setTimeStatement_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "setTimeStatement_0")) return false;
    blockLabel(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // NUMBER IDENTIFIER
  static boolean shorthandType(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "shorthandType")) return false;
    if (!nextTokenIs(builder_, NUMBER)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, NUMBER, IDENTIFIER);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // moveStatement
  //   | performStatement
  //   | callnatStatement
  //   | includeStatement
  //   | assignStatement
  //   | computeStatement
  //   | addStatement
  //   | subtractStatement
  //   | multiplyStatement
  //   | divideStatement
  //   | compressStatement
  //   | examineStatement
  //   | separateStatement
  //   | translateStatement
  //   | resetStatement
  //   | writeWorkFileStatement
  //   | downloadStatement
  //   | closeWorkStatement
  //   | writeStatement
  //   | displayStatement
  //   | printStatement
  //   | inputStatement
  //   | reinputStatement
  //   | stopStatement
  //   | retryStatement
  //   | terminateStatement
  //   | escapeStatement
  //   | fetchStatement
  //   | callStatement
  //   | runStatement
  //   | endTransactionStatement
  //   | backoutStatement
  //   | storeStatement
  //   | updateStatement
  //   | deleteStatement
  //   | rejectStatement
  //   | acceptStatement
  //   | skipStatement
  //   | newpageStatement
  //   | optionsStatement
  //   | formatStatement
  //   | ignoreStatement
  //   | ejectStatement
  //   | endAllStatement
  //   | defineWindowStatement
  //   | definePrinterStatement
  //   | defineWorkFileStatement
  //   | markStatement
  //   | stackStatement
  //   | releaseStatement
  //   | setControlStatement
  //   | setKeyStatement
  //   | controlStatement
  //   | getStatement
  //   | expandStatement
  //   | reduceStatement
  //   | resizeStatement
  //   | histogramStatement
  //   | incdicStatement
  //   | processCommandStatement
  //   | requestDocumentStatement
  public static boolean simpleStatements(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simpleStatements")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SIMPLE_STATEMENTS, "<simple statements>");
    result_ = moveStatement(builder_, level_ + 1);
    if (!result_) result_ = performStatement(builder_, level_ + 1);
    if (!result_) result_ = callnatStatement(builder_, level_ + 1);
    if (!result_) result_ = includeStatement(builder_, level_ + 1);
    if (!result_) result_ = assignStatement(builder_, level_ + 1);
    if (!result_) result_ = computeStatement(builder_, level_ + 1);
    if (!result_) result_ = addStatement(builder_, level_ + 1);
    if (!result_) result_ = subtractStatement(builder_, level_ + 1);
    if (!result_) result_ = multiplyStatement(builder_, level_ + 1);
    if (!result_) result_ = divideStatement(builder_, level_ + 1);
    if (!result_) result_ = compressStatement(builder_, level_ + 1);
    if (!result_) result_ = examineStatement(builder_, level_ + 1);
    if (!result_) result_ = separateStatement(builder_, level_ + 1);
    if (!result_) result_ = translateStatement(builder_, level_ + 1);
    if (!result_) result_ = resetStatement(builder_, level_ + 1);
    if (!result_) result_ = writeWorkFileStatement(builder_, level_ + 1);
    if (!result_) result_ = downloadStatement(builder_, level_ + 1);
    if (!result_) result_ = closeWorkStatement(builder_, level_ + 1);
    if (!result_) result_ = writeStatement(builder_, level_ + 1);
    if (!result_) result_ = displayStatement(builder_, level_ + 1);
    if (!result_) result_ = printStatement(builder_, level_ + 1);
    if (!result_) result_ = inputStatement(builder_, level_ + 1);
    if (!result_) result_ = reinputStatement(builder_, level_ + 1);
    if (!result_) result_ = stopStatement(builder_, level_ + 1);
    if (!result_) result_ = retryStatement(builder_, level_ + 1);
    if (!result_) result_ = terminateStatement(builder_, level_ + 1);
    if (!result_) result_ = escapeStatement(builder_, level_ + 1);
    if (!result_) result_ = fetchStatement(builder_, level_ + 1);
    if (!result_) result_ = callStatement(builder_, level_ + 1);
    if (!result_) result_ = runStatement(builder_, level_ + 1);
    if (!result_) result_ = endTransactionStatement(builder_, level_ + 1);
    if (!result_) result_ = backoutStatement(builder_, level_ + 1);
    if (!result_) result_ = storeStatement(builder_, level_ + 1);
    if (!result_) result_ = updateStatement(builder_, level_ + 1);
    if (!result_) result_ = deleteStatement(builder_, level_ + 1);
    if (!result_) result_ = rejectStatement(builder_, level_ + 1);
    if (!result_) result_ = acceptStatement(builder_, level_ + 1);
    if (!result_) result_ = skipStatement(builder_, level_ + 1);
    if (!result_) result_ = newpageStatement(builder_, level_ + 1);
    if (!result_) result_ = optionsStatement(builder_, level_ + 1);
    if (!result_) result_ = formatStatement(builder_, level_ + 1);
    if (!result_) result_ = ignoreStatement(builder_, level_ + 1);
    if (!result_) result_ = ejectStatement(builder_, level_ + 1);
    if (!result_) result_ = endAllStatement(builder_, level_ + 1);
    if (!result_) result_ = defineWindowStatement(builder_, level_ + 1);
    if (!result_) result_ = definePrinterStatement(builder_, level_ + 1);
    if (!result_) result_ = defineWorkFileStatement(builder_, level_ + 1);
    if (!result_) result_ = markStatement(builder_, level_ + 1);
    if (!result_) result_ = stackStatement(builder_, level_ + 1);
    if (!result_) result_ = releaseStatement(builder_, level_ + 1);
    if (!result_) result_ = setControlStatement(builder_, level_ + 1);
    if (!result_) result_ = setKeyStatement(builder_, level_ + 1);
    if (!result_) result_ = controlStatement(builder_, level_ + 1);
    if (!result_) result_ = getStatement(builder_, level_ + 1);
    if (!result_) result_ = expandStatement(builder_, level_ + 1);
    if (!result_) result_ = reduceStatement(builder_, level_ + 1);
    if (!result_) result_ = resizeStatement(builder_, level_ + 1);
    if (!result_) result_ = histogramStatement(builder_, level_ + 1);
    if (!result_) result_ = incdicStatement(builder_, level_ + 1);
    if (!result_) result_ = processCommandStatement(builder_, level_ + 1);
    if (!result_) result_ = requestDocumentStatement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // KW_SKIP KW_RECORDS? KW_IN KW_HOLD
  public static boolean skipRecordsInHold(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "skipRecordsInHold")) return false;
    if (!nextTokenIs(builder_, KW_SKIP)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_SKIP);
    result_ = result_ && skipRecordsInHold_1(builder_, level_ + 1);
    result_ = result_ && consumeTokens(builder_, 0, KW_IN, KW_HOLD);
    exit_section_(builder_, marker_, SKIP_RECORDS_IN_HOLD, result_);
    return result_;
  }

  // KW_RECORDS?
  private static boolean skipRecordsInHold_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "skipRecordsInHold_1")) return false;
    consumeToken(builder_, KW_RECORDS);
    return true;
  }

  /* ********************************************************** */
  // KW_SKIP (LPAREN (NUMBER | IDENTIFIER | SUBST_PARAM) RPAREN)? writeArg* KW_LINES?
  public static boolean skipStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "skipStatement")) return false;
    if (!nextTokenIs(builder_, KW_SKIP)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SKIP_STATEMENT, null);
    result_ = consumeToken(builder_, KW_SKIP);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, skipStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, skipStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && skipStatement_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (LPAREN (NUMBER | IDENTIFIER | SUBST_PARAM) RPAREN)?
  private static boolean skipStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "skipStatement_1")) return false;
    skipStatement_1_0(builder_, level_ + 1);
    return true;
  }

  // LPAREN (NUMBER | IDENTIFIER | SUBST_PARAM) RPAREN
  private static boolean skipStatement_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "skipStatement_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && skipStatement_1_0_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // NUMBER | IDENTIFIER | SUBST_PARAM
  private static boolean skipStatement_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "skipStatement_1_0_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, SUBST_PARAM);
    return result_;
  }

  // writeArg*
  private static boolean skipStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "skipStatement_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "skipStatement_2", pos_)) break;
    }
    return true;
  }

  // KW_LINES?
  private static boolean skipStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "skipStatement_3")) return false;
    consumeToken(builder_, KW_LINES);
    return true;
  }

  /* ********************************************************** */
  // KW_MAX | KW_MIN | KW_NMIN | KW_COUNT | KW_NCOUNT
  //                                  | KW_OLD | KW_AVER | KW_NAVER | KW_SUM | KW_TOTAL
  static boolean sortAggregateFunction(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortAggregateFunction")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_MAX);
    if (!result_) result_ = consumeToken(builder_, KW_MIN);
    if (!result_) result_ = consumeToken(builder_, KW_NMIN);
    if (!result_) result_ = consumeToken(builder_, KW_COUNT);
    if (!result_) result_ = consumeToken(builder_, KW_NCOUNT);
    if (!result_) result_ = consumeToken(builder_, KW_OLD);
    if (!result_) result_ = consumeToken(builder_, KW_AVER);
    if (!result_) result_ = consumeToken(builder_, KW_NAVER);
    if (!result_) result_ = consumeToken(builder_, KW_SUM);
    if (!result_) result_ = consumeToken(builder_, KW_TOTAL);
    return result_;
  }

  /* ********************************************************** */
  // blockLabel? KW_AND? KW_SORT (KW_THEM | KW_RECORDS)? KW_BY?
  //               sortKey+
  //               KW_RECORDS?
  //               sortUsingClause?
  //               sortGiveClause?
  //               statement*
  //               (KW_END_SORT | KW_END_ALL)?
  public static boolean sortBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortBlock")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SORT_BLOCK, "<sort block>");
    result_ = sortBlock_0(builder_, level_ + 1);
    result_ = result_ && sortBlock_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_SORT);
    pinned_ = result_; // pin = 3
    result_ = result_ && report_error_(builder_, sortBlock_3(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, sortBlock_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, sortBlock_5(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, sortBlock_6(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, sortBlock_7(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, sortBlock_8(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, sortBlock_9(builder_, level_ + 1)) && result_;
    result_ = pinned_ && sortBlock_10(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // blockLabel?
  private static boolean sortBlock_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortBlock_0")) return false;
    blockLabel(builder_, level_ + 1);
    return true;
  }

  // KW_AND?
  private static boolean sortBlock_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortBlock_1")) return false;
    consumeToken(builder_, KW_AND);
    return true;
  }

  // (KW_THEM | KW_RECORDS)?
  private static boolean sortBlock_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortBlock_3")) return false;
    sortBlock_3_0(builder_, level_ + 1);
    return true;
  }

  // KW_THEM | KW_RECORDS
  private static boolean sortBlock_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortBlock_3_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_THEM);
    if (!result_) result_ = consumeToken(builder_, KW_RECORDS);
    return result_;
  }

  // KW_BY?
  private static boolean sortBlock_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortBlock_4")) return false;
    consumeToken(builder_, KW_BY);
    return true;
  }

  // sortKey+
  private static boolean sortBlock_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortBlock_5")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = sortKey(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!sortKey(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "sortBlock_5", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_RECORDS?
  private static boolean sortBlock_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortBlock_6")) return false;
    consumeToken(builder_, KW_RECORDS);
    return true;
  }

  // sortUsingClause?
  private static boolean sortBlock_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortBlock_7")) return false;
    sortUsingClause(builder_, level_ + 1);
    return true;
  }

  // sortGiveClause?
  private static boolean sortBlock_8(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortBlock_8")) return false;
    sortGiveClause(builder_, level_ + 1);
    return true;
  }

  // statement*
  private static boolean sortBlock_9(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortBlock_9")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "sortBlock_9", pos_)) break;
    }
    return true;
  }

  // (KW_END_SORT | KW_END_ALL)?
  private static boolean sortBlock_10(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortBlock_10")) return false;
    sortBlock_10_0(builder_, level_ + 1);
    return true;
  }

  // KW_END_SORT | KW_END_ALL
  private static boolean sortBlock_10_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortBlock_10_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_END_SORT);
    if (!result_) result_ = consumeToken(builder_, KW_END_ALL);
    return result_;
  }

  /* ********************************************************** */
  // KW_GIVE sortGiveItem+
  public static boolean sortGiveClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortGiveClause")) return false;
    if (!nextTokenIs(builder_, KW_GIVE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SORT_GIVE_CLAUSE, null);
    result_ = consumeToken(builder_, KW_GIVE);
    pinned_ = result_; // pin = 1
    result_ = result_ && sortGiveClause_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // sortGiveItem+
  private static boolean sortGiveClause_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortGiveClause_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = sortGiveItem(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!sortGiveItem(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "sortGiveClause_1", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // sortAggregateFunction KW_OF? LPAREN expression+ RPAREN
  //                  (LPAREN SP_NL EQ_OP NUMBER RPAREN)?
  public static boolean sortGiveItem(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortGiveItem")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SORT_GIVE_ITEM, "<sort give item>");
    result_ = sortAggregateFunction(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, sortGiveItem_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, consumeToken(builder_, LPAREN)) && result_;
    result_ = pinned_ && report_error_(builder_, sortGiveItem_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, consumeToken(builder_, RPAREN)) && result_;
    result_ = pinned_ && sortGiveItem_5(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_OF?
  private static boolean sortGiveItem_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortGiveItem_1")) return false;
    consumeToken(builder_, KW_OF);
    return true;
  }

  // expression+
  private static boolean sortGiveItem_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortGiveItem_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!expression(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "sortGiveItem_3", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (LPAREN SP_NL EQ_OP NUMBER RPAREN)?
  private static boolean sortGiveItem_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortGiveItem_5")) return false;
    sortGiveItem_5_0(builder_, level_ + 1);
    return true;
  }

  // LPAREN SP_NL EQ_OP NUMBER RPAREN
  private static boolean sortGiveItem_5_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortGiveItem_5_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, LPAREN, SP_NL, EQ_OP, NUMBER, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // expression (KW_ASCENDING | KW_DESCENDING)?
  public static boolean sortKey(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortKey")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SORT_KEY, "<sort key>");
    result_ = expression(builder_, level_ + 1);
    result_ = result_ && sortKey_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (KW_ASCENDING | KW_DESCENDING)?
  private static boolean sortKey_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortKey_1")) return false;
    sortKey_1_0(builder_, level_ + 1);
    return true;
  }

  // KW_ASCENDING | KW_DESCENDING
  private static boolean sortKey_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortKey_1_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_ASCENDING);
    if (!result_) result_ = consumeToken(builder_, KW_DESCENDING);
    return result_;
  }

  /* ********************************************************** */
  // !statement expression KW_DESCENDING?
  static boolean sortKeyItem(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortKeyItem")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = sortKeyItem_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && sortKeyItem_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean sortKeyItem_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortKeyItem_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_DESCENDING?
  private static boolean sortKeyItem_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortKeyItem_2")) return false;
    consumeToken(builder_, KW_DESCENDING);
    return true;
  }

  /* ********************************************************** */
  // !statement expression
  static boolean sortUsingArg(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortUsingArg")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = sortUsingArg_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean sortUsingArg_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortUsingArg_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // KW_USING (KW_KEYS | sortUsingArg+)
  public static boolean sortUsingClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortUsingClause")) return false;
    if (!nextTokenIs(builder_, KW_USING)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SORT_USING_CLAUSE, null);
    result_ = consumeToken(builder_, KW_USING);
    pinned_ = result_; // pin = 1
    result_ = result_ && sortUsingClause_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_KEYS | sortUsingArg+
  private static boolean sortUsingClause_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortUsingClause_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_KEYS);
    if (!result_) result_ = sortUsingClause_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // sortUsingArg+
  private static boolean sortUsingClause_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortUsingClause_1_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = sortUsingArg(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!sortUsingArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "sortUsingClause_1_1", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_SORTED KW_BY? sortKeyItem+
  public static boolean sortedByClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortedByClause")) return false;
    if (!nextTokenIs(builder_, KW_SORTED)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SORTED_BY_CLAUSE, null);
    result_ = consumeToken(builder_, KW_SORTED);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, sortedByClause_1(builder_, level_ + 1));
    result_ = pinned_ && sortedByClause_2(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_BY?
  private static boolean sortedByClause_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortedByClause_1")) return false;
    consumeToken(builder_, KW_BY);
    return true;
  }

  // sortKeyItem+
  private static boolean sortedByClause_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sortedByClause_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = sortKeyItem(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!sortKeyItem(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "sortedByClause_2", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_STACK writeArg*
  public static boolean stackStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "stackStatement")) return false;
    if (!nextTokenIs(builder_, KW_STACK)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, STACK_STATEMENT, null);
    result_ = consumeToken(builder_, KW_STACK);
    pinned_ = result_; // pin = 1
    result_ = result_ && stackStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // writeArg*
  private static boolean stackStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "stackStatement_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "stackStatement_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_STARTING KW_WITH KW_ISN EQ_OP expression
  public static boolean startingWithIsn(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "startingWithIsn")) return false;
    if (!nextTokenIs(builder_, KW_STARTING)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_STARTING, KW_WITH, KW_ISN, EQ_OP);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, STARTING_WITH_ISN, result_);
    return result_;
  }

  /* ********************************************************** */
  // decideForBlock
  //   | decideOnBlock
  //   | ifNoRecordsFoundClause
  //   | ifBlock
  //   | forBlock
  //   | repeatBlock
  //   | defineSubroutineBlock
  //   | readBlock
  //   | readWorkBlock
  //   | findStatement
  //   | findBlock
  //   | sortBlock
  //   | atBreakBlock
  //   | atTopPageBlock
  //   | atEndOfPageBlock
  //   | atEndOfDataBlock
  //   | atStartOfDataBlock
  //   | onErrorBlock
  //   | callnatStatement
  //   | writeStatement
  //   | setTimeStatement
  //   | assignmentStatement
  //   | simpleStatements
  public static boolean statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "statement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, STATEMENT, "<statement>");
    result_ = decideForBlock(builder_, level_ + 1);
    if (!result_) result_ = decideOnBlock(builder_, level_ + 1);
    if (!result_) result_ = ifNoRecordsFoundClause(builder_, level_ + 1);
    if (!result_) result_ = ifBlock(builder_, level_ + 1);
    if (!result_) result_ = forBlock(builder_, level_ + 1);
    if (!result_) result_ = repeatBlock(builder_, level_ + 1);
    if (!result_) result_ = defineSubroutineBlock(builder_, level_ + 1);
    if (!result_) result_ = readBlock(builder_, level_ + 1);
    if (!result_) result_ = readWorkBlock(builder_, level_ + 1);
    if (!result_) result_ = findStatement(builder_, level_ + 1);
    if (!result_) result_ = findBlock(builder_, level_ + 1);
    if (!result_) result_ = sortBlock(builder_, level_ + 1);
    if (!result_) result_ = atBreakBlock(builder_, level_ + 1);
    if (!result_) result_ = atTopPageBlock(builder_, level_ + 1);
    if (!result_) result_ = atEndOfPageBlock(builder_, level_ + 1);
    if (!result_) result_ = atEndOfDataBlock(builder_, level_ + 1);
    if (!result_) result_ = atStartOfDataBlock(builder_, level_ + 1);
    if (!result_) result_ = onErrorBlock(builder_, level_ + 1);
    if (!result_) result_ = callnatStatement(builder_, level_ + 1);
    if (!result_) result_ = writeStatement(builder_, level_ + 1);
    if (!result_) result_ = setTimeStatement(builder_, level_ + 1);
    if (!result_) result_ = assignmentStatement(builder_, level_ + 1);
    if (!result_) result_ = simpleStatements(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // LPAREN (IDENTIFIER | KW_FIND | KW_READ | KW_REPEAT | KW_FOR | KW_PERFORM | KW_GET | KW_SORT | NUMBER) DOT? RPAREN
  static boolean statementRef(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "statementRef")) return false;
    if (!nextTokenIs(builder_, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && statementRef_1(builder_, level_ + 1);
    result_ = result_ && statementRef_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IDENTIFIER | KW_FIND | KW_READ | KW_REPEAT | KW_FOR | KW_PERFORM | KW_GET | KW_SORT | NUMBER
  private static boolean statementRef_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "statementRef_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, KW_FIND);
    if (!result_) result_ = consumeToken(builder_, KW_READ);
    if (!result_) result_ = consumeToken(builder_, KW_REPEAT);
    if (!result_) result_ = consumeToken(builder_, KW_FOR);
    if (!result_) result_ = consumeToken(builder_, KW_PERFORM);
    if (!result_) result_ = consumeToken(builder_, KW_GET);
    if (!result_) result_ = consumeToken(builder_, KW_SORT);
    if (!result_) result_ = consumeToken(builder_, NUMBER);
    return result_;
  }

  // DOT?
  private static boolean statementRef_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "statementRef_2")) return false;
    consumeToken(builder_, DOT);
    return true;
  }

  /* ********************************************************** */
  // KW_STOP
  public static boolean stopStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "stopStatement")) return false;
    if (!nextTokenIs(builder_, KW_STOP)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_STOP);
    exit_section_(builder_, marker_, STOP_STATEMENT, result_);
    return result_;
  }

  /* ********************************************************** */
  // (KW_USING | KW_GIVING) KW_NUMBER expression
  static boolean storeNumberClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "storeNumberClause")) return false;
    if (!nextTokenIs(builder_, "", KW_GIVING, KW_USING)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = storeNumberClause_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_NUMBER);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_USING | KW_GIVING
  private static boolean storeNumberClause_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "storeNumberClause_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_USING);
    if (!result_) result_ = consumeToken(builder_, KW_GIVING);
    return result_;
  }

  /* ********************************************************** */
  // blockLabel? KW_STORE KW_RECORD? KW_IN? KW_FILE? identifier
  //                          passwordClause?
  //                          cipherClause?
  //                          storeNumberClause?
  public static boolean storeStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "storeStatement")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, STORE_STATEMENT, "<store statement>");
    result_ = storeStatement_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KW_STORE);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, storeStatement_2(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, storeStatement_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, storeStatement_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, identifier(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, storeStatement_6(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, storeStatement_7(builder_, level_ + 1)) && result_;
    result_ = pinned_ && storeStatement_8(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // blockLabel?
  private static boolean storeStatement_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "storeStatement_0")) return false;
    blockLabel(builder_, level_ + 1);
    return true;
  }

  // KW_RECORD?
  private static boolean storeStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "storeStatement_2")) return false;
    consumeToken(builder_, KW_RECORD);
    return true;
  }

  // KW_IN?
  private static boolean storeStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "storeStatement_3")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // KW_FILE?
  private static boolean storeStatement_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "storeStatement_4")) return false;
    consumeToken(builder_, KW_FILE);
    return true;
  }

  // passwordClause?
  private static boolean storeStatement_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "storeStatement_6")) return false;
    passwordClause(builder_, level_ + 1);
    return true;
  }

  // cipherClause?
  private static boolean storeStatement_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "storeStatement_7")) return false;
    cipherClause(builder_, level_ + 1);
    return true;
  }

  // storeNumberClause?
  private static boolean storeStatement_8(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "storeStatement_8")) return false;
    storeNumberClause(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // STRING_LITERAL | identifier
  static boolean stringOrIdentifier(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "stringOrIdentifier")) return false;
    boolean result_;
    result_ = consumeToken(builder_, STRING_LITERAL);
    if (!result_) result_ = identifier(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // USER_VARIABLE | IDENTIFIER | keywordAsVarPrefix
  //   | KW_WRITE | KW_PRINT | KW_STOP | KW_SORT | KW_FIND | KW_GET | KW_INPUT | KW_FETCH | KW_CALL | KW_PERFORM | KW_DECIDE
  //   | KW_MARK | KW_RELEASE | KW_STACK | KW_EXAMINE | KW_SEPARATE | KW_TRANSLATE | KW_COMPRESS | KW_REDUCE
  //   | KW_EXPAND | KW_CONTROL | KW_IGNORE | KW_ESCAPE | KW_ADD | KW_SUBTRACT | KW_MULTIPLY | KW_DIVIDE
  //   | KW_COMPUTE | KW_ASSIGN | KW_DELETE | KW_END | KW_LOOP | KW_IF | KW_THEN | KW_ELSE | KW_AND | KW_OR
  //   | KW_NOT | KW_WHEN | KW_FOR | KW_REPEAT | KW_CALLNAT | KW_INCLUDE | KW_NEWPAGE | KW_SKIP | KW_INPUT
  //   | KW_READ | KW_UPDATE | KW_STORE | KW_DISPLAY | KW_HISTOGRAM | KW_RESET | KW_MOVE | KW_EJECT
  public static boolean subroutineName(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subroutineName")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SUBROUTINE_NAME, "<subroutine name>");
    result_ = consumeToken(builder_, USER_VARIABLE);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = keywordAsVarPrefix(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, KW_WRITE);
    if (!result_) result_ = consumeToken(builder_, KW_PRINT);
    if (!result_) result_ = consumeToken(builder_, KW_STOP);
    if (!result_) result_ = consumeToken(builder_, KW_SORT);
    if (!result_) result_ = consumeToken(builder_, KW_FIND);
    if (!result_) result_ = consumeToken(builder_, KW_GET);
    if (!result_) result_ = consumeToken(builder_, KW_INPUT);
    if (!result_) result_ = consumeToken(builder_, KW_FETCH);
    if (!result_) result_ = consumeToken(builder_, KW_CALL);
    if (!result_) result_ = consumeToken(builder_, KW_PERFORM);
    if (!result_) result_ = consumeToken(builder_, KW_DECIDE);
    if (!result_) result_ = consumeToken(builder_, KW_MARK);
    if (!result_) result_ = consumeToken(builder_, KW_RELEASE);
    if (!result_) result_ = consumeToken(builder_, KW_STACK);
    if (!result_) result_ = consumeToken(builder_, KW_EXAMINE);
    if (!result_) result_ = consumeToken(builder_, KW_SEPARATE);
    if (!result_) result_ = consumeToken(builder_, KW_TRANSLATE);
    if (!result_) result_ = consumeToken(builder_, KW_COMPRESS);
    if (!result_) result_ = consumeToken(builder_, KW_REDUCE);
    if (!result_) result_ = consumeToken(builder_, KW_EXPAND);
    if (!result_) result_ = consumeToken(builder_, KW_CONTROL);
    if (!result_) result_ = consumeToken(builder_, KW_IGNORE);
    if (!result_) result_ = consumeToken(builder_, KW_ESCAPE);
    if (!result_) result_ = consumeToken(builder_, KW_ADD);
    if (!result_) result_ = consumeToken(builder_, KW_SUBTRACT);
    if (!result_) result_ = consumeToken(builder_, KW_MULTIPLY);
    if (!result_) result_ = consumeToken(builder_, KW_DIVIDE);
    if (!result_) result_ = consumeToken(builder_, KW_COMPUTE);
    if (!result_) result_ = consumeToken(builder_, KW_ASSIGN);
    if (!result_) result_ = consumeToken(builder_, KW_DELETE);
    if (!result_) result_ = consumeToken(builder_, KW_END);
    if (!result_) result_ = consumeToken(builder_, KW_LOOP);
    if (!result_) result_ = consumeToken(builder_, KW_IF);
    if (!result_) result_ = consumeToken(builder_, KW_THEN);
    if (!result_) result_ = consumeToken(builder_, KW_ELSE);
    if (!result_) result_ = consumeToken(builder_, KW_AND);
    if (!result_) result_ = consumeToken(builder_, KW_OR);
    if (!result_) result_ = consumeToken(builder_, KW_NOT);
    if (!result_) result_ = consumeToken(builder_, KW_WHEN);
    if (!result_) result_ = consumeToken(builder_, KW_FOR);
    if (!result_) result_ = consumeToken(builder_, KW_REPEAT);
    if (!result_) result_ = consumeToken(builder_, KW_CALLNAT);
    if (!result_) result_ = consumeToken(builder_, KW_INCLUDE);
    if (!result_) result_ = consumeToken(builder_, KW_NEWPAGE);
    if (!result_) result_ = consumeToken(builder_, KW_SKIP);
    if (!result_) result_ = consumeToken(builder_, KW_INPUT);
    if (!result_) result_ = consumeToken(builder_, KW_READ);
    if (!result_) result_ = consumeToken(builder_, KW_UPDATE);
    if (!result_) result_ = consumeToken(builder_, KW_STORE);
    if (!result_) result_ = consumeToken(builder_, KW_DISPLAY);
    if (!result_) result_ = consumeToken(builder_, KW_HISTOGRAM);
    if (!result_) result_ = consumeToken(builder_, KW_RESET);
    if (!result_) result_ = consumeToken(builder_, KW_MOVE);
    if (!result_) result_ = consumeToken(builder_, KW_EJECT);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // USER_VARIABLE | IDENTIFIER | KW_PRINT | KW_WRITE | KW_DISPLAY | KW_INPUT | KW_REINPUT | KW_FORMAT | KW_STOP | KW_GET | KW_FIND | KW_SORT | KW_READ | KW_ADD | KW_MOVE | KW_RESET | KW_ESCAPE | KW_IF | KW_FOR | KW_REPEAT | KW_FETCH | KW_CALL | KW_EXAMINE | KW_COMPRESS | KW_DELETE | KW_STORE | KW_UPDATE | KW_CLOSE | KW_RELEASE | KW_INCLUDE | KW_HISTOGRAM | KW_SELECT
  public static boolean subroutineRef(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subroutineRef")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SUBROUTINE_REF, "<subroutine ref>");
    result_ = consumeToken(builder_, USER_VARIABLE);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, KW_PRINT);
    if (!result_) result_ = consumeToken(builder_, KW_WRITE);
    if (!result_) result_ = consumeToken(builder_, KW_DISPLAY);
    if (!result_) result_ = consumeToken(builder_, KW_INPUT);
    if (!result_) result_ = consumeToken(builder_, KW_REINPUT);
    if (!result_) result_ = consumeToken(builder_, KW_FORMAT);
    if (!result_) result_ = consumeToken(builder_, KW_STOP);
    if (!result_) result_ = consumeToken(builder_, KW_GET);
    if (!result_) result_ = consumeToken(builder_, KW_FIND);
    if (!result_) result_ = consumeToken(builder_, KW_SORT);
    if (!result_) result_ = consumeToken(builder_, KW_READ);
    if (!result_) result_ = consumeToken(builder_, KW_ADD);
    if (!result_) result_ = consumeToken(builder_, KW_MOVE);
    if (!result_) result_ = consumeToken(builder_, KW_RESET);
    if (!result_) result_ = consumeToken(builder_, KW_ESCAPE);
    if (!result_) result_ = consumeToken(builder_, KW_IF);
    if (!result_) result_ = consumeToken(builder_, KW_FOR);
    if (!result_) result_ = consumeToken(builder_, KW_REPEAT);
    if (!result_) result_ = consumeToken(builder_, KW_FETCH);
    if (!result_) result_ = consumeToken(builder_, KW_CALL);
    if (!result_) result_ = consumeToken(builder_, KW_EXAMINE);
    if (!result_) result_ = consumeToken(builder_, KW_COMPRESS);
    if (!result_) result_ = consumeToken(builder_, KW_DELETE);
    if (!result_) result_ = consumeToken(builder_, KW_STORE);
    if (!result_) result_ = consumeToken(builder_, KW_UPDATE);
    if (!result_) result_ = consumeToken(builder_, KW_CLOSE);
    if (!result_) result_ = consumeToken(builder_, KW_RELEASE);
    if (!result_) result_ = consumeToken(builder_, KW_INCLUDE);
    if (!result_) result_ = consumeToken(builder_, KW_HISTOGRAM);
    if (!result_) result_ = consumeToken(builder_, KW_SELECT);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // "(" (subscriptParamGroup | subscriptRange? ("," subscriptRange)*) ")"
  public static boolean subscript(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscript")) return false;
    if (!nextTokenIs(builder_, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && subscript_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, SUBSCRIPT, result_);
    return result_;
  }

  // subscriptParamGroup | subscriptRange? ("," subscriptRange)*
  private static boolean subscript_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscript_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = subscriptParamGroup(builder_, level_ + 1);
    if (!result_) result_ = subscript_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // subscriptRange? ("," subscriptRange)*
  private static boolean subscript_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscript_1_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = subscript_1_1_0(builder_, level_ + 1);
    result_ = result_ && subscript_1_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // subscriptRange?
  private static boolean subscript_1_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscript_1_1_0")) return false;
    subscriptRange(builder_, level_ + 1);
    return true;
  }

  // ("," subscriptRange)*
  private static boolean subscript_1_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscript_1_1_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!subscript_1_1_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "subscript_1_1_1", pos_)) break;
    }
    return true;
  }

  // "," subscriptRange
  private static boolean subscript_1_1_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscript_1_1_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && subscriptRange(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_FIND | KW_READ | KW_REPEAT | KW_FOR | KW_GET | KW_SORT | KW_PERFORM
  static boolean subscriptLabelKeyword(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscriptLabelKeyword")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_FIND);
    if (!result_) result_ = consumeToken(builder_, KW_READ);
    if (!result_) result_ = consumeToken(builder_, KW_REPEAT);
    if (!result_) result_ = consumeToken(builder_, KW_FOR);
    if (!result_) result_ = consumeToken(builder_, KW_GET);
    if (!result_) result_ = consumeToken(builder_, KW_SORT);
    if (!result_) result_ = consumeToken(builder_, KW_PERFORM);
    return result_;
  }

  /* ********************************************************** */
  // sessionParameter EQ_OP editMaskValue ("," sessionParameter EQ_OP editMaskValue)*
  static boolean subscriptParamGroup(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscriptParamGroup")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = sessionParameter(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, EQ_OP);
    result_ = result_ && editMaskValue(builder_, level_ + 1);
    result_ = result_ && subscriptParamGroup_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ("," sessionParameter EQ_OP editMaskValue)*
  private static boolean subscriptParamGroup_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscriptParamGroup_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!subscriptParamGroup_3_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "subscriptParamGroup_3", pos_)) break;
    }
    return true;
  }

  // "," sessionParameter EQ_OP editMaskValue
  private static boolean subscriptParamGroup_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscriptParamGroup_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && sessionParameter(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, EQ_OP);
    result_ = result_ && editMaskValue(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // STAR | subscriptLabelKeyword DOT | expression DOT? (":" expression DOT?)?
  public static boolean subscriptRange(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscriptRange")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SUBSCRIPT_RANGE, "<subscript range>");
    result_ = consumeToken(builder_, STAR);
    if (!result_) result_ = subscriptRange_1(builder_, level_ + 1);
    if (!result_) result_ = subscriptRange_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // subscriptLabelKeyword DOT
  private static boolean subscriptRange_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscriptRange_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = subscriptLabelKeyword(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, DOT);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // expression DOT? (":" expression DOT?)?
  private static boolean subscriptRange_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscriptRange_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1);
    result_ = result_ && subscriptRange_2_1(builder_, level_ + 1);
    result_ = result_ && subscriptRange_2_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // DOT?
  private static boolean subscriptRange_2_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscriptRange_2_1")) return false;
    consumeToken(builder_, DOT);
    return true;
  }

  // (":" expression DOT?)?
  private static boolean subscriptRange_2_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscriptRange_2_2")) return false;
    subscriptRange_2_2_0(builder_, level_ + 1);
    return true;
  }

  // ":" expression DOT?
  private static boolean subscriptRange_2_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscriptRange_2_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COLON);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && subscriptRange_2_2_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // DOT?
  private static boolean subscriptRange_2_2_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subscriptRange_2_2_0_2")) return false;
    consumeToken(builder_, DOT);
    return true;
  }

  /* ********************************************************** */
  // KW_SUBTRACT (!KW_FROM !KW_GIVING !statement expression)+ (KW_FROM expression)? (KW_GIVING expression)?
  public static boolean subtractStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subtractStatement")) return false;
    if (!nextTokenIs(builder_, KW_SUBTRACT)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SUBTRACT_STATEMENT, null);
    result_ = consumeToken(builder_, KW_SUBTRACT);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, subtractStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, subtractStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && subtractStatement_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (!KW_FROM !KW_GIVING !statement expression)+
  private static boolean subtractStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subtractStatement_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = subtractStatement_1_0(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!subtractStatement_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "subtractStatement_1", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !KW_FROM !KW_GIVING !statement expression
  private static boolean subtractStatement_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subtractStatement_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = subtractStatement_1_0_0(builder_, level_ + 1);
    result_ = result_ && subtractStatement_1_0_1(builder_, level_ + 1);
    result_ = result_ && subtractStatement_1_0_2(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !KW_FROM
  private static boolean subtractStatement_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subtractStatement_1_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, KW_FROM);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !KW_GIVING
  private static boolean subtractStatement_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subtractStatement_1_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, KW_GIVING);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !statement
  private static boolean subtractStatement_1_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subtractStatement_1_0_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (KW_FROM expression)?
  private static boolean subtractStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subtractStatement_2")) return false;
    subtractStatement_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_FROM expression
  private static boolean subtractStatement_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subtractStatement_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_FROM);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (KW_GIVING expression)?
  private static boolean subtractStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subtractStatement_3")) return false;
    subtractStatement_3_0(builder_, level_ + 1);
    return true;
  }

  // KW_GIVING expression
  private static boolean subtractStatement_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subtractStatement_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_GIVING);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_SUM | KW_MAX | KW_MIN | KW_NMIN | KW_COUNT | KW_NCOUNT
  //   | KW_AVER | KW_NAVER | KW_OLD | KW_TOTAL
  //   | KW_SUBSTRING | KW_SV_TRANSLATE
  static boolean systemFunctionName(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "systemFunctionName")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_SUM);
    if (!result_) result_ = consumeToken(builder_, KW_MAX);
    if (!result_) result_ = consumeToken(builder_, KW_MIN);
    if (!result_) result_ = consumeToken(builder_, KW_NMIN);
    if (!result_) result_ = consumeToken(builder_, KW_COUNT);
    if (!result_) result_ = consumeToken(builder_, KW_NCOUNT);
    if (!result_) result_ = consumeToken(builder_, KW_AVER);
    if (!result_) result_ = consumeToken(builder_, KW_NAVER);
    if (!result_) result_ = consumeToken(builder_, KW_OLD);
    if (!result_) result_ = consumeToken(builder_, KW_TOTAL);
    if (!result_) result_ = consumeToken(builder_, KW_SUBSTRING);
    if (!result_) result_ = consumeToken(builder_, KW_SV_TRANSLATE);
    return result_;
  }

  /* ********************************************************** */
  // systemVariables (DOT IDENTIFIER)? subscript?
  public static boolean systemVarRef(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "systemVarRef")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SYSTEM_VAR_REF, "<system var ref>");
    result_ = systemVariables(builder_, level_ + 1);
    result_ = result_ && systemVarRef_1(builder_, level_ + 1);
    result_ = result_ && systemVarRef_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (DOT IDENTIFIER)?
  private static boolean systemVarRef_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "systemVarRef_1")) return false;
    systemVarRef_1_0(builder_, level_ + 1);
    return true;
  }

  // DOT IDENTIFIER
  private static boolean systemVarRef_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "systemVarRef_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, DOT, IDENTIFIER);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // subscript?
  private static boolean systemVarRef_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "systemVarRef_2")) return false;
    subscript(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // SYSTEM_VARIABLE | SV_DATX | SV_USER |
  //     // Application-related
  //     SV_APPLIC_ID | SV_APPLIC_NAME | SV_COM | SV_CONVID | SV_COUNTER | SV_CPU_TIME |
  //     SV_CURRENT_UNIT | SV_DATA | SV_ERROR_LINE | SV_ERROR_NR | SV_ERROR_TA | SV_ETID |
  //     SV_ISN | SV_LBOUND | SV_LENGTH | SV_LEVEL | SV_LIBRARY_ID | SV_LINE | SV_LINEX |
  //     SV_LOAD_LIBRARY_ID | SV_NUMBER | SV_OCCURRENCE | SV_PAGE_EVENT | SV_PAGE_LEVEL |
  //     SV_PROGRAM | SV_REINPUT_TYPE | SV_ROWCOUNT | SV_STARTUP | SV_STEPLIB |
  //     SV_SUBROUTINE | SV_THIS_OBJECT | SV_TYPE | SV_UBOUND |
  //     // Date/Time
  //     SV_DATD | SV_DAT4D | SV_DATE | SV_DAT4E | SV_DATG | SV_DATI | SV_DAT4I |
  //     SV_DATJ | SV_DAT4J | SV_DATN | SV_DATU | SV_DAT4U | SV_DATV | SV_DATVS |
  //     SV_TIMD | SV_TIME | SV_TIME_OUT | SV_TIMESTMP | SV_TIMN | SV_TIMX |
  //     // Input/Output
  //     SV_CURS_COL | SV_CURS_FIELD | SV_CURS_LINE | SV_CURSOR | SV_LINE_COUNT |
  //     SV_LINESIZE | SV_LOG_LS | SV_LOG_PS | SV_PAGE_NUMBER | SV_PAGESIZE |
  //     SV_PF_KEY | SV_PF_NAME | SV_WINDOW_LS | SV_WINDOW_POS | SV_WINDOW_PS |
  //     // Natural environment
  //     SV_BROWSER_IO | SV_DEVICE | SV_GROUP | SV_HARDCOPY | SV_INIT_USER | SV_LANGUAGE |
  //     SV_NATVERS | SV_NET_USER | SV_PARM_USER | SV_PATCH_LEVEL | SV_PID | SV_SCREEN_IO |
  //     SV_SERVER_TYPE | SV_UI | SV_USER_NAME |
  //     // System environment
  //     SV_CODEPAGE | SV_HARDWARE | SV_HOSTNAME | SV_INIT_ID | SV_INIT_PROGRAM | SV_LOCALE |
  //     SV_MACHINE_CLASS | SV_OPSYS | SV_OS | SV_OSVERS | SV_TP | SV_TPSYS | SV_TPVERS |
  //     SV_WINMGR | SV_WINMGRVERS |
  //     // XML
  //     SV_PARSE_COL | SV_PARSE_LEVEL | SV_PARSE_NAMESPACE_URI | SV_PARSE_ROW | SV_PARSE_TYPE
  static boolean systemVariables(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "systemVariables")) return false;
    boolean result_;
    result_ = consumeToken(builder_, SYSTEM_VARIABLE);
    if (!result_) result_ = consumeToken(builder_, SV_DATX);
    if (!result_) result_ = consumeToken(builder_, SV_USER);
    if (!result_) result_ = consumeToken(builder_, SV_APPLIC_ID);
    if (!result_) result_ = consumeToken(builder_, SV_APPLIC_NAME);
    if (!result_) result_ = consumeToken(builder_, SV_COM);
    if (!result_) result_ = consumeToken(builder_, SV_CONVID);
    if (!result_) result_ = consumeToken(builder_, SV_COUNTER);
    if (!result_) result_ = consumeToken(builder_, SV_CPU_TIME);
    if (!result_) result_ = consumeToken(builder_, SV_CURRENT_UNIT);
    if (!result_) result_ = consumeToken(builder_, SV_DATA);
    if (!result_) result_ = consumeToken(builder_, SV_ERROR_LINE);
    if (!result_) result_ = consumeToken(builder_, SV_ERROR_NR);
    if (!result_) result_ = consumeToken(builder_, SV_ERROR_TA);
    if (!result_) result_ = consumeToken(builder_, SV_ETID);
    if (!result_) result_ = consumeToken(builder_, SV_ISN);
    if (!result_) result_ = consumeToken(builder_, SV_LBOUND);
    if (!result_) result_ = consumeToken(builder_, SV_LENGTH);
    if (!result_) result_ = consumeToken(builder_, SV_LEVEL);
    if (!result_) result_ = consumeToken(builder_, SV_LIBRARY_ID);
    if (!result_) result_ = consumeToken(builder_, SV_LINE);
    if (!result_) result_ = consumeToken(builder_, SV_LINEX);
    if (!result_) result_ = consumeToken(builder_, SV_LOAD_LIBRARY_ID);
    if (!result_) result_ = consumeToken(builder_, SV_NUMBER);
    if (!result_) result_ = consumeToken(builder_, SV_OCCURRENCE);
    if (!result_) result_ = consumeToken(builder_, SV_PAGE_EVENT);
    if (!result_) result_ = consumeToken(builder_, SV_PAGE_LEVEL);
    if (!result_) result_ = consumeToken(builder_, SV_PROGRAM);
    if (!result_) result_ = consumeToken(builder_, SV_REINPUT_TYPE);
    if (!result_) result_ = consumeToken(builder_, SV_ROWCOUNT);
    if (!result_) result_ = consumeToken(builder_, SV_STARTUP);
    if (!result_) result_ = consumeToken(builder_, SV_STEPLIB);
    if (!result_) result_ = consumeToken(builder_, SV_SUBROUTINE);
    if (!result_) result_ = consumeToken(builder_, SV_THIS_OBJECT);
    if (!result_) result_ = consumeToken(builder_, SV_TYPE);
    if (!result_) result_ = consumeToken(builder_, SV_UBOUND);
    if (!result_) result_ = consumeToken(builder_, SV_DATD);
    if (!result_) result_ = consumeToken(builder_, SV_DAT4D);
    if (!result_) result_ = consumeToken(builder_, SV_DATE);
    if (!result_) result_ = consumeToken(builder_, SV_DAT4E);
    if (!result_) result_ = consumeToken(builder_, SV_DATG);
    if (!result_) result_ = consumeToken(builder_, SV_DATI);
    if (!result_) result_ = consumeToken(builder_, SV_DAT4I);
    if (!result_) result_ = consumeToken(builder_, SV_DATJ);
    if (!result_) result_ = consumeToken(builder_, SV_DAT4J);
    if (!result_) result_ = consumeToken(builder_, SV_DATN);
    if (!result_) result_ = consumeToken(builder_, SV_DATU);
    if (!result_) result_ = consumeToken(builder_, SV_DAT4U);
    if (!result_) result_ = consumeToken(builder_, SV_DATV);
    if (!result_) result_ = consumeToken(builder_, SV_DATVS);
    if (!result_) result_ = consumeToken(builder_, SV_TIMD);
    if (!result_) result_ = consumeToken(builder_, SV_TIME);
    if (!result_) result_ = consumeToken(builder_, SV_TIME_OUT);
    if (!result_) result_ = consumeToken(builder_, SV_TIMESTMP);
    if (!result_) result_ = consumeToken(builder_, SV_TIMN);
    if (!result_) result_ = consumeToken(builder_, SV_TIMX);
    if (!result_) result_ = consumeToken(builder_, SV_CURS_COL);
    if (!result_) result_ = consumeToken(builder_, SV_CURS_FIELD);
    if (!result_) result_ = consumeToken(builder_, SV_CURS_LINE);
    if (!result_) result_ = consumeToken(builder_, SV_CURSOR);
    if (!result_) result_ = consumeToken(builder_, SV_LINE_COUNT);
    if (!result_) result_ = consumeToken(builder_, SV_LINESIZE);
    if (!result_) result_ = consumeToken(builder_, SV_LOG_LS);
    if (!result_) result_ = consumeToken(builder_, SV_LOG_PS);
    if (!result_) result_ = consumeToken(builder_, SV_PAGE_NUMBER);
    if (!result_) result_ = consumeToken(builder_, SV_PAGESIZE);
    if (!result_) result_ = consumeToken(builder_, SV_PF_KEY);
    if (!result_) result_ = consumeToken(builder_, SV_PF_NAME);
    if (!result_) result_ = consumeToken(builder_, SV_WINDOW_LS);
    if (!result_) result_ = consumeToken(builder_, SV_WINDOW_POS);
    if (!result_) result_ = consumeToken(builder_, SV_WINDOW_PS);
    if (!result_) result_ = consumeToken(builder_, SV_BROWSER_IO);
    if (!result_) result_ = consumeToken(builder_, SV_DEVICE);
    if (!result_) result_ = consumeToken(builder_, SV_GROUP);
    if (!result_) result_ = consumeToken(builder_, SV_HARDCOPY);
    if (!result_) result_ = consumeToken(builder_, SV_INIT_USER);
    if (!result_) result_ = consumeToken(builder_, SV_LANGUAGE);
    if (!result_) result_ = consumeToken(builder_, SV_NATVERS);
    if (!result_) result_ = consumeToken(builder_, SV_NET_USER);
    if (!result_) result_ = consumeToken(builder_, SV_PARM_USER);
    if (!result_) result_ = consumeToken(builder_, SV_PATCH_LEVEL);
    if (!result_) result_ = consumeToken(builder_, SV_PID);
    if (!result_) result_ = consumeToken(builder_, SV_SCREEN_IO);
    if (!result_) result_ = consumeToken(builder_, SV_SERVER_TYPE);
    if (!result_) result_ = consumeToken(builder_, SV_UI);
    if (!result_) result_ = consumeToken(builder_, SV_USER_NAME);
    if (!result_) result_ = consumeToken(builder_, SV_CODEPAGE);
    if (!result_) result_ = consumeToken(builder_, SV_HARDWARE);
    if (!result_) result_ = consumeToken(builder_, SV_HOSTNAME);
    if (!result_) result_ = consumeToken(builder_, SV_INIT_ID);
    if (!result_) result_ = consumeToken(builder_, SV_INIT_PROGRAM);
    if (!result_) result_ = consumeToken(builder_, SV_LOCALE);
    if (!result_) result_ = consumeToken(builder_, SV_MACHINE_CLASS);
    if (!result_) result_ = consumeToken(builder_, SV_OPSYS);
    if (!result_) result_ = consumeToken(builder_, SV_OS);
    if (!result_) result_ = consumeToken(builder_, SV_OSVERS);
    if (!result_) result_ = consumeToken(builder_, SV_TP);
    if (!result_) result_ = consumeToken(builder_, SV_TPSYS);
    if (!result_) result_ = consumeToken(builder_, SV_TPVERS);
    if (!result_) result_ = consumeToken(builder_, SV_WINMGR);
    if (!result_) result_ = consumeToken(builder_, SV_WINMGRVERS);
    if (!result_) result_ = consumeToken(builder_, SV_PARSE_COL);
    if (!result_) result_ = consumeToken(builder_, SV_PARSE_LEVEL);
    if (!result_) result_ = consumeToken(builder_, SV_PARSE_NAMESPACE_URI);
    if (!result_) result_ = consumeToken(builder_, SV_PARSE_ROW);
    if (!result_) result_ = consumeToken(builder_, SV_PARSE_TYPE);
    return result_;
  }

  /* ********************************************************** */
  // KW_TERMINATE (expression (expression)?)?
  public static boolean terminateStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "terminateStatement")) return false;
    if (!nextTokenIs(builder_, KW_TERMINATE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TERMINATE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_TERMINATE);
    pinned_ = result_; // pin = 1
    result_ = result_ && terminateStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (expression (expression)?)?
  private static boolean terminateStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "terminateStatement_1")) return false;
    terminateStatement_1_0(builder_, level_ + 1);
    return true;
  }

  // expression (expression)?
  private static boolean terminateStatement_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "terminateStatement_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1);
    result_ = result_ && terminateStatement_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (expression)?
  private static boolean terminateStatement_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "terminateStatement_1_0_1")) return false;
    terminateStatement_1_0_1_0(builder_, level_ + 1);
    return true;
  }

  // (expression)
  private static boolean terminateStatement_1_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "terminateStatement_1_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_THRU | KW_ENDING KW_AT
  static boolean thruOrEndingAt(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "thruOrEndingAt")) return false;
    if (!nextTokenIs(builder_, "", KW_ENDING, KW_THRU)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_THRU);
    if (!result_) result_ = parseTokens(builder_, 0, KW_ENDING, KW_AT);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_TRANSLATE expression (KW_USING expression)?
  public static boolean translateStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "translateStatement")) return false;
    if (!nextTokenIs(builder_, KW_TRANSLATE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TRANSLATE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_TRANSLATE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, expression(builder_, level_ + 1));
    result_ = pinned_ && translateStatement_2(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_USING expression)?
  private static boolean translateStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "translateStatement_2")) return false;
    translateStatement_2_0(builder_, level_ + 1);
    return true;
  }

  // KW_USING expression
  private static boolean translateStatement_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "translateStatement_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_USING);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // KW_SV_TRANSLATE LPAREN expression COMMA (KW_UPPER | KW_LOWER) RPAREN
  static boolean translateSystemFunction(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "translateSystemFunction")) return false;
    if (!nextTokenIs(builder_, KW_SV_TRANSLATE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, KW_SV_TRANSLATE, LPAREN);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, COMMA);
    result_ = result_ && translateSystemFunction_4(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_UPPER | KW_LOWER
  private static boolean translateSystemFunction_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "translateSystemFunction_4")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_UPPER);
    if (!result_) result_ = consumeToken(builder_, KW_LOWER);
    return result_;
  }

  /* ********************************************************** */
  // SP_IS LPAREN (IDENTIFIER | NUMBER) (DOT NUMBER)? RPAREN
  static boolean typeCheck(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "typeCheck")) return false;
    if (!nextTokenIs(builder_, SP_IS)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, SP_IS, LPAREN);
    result_ = result_ && typeCheck_2(builder_, level_ + 1);
    result_ = result_ && typeCheck_3(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IDENTIFIER | NUMBER
  private static boolean typeCheck_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "typeCheck_2")) return false;
    boolean result_;
    result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, NUMBER);
    return result_;
  }

  // (DOT NUMBER)?
  private static boolean typeCheck_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "typeCheck_3")) return false;
    typeCheck_3_0(builder_, level_ + 1);
    return true;
  }

  // DOT NUMBER
  private static boolean typeCheck_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "typeCheck_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, DOT, NUMBER);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // (MINUS | PLUS)? primary
  public static boolean unaryExpr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "unaryExpr")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, UNARY_EXPR, "<unary expr>");
    result_ = unaryExpr_0(builder_, level_ + 1);
    result_ = result_ && primary(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (MINUS | PLUS)?
  private static boolean unaryExpr_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "unaryExpr_0")) return false;
    unaryExpr_0_0(builder_, level_ + 1);
    return true;
  }

  // MINUS | PLUS
  private static boolean unaryExpr_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "unaryExpr_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, MINUS);
    if (!result_) result_ = consumeToken(builder_, PLUS);
    return result_;
  }

  /* ********************************************************** */
  // KW_UPDATE KW_RECORD? KW_IN? KW_STATEMENT? statementRef?
  public static boolean updateStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "updateStatement")) return false;
    if (!nextTokenIs(builder_, KW_UPDATE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, UPDATE_STATEMENT, null);
    result_ = consumeToken(builder_, KW_UPDATE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, updateStatement_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, updateStatement_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, updateStatement_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && updateStatement_4(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_RECORD?
  private static boolean updateStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "updateStatement_1")) return false;
    consumeToken(builder_, KW_RECORD);
    return true;
  }

  // KW_IN?
  private static boolean updateStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "updateStatement_2")) return false;
    consumeToken(builder_, KW_IN);
    return true;
  }

  // KW_STATEMENT?
  private static boolean updateStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "updateStatement_3")) return false;
    consumeToken(builder_, KW_STATEMENT);
    return true;
  }

  // statementRef?
  private static boolean updateStatement_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "updateStatement_4")) return false;
    statementRef(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // (LEVEL_NUMBER | NUMBER) (KW_REDEFINE variableName | variableName dataTypeOrViewSpec? variableModifier*)
  public static boolean variableDecl(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableDecl")) return false;
    if (!nextTokenIs(builder_, "<variable decl>", LEVEL_NUMBER, NUMBER)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, VARIABLE_DECL, "<variable decl>");
    result_ = variableDecl_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && variableDecl_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // LEVEL_NUMBER | NUMBER
  private static boolean variableDecl_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableDecl_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, LEVEL_NUMBER);
    if (!result_) result_ = consumeToken(builder_, NUMBER);
    return result_;
  }

  // KW_REDEFINE variableName | variableName dataTypeOrViewSpec? variableModifier*
  private static boolean variableDecl_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableDecl_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = variableDecl_1_0(builder_, level_ + 1);
    if (!result_) result_ = variableDecl_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_REDEFINE variableName
  private static boolean variableDecl_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableDecl_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_REDEFINE);
    result_ = result_ && variableName(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // variableName dataTypeOrViewSpec? variableModifier*
  private static boolean variableDecl_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableDecl_1_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = variableName(builder_, level_ + 1);
    result_ = result_ && variableDecl_1_1_1(builder_, level_ + 1);
    result_ = result_ && variableDecl_1_1_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // dataTypeOrViewSpec?
  private static boolean variableDecl_1_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableDecl_1_1_1")) return false;
    dataTypeOrViewSpec(builder_, level_ + 1);
    return true;
  }

  // variableModifier*
  private static boolean variableDecl_1_1_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableDecl_1_1_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!variableModifier(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "variableDecl_1_1_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_DYNAMIC
  //   | KW_CONST initValueGroup?
  //   | (KW_INIT | KW_INITIAL) initLengthSpec? KW_ALL?
  //   | initValueGroup
  //   | initIndexedValue
  //   | KW_REDEFINE
  //   | KW_HANDLE_OF
  //   | KW_BY KW_VALUE? KW_RESULT?
  //   | KW_OPTIONAL
  //   | defineDataEditMask
  //   | arraySpec
  public static boolean variableModifier(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableModifier")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, VARIABLE_MODIFIER, "<variable modifier>");
    result_ = consumeToken(builder_, KW_DYNAMIC);
    if (!result_) result_ = variableModifier_1(builder_, level_ + 1);
    if (!result_) result_ = variableModifier_2(builder_, level_ + 1);
    if (!result_) result_ = initValueGroup(builder_, level_ + 1);
    if (!result_) result_ = initIndexedValue(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, KW_REDEFINE);
    if (!result_) result_ = consumeToken(builder_, KW_HANDLE_OF);
    if (!result_) result_ = variableModifier_7(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, KW_OPTIONAL);
    if (!result_) result_ = defineDataEditMask(builder_, level_ + 1);
    if (!result_) result_ = arraySpec(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // KW_CONST initValueGroup?
  private static boolean variableModifier_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableModifier_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_CONST);
    result_ = result_ && variableModifier_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // initValueGroup?
  private static boolean variableModifier_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableModifier_1_1")) return false;
    initValueGroup(builder_, level_ + 1);
    return true;
  }

  // (KW_INIT | KW_INITIAL) initLengthSpec? KW_ALL?
  private static boolean variableModifier_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableModifier_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = variableModifier_2_0(builder_, level_ + 1);
    result_ = result_ && variableModifier_2_1(builder_, level_ + 1);
    result_ = result_ && variableModifier_2_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_INIT | KW_INITIAL
  private static boolean variableModifier_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableModifier_2_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KW_INIT);
    if (!result_) result_ = consumeToken(builder_, KW_INITIAL);
    return result_;
  }

  // initLengthSpec?
  private static boolean variableModifier_2_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableModifier_2_1")) return false;
    initLengthSpec(builder_, level_ + 1);
    return true;
  }

  // KW_ALL?
  private static boolean variableModifier_2_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableModifier_2_2")) return false;
    consumeToken(builder_, KW_ALL);
    return true;
  }

  // KW_BY KW_VALUE? KW_RESULT?
  private static boolean variableModifier_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableModifier_7")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_BY);
    result_ = result_ && variableModifier_7_1(builder_, level_ + 1);
    result_ = result_ && variableModifier_7_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_VALUE?
  private static boolean variableModifier_7_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableModifier_7_1")) return false;
    consumeToken(builder_, KW_VALUE);
    return true;
  }

  // KW_RESULT?
  private static boolean variableModifier_7_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableModifier_7_2")) return false;
    consumeToken(builder_, KW_RESULT);
    return true;
  }

  /* ********************************************************** */
  // USER_VARIABLE | SUBST_PARAM | IDENTIFIER (DOT IDENTIFIER | SLASH (IDENTIFIER | NUMBER (MINUS NUMBER)?))*
  static boolean variableName(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableName")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, USER_VARIABLE);
    if (!result_) result_ = consumeToken(builder_, SUBST_PARAM);
    if (!result_) result_ = variableName_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IDENTIFIER (DOT IDENTIFIER | SLASH (IDENTIFIER | NUMBER (MINUS NUMBER)?))*
  private static boolean variableName_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableName_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IDENTIFIER);
    result_ = result_ && variableName_2_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (DOT IDENTIFIER | SLASH (IDENTIFIER | NUMBER (MINUS NUMBER)?))*
  private static boolean variableName_2_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableName_2_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!variableName_2_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "variableName_2_1", pos_)) break;
    }
    return true;
  }

  // DOT IDENTIFIER | SLASH (IDENTIFIER | NUMBER (MINUS NUMBER)?)
  private static boolean variableName_2_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableName_2_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = parseTokens(builder_, 0, DOT, IDENTIFIER);
    if (!result_) result_ = variableName_2_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // SLASH (IDENTIFIER | NUMBER (MINUS NUMBER)?)
  private static boolean variableName_2_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableName_2_1_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, SLASH);
    result_ = result_ && variableName_2_1_0_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IDENTIFIER | NUMBER (MINUS NUMBER)?
  private static boolean variableName_2_1_0_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableName_2_1_0_1_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = variableName_2_1_0_1_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // NUMBER (MINUS NUMBER)?
  private static boolean variableName_2_1_0_1_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableName_2_1_0_1_1_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, NUMBER);
    result_ = result_ && variableName_2_1_0_1_1_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (MINUS NUMBER)?
  private static boolean variableName_2_1_0_1_1_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableName_2_1_0_1_1_1_1")) return false;
    variableName_2_1_0_1_1_1_1_0(builder_, level_ + 1);
    return true;
  }

  // MINUS NUMBER
  private static boolean variableName_2_1_0_1_1_1_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableName_2_1_0_1_1_1_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, MINUS, NUMBER);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // (USER_VARIABLE | systemVariables | IDENTIFIER | SUBST_PARAM | keywordAsVarPrefix | sessionParameter) ("." (IDENTIFIER | SUBST_PARAM | keywordAsVarPrefix | KW_VALUES | KW_ISN | KW_FULL | KW_MODIFIED | KW_READ | KW_WRITE | KW_PRINT | KW_MOVE | KW_FORMAT | KW_RESET | KW_DISPLAY | KW_STORE | KW_UPDATE | KW_CLOSE | KW_OPEN | KW_REJECT | KW_ACCEPT | KW_GET | KW_FIND | KW_SORT | KW_FOR | KW_REPEAT | KW_INPUT | KW_FETCH | KW_CALL | KW_STOP | KW_ESCAPE | KW_EXAMINE | KW_COMPRESS | KW_RELEASE | KW_REDUCE | KW_EXPAND | sessionParameter))* subscript?
  public static boolean variableRef(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableRef")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, VARIABLE_REF, "<variable ref>");
    result_ = variableRef_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, variableRef_1(builder_, level_ + 1));
    result_ = pinned_ && variableRef_2(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // USER_VARIABLE | systemVariables | IDENTIFIER | SUBST_PARAM | keywordAsVarPrefix | sessionParameter
  private static boolean variableRef_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableRef_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, USER_VARIABLE);
    if (!result_) result_ = systemVariables(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, SUBST_PARAM);
    if (!result_) result_ = keywordAsVarPrefix(builder_, level_ + 1);
    if (!result_) result_ = sessionParameter(builder_, level_ + 1);
    return result_;
  }

  // ("." (IDENTIFIER | SUBST_PARAM | keywordAsVarPrefix | KW_VALUES | KW_ISN | KW_FULL | KW_MODIFIED | KW_READ | KW_WRITE | KW_PRINT | KW_MOVE | KW_FORMAT | KW_RESET | KW_DISPLAY | KW_STORE | KW_UPDATE | KW_CLOSE | KW_OPEN | KW_REJECT | KW_ACCEPT | KW_GET | KW_FIND | KW_SORT | KW_FOR | KW_REPEAT | KW_INPUT | KW_FETCH | KW_CALL | KW_STOP | KW_ESCAPE | KW_EXAMINE | KW_COMPRESS | KW_RELEASE | KW_REDUCE | KW_EXPAND | sessionParameter))*
  private static boolean variableRef_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableRef_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!variableRef_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "variableRef_1", pos_)) break;
    }
    return true;
  }

  // "." (IDENTIFIER | SUBST_PARAM | keywordAsVarPrefix | KW_VALUES | KW_ISN | KW_FULL | KW_MODIFIED | KW_READ | KW_WRITE | KW_PRINT | KW_MOVE | KW_FORMAT | KW_RESET | KW_DISPLAY | KW_STORE | KW_UPDATE | KW_CLOSE | KW_OPEN | KW_REJECT | KW_ACCEPT | KW_GET | KW_FIND | KW_SORT | KW_FOR | KW_REPEAT | KW_INPUT | KW_FETCH | KW_CALL | KW_STOP | KW_ESCAPE | KW_EXAMINE | KW_COMPRESS | KW_RELEASE | KW_REDUCE | KW_EXPAND | sessionParameter)
  private static boolean variableRef_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableRef_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, DOT);
    result_ = result_ && variableRef_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IDENTIFIER | SUBST_PARAM | keywordAsVarPrefix | KW_VALUES | KW_ISN | KW_FULL | KW_MODIFIED | KW_READ | KW_WRITE | KW_PRINT | KW_MOVE | KW_FORMAT | KW_RESET | KW_DISPLAY | KW_STORE | KW_UPDATE | KW_CLOSE | KW_OPEN | KW_REJECT | KW_ACCEPT | KW_GET | KW_FIND | KW_SORT | KW_FOR | KW_REPEAT | KW_INPUT | KW_FETCH | KW_CALL | KW_STOP | KW_ESCAPE | KW_EXAMINE | KW_COMPRESS | KW_RELEASE | KW_REDUCE | KW_EXPAND | sessionParameter
  private static boolean variableRef_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableRef_1_0_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, SUBST_PARAM);
    if (!result_) result_ = keywordAsVarPrefix(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, KW_VALUES);
    if (!result_) result_ = consumeToken(builder_, KW_ISN);
    if (!result_) result_ = consumeToken(builder_, KW_FULL);
    if (!result_) result_ = consumeToken(builder_, KW_MODIFIED);
    if (!result_) result_ = consumeToken(builder_, KW_READ);
    if (!result_) result_ = consumeToken(builder_, KW_WRITE);
    if (!result_) result_ = consumeToken(builder_, KW_PRINT);
    if (!result_) result_ = consumeToken(builder_, KW_MOVE);
    if (!result_) result_ = consumeToken(builder_, KW_FORMAT);
    if (!result_) result_ = consumeToken(builder_, KW_RESET);
    if (!result_) result_ = consumeToken(builder_, KW_DISPLAY);
    if (!result_) result_ = consumeToken(builder_, KW_STORE);
    if (!result_) result_ = consumeToken(builder_, KW_UPDATE);
    if (!result_) result_ = consumeToken(builder_, KW_CLOSE);
    if (!result_) result_ = consumeToken(builder_, KW_OPEN);
    if (!result_) result_ = consumeToken(builder_, KW_REJECT);
    if (!result_) result_ = consumeToken(builder_, KW_ACCEPT);
    if (!result_) result_ = consumeToken(builder_, KW_GET);
    if (!result_) result_ = consumeToken(builder_, KW_FIND);
    if (!result_) result_ = consumeToken(builder_, KW_SORT);
    if (!result_) result_ = consumeToken(builder_, KW_FOR);
    if (!result_) result_ = consumeToken(builder_, KW_REPEAT);
    if (!result_) result_ = consumeToken(builder_, KW_INPUT);
    if (!result_) result_ = consumeToken(builder_, KW_FETCH);
    if (!result_) result_ = consumeToken(builder_, KW_CALL);
    if (!result_) result_ = consumeToken(builder_, KW_STOP);
    if (!result_) result_ = consumeToken(builder_, KW_ESCAPE);
    if (!result_) result_ = consumeToken(builder_, KW_EXAMINE);
    if (!result_) result_ = consumeToken(builder_, KW_COMPRESS);
    if (!result_) result_ = consumeToken(builder_, KW_RELEASE);
    if (!result_) result_ = consumeToken(builder_, KW_REDUCE);
    if (!result_) result_ = consumeToken(builder_, KW_EXPAND);
    if (!result_) result_ = sessionParameter(builder_, level_ + 1);
    return result_;
  }

  // subscript?
  private static boolean variableRef_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variableRef_2")) return false;
    subscript(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // KW_VIEW KW_OF? IDENTIFIER
  static boolean viewSpec(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "viewSpec")) return false;
    if (!nextTokenIs(builder_, KW_VIEW)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_VIEW);
    result_ = result_ && viewSpec_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, IDENTIFIER);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_OF?
  private static boolean viewSpec_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "viewSpec_1")) return false;
    consumeToken(builder_, KW_OF);
    return true;
  }

  /* ********************************************************** */
  // KW_WHEN KW_ALL statement*
  public static boolean whenAllClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "whenAllClause")) return false;
    if (!nextTokenIs(builder_, KW_WHEN)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, WHEN_ALL_CLAUSE, null);
    result_ = consumeTokens(builder_, 2, KW_WHEN, KW_ALL);
    pinned_ = result_; // pin = 2
    result_ = result_ && whenAllClause_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // statement*
  private static boolean whenAllClause_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "whenAllClause_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "whenAllClause_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_WHEN KW_ANY statement*
  public static boolean whenAnyClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "whenAnyClause")) return false;
    if (!nextTokenIs(builder_, KW_WHEN)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, WHEN_ANY_CLAUSE, null);
    result_ = consumeTokens(builder_, 2, KW_WHEN, KW_ANY);
    pinned_ = result_; // pin = 2
    result_ = result_ && whenAnyClause_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // statement*
  private static boolean whenAnyClause_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "whenAnyClause_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "whenAnyClause_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_WHEN condition statement*
  public static boolean whenForClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "whenForClause")) return false;
    if (!nextTokenIs(builder_, KW_WHEN)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, WHEN_FOR_CLAUSE, null);
    result_ = consumeToken(builder_, KW_WHEN);
    result_ = result_ && condition(builder_, level_ + 1);
    pinned_ = result_; // pin = 2
    result_ = result_ && whenForClause_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // statement*
  private static boolean whenForClause_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "whenForClause_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "whenForClause_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_WHEN KW_NONE statement*
  public static boolean whenNoneClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "whenNoneClause")) return false;
    if (!nextTokenIs(builder_, KW_WHEN)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, WHEN_NONE_CLAUSE, null);
    result_ = consumeTokens(builder_, 2, KW_WHEN, KW_NONE);
    pinned_ = result_; // pin = 2
    result_ = result_ && whenNoneClause_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // statement*
  private static boolean whenNoneClause_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "whenNoneClause_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "whenNoneClause_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_WHERE condition
  public static boolean whereClause(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "whereClause")) return false;
    if (!nextTokenIs(builder_, KW_WHERE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_WHERE);
    result_ = result_ && condition(builder_, level_ + 1);
    exit_section_(builder_, marker_, WHERE_CLAUSE, result_);
    return result_;
  }

  /* ********************************************************** */
  // writeMulExpr ((PLUS | MINUS) writeMulExpr)*
  static boolean writeAddExpr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeAddExpr")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = writeMulExpr(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && writeAddExpr_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // ((PLUS | MINUS) writeMulExpr)*
  private static boolean writeAddExpr_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeAddExpr_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeAddExpr_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "writeAddExpr_1", pos_)) break;
    }
    return true;
  }

  // (PLUS | MINUS) writeMulExpr
  private static boolean writeAddExpr_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeAddExpr_1_0")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = writeAddExpr_1_0_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && writeMulExpr(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // PLUS | MINUS
  private static boolean writeAddExpr_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeAddExpr_1_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, PLUS);
    if (!result_) result_ = consumeToken(builder_, MINUS);
    return result_;
  }

  /* ********************************************************** */
  // !statement !(IDENTIFIER ASSIGN_OP) !(USER_VARIABLE ASSIGN_OP) (writeParenBlock | SLASH | KW_USING | KW_MAP | KW_NO | KW_WINDOW | KW_AS | KW_FROM | KW_WITH | KW_VALUE | KW_ENCODED | writeAddExpr)
  static boolean writeArg(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeArg")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = writeArg_0(builder_, level_ + 1);
    result_ = result_ && writeArg_1(builder_, level_ + 1);
    result_ = result_ && writeArg_2(builder_, level_ + 1);
    result_ = result_ && writeArg_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !statement
  private static boolean writeArg_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeArg_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !(IDENTIFIER ASSIGN_OP)
  private static boolean writeArg_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeArg_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !writeArg_1_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // IDENTIFIER ASSIGN_OP
  private static boolean writeArg_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeArg_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, IDENTIFIER, ASSIGN_OP);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !(USER_VARIABLE ASSIGN_OP)
  private static boolean writeArg_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeArg_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !writeArg_2_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // USER_VARIABLE ASSIGN_OP
  private static boolean writeArg_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeArg_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, USER_VARIABLE, ASSIGN_OP);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // writeParenBlock | SLASH | KW_USING | KW_MAP | KW_NO | KW_WINDOW | KW_AS | KW_FROM | KW_WITH | KW_VALUE | KW_ENCODED | writeAddExpr
  private static boolean writeArg_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeArg_3")) return false;
    boolean result_;
    result_ = writeParenBlock(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, SLASH);
    if (!result_) result_ = consumeToken(builder_, KW_USING);
    if (!result_) result_ = consumeToken(builder_, KW_MAP);
    if (!result_) result_ = consumeToken(builder_, KW_NO);
    if (!result_) result_ = consumeToken(builder_, KW_WINDOW);
    if (!result_) result_ = consumeToken(builder_, KW_AS);
    if (!result_) result_ = consumeToken(builder_, KW_FROM);
    if (!result_) result_ = consumeToken(builder_, KW_WITH);
    if (!result_) result_ = consumeToken(builder_, KW_VALUE);
    if (!result_) result_ = consumeToken(builder_, KW_ENCODED);
    if (!result_) result_ = writeAddExpr(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // writeParenBlock
  static boolean writeGlobalParams(PsiBuilder builder_, int level_) {
    return writeParenBlock(builder_, level_ + 1);
  }

  /* ********************************************************** */
  // unaryExpr ((STAR | DOUBLE_STAR) unaryExpr | SYSTEM_VARIABLE !DOT !ASSIGN_OP)*
  static boolean writeMulExpr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeMulExpr")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = unaryExpr(builder_, level_ + 1);
    result_ = result_ && writeMulExpr_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ((STAR | DOUBLE_STAR) unaryExpr | SYSTEM_VARIABLE !DOT !ASSIGN_OP)*
  private static boolean writeMulExpr_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeMulExpr_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeMulExpr_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "writeMulExpr_1", pos_)) break;
    }
    return true;
  }

  // (STAR | DOUBLE_STAR) unaryExpr | SYSTEM_VARIABLE !DOT !ASSIGN_OP
  private static boolean writeMulExpr_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeMulExpr_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = writeMulExpr_1_0_0(builder_, level_ + 1);
    if (!result_) result_ = writeMulExpr_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (STAR | DOUBLE_STAR) unaryExpr
  private static boolean writeMulExpr_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeMulExpr_1_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = writeMulExpr_1_0_0_0(builder_, level_ + 1);
    result_ = result_ && unaryExpr(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // STAR | DOUBLE_STAR
  private static boolean writeMulExpr_1_0_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeMulExpr_1_0_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, STAR);
    if (!result_) result_ = consumeToken(builder_, DOUBLE_STAR);
    return result_;
  }

  // SYSTEM_VARIABLE !DOT !ASSIGN_OP
  private static boolean writeMulExpr_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeMulExpr_1_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, SYSTEM_VARIABLE);
    result_ = result_ && writeMulExpr_1_0_1_1(builder_, level_ + 1);
    result_ = result_ && writeMulExpr_1_0_1_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !DOT
  private static boolean writeMulExpr_1_0_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeMulExpr_1_0_1_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, DOT);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !ASSIGN_OP
  private static boolean writeMulExpr_1_0_1_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeMulExpr_1_0_1_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, ASSIGN_OP);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // LPAREN writeParenItem* RPAREN
  static boolean writeParenBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeParenBlock")) return false;
    if (!nextTokenIs(builder_, LPAREN)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = consumeToken(builder_, LPAREN);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, writeParenBlock_1(builder_, level_ + 1));
    result_ = pinned_ && consumeToken(builder_, RPAREN) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // writeParenItem*
  private static boolean writeParenBlock_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeParenBlock_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeParenItem(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "writeParenBlock_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !RPAREN (writeParenParam | NEQ_OP | EQ_OP | LT_OP | GT_OP | GE_OP | LE_OP | expression)
  static boolean writeParenItem(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeParenItem")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = writeParenItem_0(builder_, level_ + 1);
    result_ = result_ && writeParenItem_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !RPAREN
  private static boolean writeParenItem_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeParenItem_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, RPAREN);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // writeParenParam | NEQ_OP | EQ_OP | LT_OP | GT_OP | GE_OP | LE_OP | expression
  private static boolean writeParenItem_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeParenItem_1")) return false;
    boolean result_;
    result_ = writeParenParam(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, NEQ_OP);
    if (!result_) result_ = consumeToken(builder_, EQ_OP);
    if (!result_) result_ = consumeToken(builder_, LT_OP);
    if (!result_) result_ = consumeToken(builder_, GT_OP);
    if (!result_) result_ = consumeToken(builder_, GE_OP);
    if (!result_) result_ = consumeToken(builder_, LE_OP);
    if (!result_) result_ = expression(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // (sessionParameter | IDENTIFIER) EQ_OP editMaskValue
  static boolean writeParenParam(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeParenParam")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = writeParenParam_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, EQ_OP);
    result_ = result_ && editMaskValue(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // sessionParameter | IDENTIFIER
  private static boolean writeParenParam_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeParenParam_0")) return false;
    boolean result_;
    result_ = sessionParameter(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    return result_;
  }

  /* ********************************************************** */
  // ((KW_WRITE !KW_WORK) | KW_DISPLAY | KW_PRINT) writeArg*
  static boolean writeStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeStatement")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = writeStatement_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && writeStatement_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // (KW_WRITE !KW_WORK) | KW_DISPLAY | KW_PRINT
  private static boolean writeStatement_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeStatement_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = writeStatement_0_0(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, KW_DISPLAY);
    if (!result_) result_ = consumeToken(builder_, KW_PRINT);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // KW_WRITE !KW_WORK
  private static boolean writeStatement_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeStatement_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, KW_WRITE);
    result_ = result_ && writeStatement_0_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !KW_WORK
  private static boolean writeStatement_0_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeStatement_0_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, KW_WORK);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // writeArg*
  private static boolean writeStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeStatement_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "writeStatement_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // KW_WRITE KW_WORK KW_FILE? expression KW_VARIABLE? writeArg+
  public static boolean writeWorkFileStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeWorkFileStatement")) return false;
    if (!nextTokenIs(builder_, KW_WRITE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, WRITE_WORK_FILE_STATEMENT, null);
    result_ = consumeTokens(builder_, 1, KW_WRITE, KW_WORK);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, writeWorkFileStatement_2(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, expression(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, writeWorkFileStatement_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && writeWorkFileStatement_5(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // KW_FILE?
  private static boolean writeWorkFileStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeWorkFileStatement_2")) return false;
    consumeToken(builder_, KW_FILE);
    return true;
  }

  // KW_VARIABLE?
  private static boolean writeWorkFileStatement_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeWorkFileStatement_4")) return false;
    consumeToken(builder_, KW_VARIABLE);
    return true;
  }

  // writeArg+
  private static boolean writeWorkFileStatement_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "writeWorkFileStatement_5")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = writeArg(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!writeArg(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "writeWorkFileStatement_5", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

}
