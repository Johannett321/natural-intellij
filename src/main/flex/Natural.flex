package com.appweb.natural.intellij.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;import kotlinx.html.COL;
import static com.appweb.natural.intellij.lexer.NaturalTokenTypes.*;

%%

%class _NaturalLexer
%implements FlexLexer
%unicode
%ignorecase
%function advance
%type IElementType

%eof{
    return;
%eof}

%{
    private int parenDepth = 0;
%}

%state YYINITIAL_NL
%state IN_DEFINE_DATA_NL
%state IN_DEFINE_DATA_BOL
%state IN_DEFINE_DATA_IN_DT_PAREN
%state IN_DEFINE_DATA
%state NORMAL_CODE_NL
%state NORMAL_CODE
%state NORMAL_CODE_AFTER_NUMBER
%state YY_END

InputCharacter = [^\r\n]
LineTerminator = \r|\n|\r\n
WhiteSpace = [ \t\f]+
Digit = [0-9]
HexDigit = [0-9A-Fa-f]
IdentStart = [A-Za-zÆØÅæøå]
IdentChar = [A-Za-z0-9\-_/#ÆØÅæøå]

%%
<YYINITIAL_NL> {
  "*" {InputCharacter}* { return  LINE_COMMENT; }

  // push everything else to the regular YYINITIAL handler
  [^] { yypushback(1); yybegin(YYINITIAL); }
}

<YYINITIAL> {
    {WhiteSpace} { return TokenType.WHITE_SPACE; }
    {LineTerminator} { yybegin(YYINITIAL_NL); return TokenType.WHITE_SPACE; }

    "/*" {InputCharacter}* { return LINE_COMMENT; }
    ^{WhiteSpace}* "*" {InputCharacter}* { return LINE_COMMENT; }

    // DEFINE DATA is only valid at the start of the file (before any normal code)
    "DEFINE" {WhiteSpace}+ "DATA" { yybegin(IN_DEFINE_DATA); return KW_DEFINE_DATA; }
    // No [^] fallback here — single-char tokens (., 1, +, -, =, etc.) are handled
    // by the <YYINITIAL, NORMAL_CODE> shared block. Its [^] catches truly bad chars.
}

<IN_DEFINE_DATA_NL> {
  "*" {InputCharacter}* { return  LINE_COMMENT; }

  // push everything else to the beginning-of-line handler (for LEVEL_NUMBER detection)
  [^] { yypushback(1); yybegin(IN_DEFINE_DATA_BOL); }
}

<IN_DEFINE_DATA_BOL> {
  // Consume leading whitespace before checking for level numbers
  {WhiteSpace} { return TokenType.WHITE_SPACE; }
  // Level numbers 1-9 only at beginning of line (after optional whitespace)
  [1-9] / [ \t] { yybegin(IN_DEFINE_DATA); return LEVEL_NUMBER; }
  // Anything else — hand off to the general IN_DEFINE_DATA state
  [^] { yypushback(1); yybegin(IN_DEFINE_DATA); }
}

<IN_DEFINE_DATA_IN_DT_PAREN> {
    // Whitespace is allowed inside type parentheses, e.g. (N2.2 /1:30)
    {WhiteSpace} { return TokenType.WHITE_SPACE; }

    // Data types
    "A" ([0-9]+)? { return DT_ALPHANUMERIC; }
    "B" ([0-9]+)? { return DT_BINARY; }
    "C" ([0-9]+)? { return DT_CV; }
    "D" ([0-9]+)? { return DT_DATE; }
    "F" ([0-9]+)? { return DT_FLOAT; }
    "I" ([0-9]+)? { return DT_INT; }
    "L" ([0-9]+)? { return DT_LOG; }
    "N" ([0-9]+)? { return DT_NUMBER; }
    "P" ([0-9]+)? { return DT_PACK; }
    "T" ([0-9]+)? { return DT_TIME; }
    "U" ([0-9]+)? { return DT_UNICODE; }
    "HANDLE OF OBJECT" ([0-9]+)? { return DT_OBJECT; }

    ":" { return COLON; }
    "/" { return SLASH; }
    "." { return DOT; }
    "," { return COMMA; }
    "=" { return EQ_OP; }
    "+" { return PLUS; }
    "-" { return MINUS; }
    "*" { return STAR; }

    [0-9]+ { return NUMBER; }

    "(" { parenDepth++; return LPAREN; }
    ")" { if (--parenDepth == 0) yybegin(IN_DEFINE_DATA); return RPAREN; }

    // String literals inside edit masks: (EM=ZZZ'.'ZZZ)
    "'" ("''" | [^'\r\n])* "'" { return STRING_LITERAL; }

    // Any identifier-like content (e.g. edit-mask chars ZZZ, session param names EM AD,
    // or array bound names like MAX-OMFATTER) — returned as IDENTIFIER so the grammar
    // can distinguish data-type specs from edit-mask and array-bound contexts.
    {IdentStart} {IdentChar}* { return IDENTIFIER; }

    // Caret used in edit masks like (EM=999^999^999)
    "^" { return CARET; }

    // Fallback
    [^] { return TokenType.BAD_CHARACTER; }
}

<IN_DEFINE_DATA> {
    // whitespace and newlines
    {WhiteSpace} { return TokenType.WHITE_SPACE; }
    {LineTerminator} { yybegin(IN_DEFINE_DATA_NL); return TokenType.WHITE_SPACE; }

    // comment
    "/*" {InputCharacter}* { return LINE_COMMENT; }

    // Special prefixed literals
    "D'" [^'\r\n]* "'" { return DATE_LITERAL; }
    "T'" [^'\r\n]* "'" { return TIME_LITERAL; }
    "E'" [^'\r\n]* "'" { return TIMESTAMP_LITERAL; }
    "UH'" [^'\r\n]* "'" { return UNICODE_HEX_LITERAL; }
    "H'" [^'\r\n]* "'" { return HEX_LITERAL; }
    "'" ("''" | [^'\r\n])* "'" { return STRING_LITERAL; }
    "\"" [^\"\r\n]* "\"" { return STRING_LITERAL; }

    // Keywords — data defintions
    "LOCAL" { return KW_LOCAL; }
    "GLOBAL" { return KW_GLOBAL; }
    "PARAMETER" { return KW_PARAMETER; }
    // Parameter modifiers recognised inside DEFINE DATA (e.g. "BY VALUE RESULT", "OPTIONAL")
    "RESULT" { return KW_RESULT; }
    "OPTIONAL" { return KW_OPTIONAL; }
    "INDEPENDENT" { return KW_INDEPENDENT; }
    "CONTEXT" { return KW_CONTEXT; }
    "USING" { return KW_USING; }
    "REDEFINE" { return KW_REDEFINE; }
    "CONST" { return KW_CONST; }
    "CONSTANT" { return KW_CONST; }
    "DYNAMIC" { return KW_DYNAMIC; }
    "INIT" { return KW_INIT; }
    "INITIAL" { return KW_INIT; }
    "ALL" { return KW_ALL; }
    "BY" { return KW_BY; }
    "VALUE" { return KW_VALUE; }
    "HANDLE" {WhiteSpace}+ "OF" { return KW_HANDLE_OF; }
    "VIEW" { return KW_VIEW; }
    "OF" { return KW_OF; }

    // Boolean literals used in INIT/CONST values
    "TRUE"  { return KW_TRUE; }
    "FALSE" { return KW_FALSE; }

    // Keywords used in INIT modifiers
    "FULL"   { return KW_FULL; }
    "LENGTH" { return KW_LENGTH; }

    // Keywords — block termination (end-***)
    "END-DEFINE" { yybegin(NORMAL_CODE); return KW_END_DEFINE; }

    "(" { parenDepth = 1; yybegin(IN_DEFINE_DATA_IN_DT_PAREN); return LPAREN; }

    // variable names — C* prefix is a Natural count-field for periodic groups (e.g. C*SAKSROLLE)
    // #VAR prefix is a user-defined variable (e.g. #REQUEST, #-KTYPE)
    // +VAR prefix is used in some programs (e.g. +LOG-SAK-NR, +DB)
    "C*" {IdentChar}+ { return IDENTIFIER; }
    "C@" {IdentChar}+ { return IDENTIFIER; }
    "#" {IdentChar}+ { return IDENTIFIER; }
    "+" {IdentChar}+ { return IDENTIFIER; }
    {IdentStart} {IdentChar}* { return IDENTIFIER; }
    {IdentStart} { return IDENTIFIER; }

    // System variables in DEFINE DATA INIT/CONST context (e.g. INIT <*USER>, INIT <*DATX>)
    "*" {IdentChar}+ { return SYSTEM_VARIABLE; }

    // other characters
    "<>" { return NEQ_OP; }
    "<" { return LT_OP; }
    ">" { return GT_OP; }
    ":" { return COLON; }
    "/" { return SLASH; }
    "." { return DOT; }
    "," { return COMMA; }
    "-" { return MINUS; }
    "+" { return PLUS; }

    // Numbers
    [0-9]+ ("." [0-9]+)? { return NUMBER; }

    // Fallback
    [^] { return TokenType.BAD_CHARACTER; }
}

<NORMAL_CODE_NL> {
  "*" {InputCharacter}* { return  LINE_COMMENT; }

  // push everything else to the regular YYINITIAL handler
  [^] { yypushback(1); yybegin(NORMAL_CODE); }
}

// Transitional state entered right after a NUMBER token. Consumes whitespace/comments,
// then: if the next non-whitespace is "+" or "-", emit PLUS/MINUS (as operator) instead
// of the greedy "+IDENT" / "-IDENT" rule from NORMAL_CODE. Otherwise transitions back.
<NORMAL_CODE_AFTER_NUMBER> {
    {WhiteSpace} { return TokenType.WHITE_SPACE; }
    {LineTerminator} { yybegin(NORMAL_CODE_NL); return TokenType.WHITE_SPACE; }
    "/*" {InputCharacter}* { return LINE_COMMENT; }
    "+" { yybegin(NORMAL_CODE); return PLUS; }
    [^] { yypushback(1); yybegin(NORMAL_CODE); }
}

<NORMAL_CODE> {
    // whitespace and newlines — state-specific (NORMAL_CODE uses NORMAL_CODE_NL)
    {WhiteSpace} { return TokenType.WHITE_SPACE; }
    {LineTerminator} { yybegin(NORMAL_CODE_NL); return TokenType.WHITE_SPACE; }

    "/*" {InputCharacter}* { return LINE_COMMENT; }

    // After a NUMBER, RPAREN, or plain IDENTIFIER, transition to NORMAL_CODE_AFTER_NUMBER
    // so that a following "+IDENT" is parsed as operator+identifier (arithmetic), not
    // greedily consumed as a +VAR identifier. Applies to "0 +VAR", "K+I", "FIELD(1)+FIELD(2)".
    // Specific rules take precedence over the shared ones below when in NORMAL_CODE.
    {Digit}+ ("." {Digit}+)? { yybegin(NORMAL_CODE_AFTER_NUMBER); return NUMBER; }
    ")" { yybegin(NORMAL_CODE_AFTER_NUMBER); return RPAREN; }
}


// All keyword, literal, identifier, and operator rules are shared between the
// initial state (YYINITIAL, before DEFINE DATA) and NORMAL_CODE (after END-DEFINE).
// This allows map files (FORMAT-first, no DEFINE DATA) to be lexed correctly in
// YYINITIAL without misidentifying tokens as BAD_CHARACTER.
<YYINITIAL, NORMAL_CODE> {
    // Special prefixed literals
    "D'" [^'\r\n]* "'" { return DATE_LITERAL; }
    "T'" [^'\r\n]* "'" { return TIME_LITERAL; }
    "E'" [^'\r\n]* "'" { return TIMESTAMP_LITERAL; }
    "UH'" [^'\r\n]* "'" { return UNICODE_HEX_LITERAL; }
    "H'" [^'\r\n]* "'" { return HEX_LITERAL; }
    "'" ("''" | [^'\r\n])* "'" { return STRING_LITERAL; }
    "\"" [^\"\r\n]* "\"" { return STRING_LITERAL; }

    // Numbers
    {Digit}+ ("." {Digit}+)? { return NUMBER; }

    // Keywords – DEFINE variants (compound tokens take priority via longest match)
    "DEFINE" {WhiteSpace}+ "FUNCTION" { return KW_DEFINE_FUNCTION; }
    "DEFINE" {WhiteSpace}+ "PRINTER" { return KW_DEFINE_PRINTER; }
    "DEFINE" {WhiteSpace}+ "WINDOW" { return KW_DEFINE_WINDOW; }
    "DEFINE" {WhiteSpace}+ "WORK FILE" { return KW_DEFINE_WORK_FILE; }
    "DEFINE" {WhiteSpace}+ "PROTOTYPE" { return KW_DEFINE_PROTOTYPE; }
    "DEFINE" {WhiteSpace}+ "CLASS" { return KW_DEFINE_CLASS; }
    // DEFINE alone — used for DEFINE [SUBROUTINE] name (SUBROUTINE is optional)
    "DEFINE" { return KW_DEFINE; }

    // Keywords – Block terminators (END-***)
    "END-IF" { return KW_END_IF; }
    "END-FOR" { return KW_END_FOR; }
    "END-REPEAT" { return KW_END_REPEAT; }
    "END-DECIDE" { return KW_END_DECIDE; }
    "END-DEFINE" { return KW_END_DEFINE; }
    "END-SUBROUTINE" { return KW_END_SUBROUTINE; }
    "END-FUNCTION" { return KW_END_FUNCTION; }
    "END-READ" { return KW_END_READ; }
    "END-FIND" { return KW_END_FIND; }
    "END-SORT" { return KW_END_SORT; }
    "END-BREAK" { return KW_END_BREAK; }
    "END-ENDDATA" { return KW_END_ENDDATA; }
    "END-ENDFILE" { return KW_END_ENDFILE; }
    "END-ENDPAGE" { return KW_END_ENDPAGE; }
    "END-ALL" { return KW_END_ALL; }
    "END-BEFORE" { return KW_END_BEFORE; }
    "END-START" { return KW_END_START; }
    "END-TOPPAGE" { return KW_END_TOPPAGE; }
    "END-ERROR" { return KW_END_ERROR; }
    "END-NOREC" { return KW_END_NOREC; }
    "END-WORK"  { return KW_END_WORK; }

    // Keywords – control flow
    "SUBROUTINE" { return KW_SUBROUTINE; }
    "IF" { return KW_IF; }
    "THEN" { return KW_THEN; }
    "ELSE" { return KW_ELSE; }
    "FOR" { return KW_FOR; }
    "REPEAT" { return KW_REPEAT; }
    "WHILE" { return KW_WHILE; }
    "UNTIL" { return KW_UNTIL; }
    "DECIDE" { return KW_DECIDE; }
    "WHEN" { return KW_WHEN; }
    "ESCAPE" { return KW_ESCAPE; }
    "PERFORM" { return KW_PERFORM; }
    "LOOP" { return KW_LOOP; }
    "FETCH" { return KW_FETCH; }
    "STOP" { return KW_STOP; }
    "TERMINATE" { return KW_TERMINATE; }
    "SETTIME" { return KW_SETTIME; }
    "ONCE" { return KW_ONCE; }
    "HELP" { return KW_HELP; }

    // Keywords – data access
    "DOWNLOAD" { return KW_DOWNLOAD; }
    "READ" { return KW_READ; }
    "FIND" { return KW_FIND; }
    "STORE" { return KW_STORE; }
    "UPDATE" { return KW_UPDATE; }
    "DELETE" { return KW_DELETE; }
    "GET" { return KW_GET; }
    "SORT" { return KW_SORT; }
    "THEM" { return KW_THEM; }
    "HISTOGRAM" { return KW_HISTOGRAM; }

    // Keywords – I/O
    "WRITE" { return KW_WRITE; }
    "DISPLAY" { return KW_DISPLAY; }
    "PRINT" { return KW_PRINT; }
    "INPUT" { return KW_INPUT; }
    "REINPUT" { return KW_REINPUT; }
    "SKIP" { return KW_SKIP; }
    "NEWPAGE" { return KW_NEWPAGE; }
    "EJECT" { return KW_EJECT; }
    "FORMAT" { return KW_FORMAT; }

    // Keywords – data manipulation
    "MOVE" { return KW_MOVE; }
    "ASSIGN" { return KW_ASSIGN; }
    "COMPUTE" { return KW_COMPUTE; }
    "ADD" { return KW_ADD; }
    "SUBTRACT" { return KW_SUBTRACT; }
    "MULTIPLY" { return KW_MULTIPLY; }
    "DIVIDE" { return KW_DIVIDE; }
    "COMPRESS" { return KW_COMPRESS; }
    "EXAMINE" { return KW_EXAMINE; }
    "DIRECTION" { return KW_DIRECTION; }
    "BACKWARD" { return KW_BACKWARD; }
    "FORWARD" { return KW_FORWARD; }
    "PATTERN" { return KW_PATTERN; }
    "SEPARATE" { return KW_SEPARATE; }
    "TRANSLATE" { return KW_TRANSLATE; }
    "RESET" { return KW_RESET; }
    "REDUCE" { return KW_REDUCE; }
    "EXPAND" { return KW_EXPAND; }
    "ARRAY"  { return KW_ARRAY; }
    "RESIZE" { return KW_RESIZE; }
    "STACK" { return KW_STACK; }

    // Keywords – calls
    "CALLNAT" { return KW_CALLNAT; }
    "CALL"    { return KW_CALL; }
    "RUN"     { return KW_RUN; }
    "INCLUDE" { return KW_INCLUDE; }
    "USING"   { return KW_USING; }

    // Keywords – clauses
    "TO" { return KW_TO; }
    "FROM" { return KW_FROM; }
    "THRU" { return KW_THRU; }
    "BY" { return KW_BY; }
    "VALUES" { return KW_VALUES; }
    "VALUE" { return KW_VALUE; }
    "OF" { return KW_OF; }
    "IN" { return KW_IN; }
    "AT" { return KW_AT; }
    "BREAK" { return KW_BREAK; }
    "BEFORE" { return KW_BEFORE; }
    "PROCESSING" { return KW_PROCESSING; }
    "PROCESS"    { return KW_PROCESS; }
    "COMMAND"    { return KW_COMMAND; }
    "REQUEST"    { return KW_REQUEST; }
    "DOCUMENT"   { return KW_DOCUMENT; }
    "STARTING" { return KW_STARTING; }
    "ENDING" { return KW_ENDING; }
    "WHERE" { return KW_WHERE; }
    "LIMIT" { return KW_LIMIT; }
    "WITH" { return KW_WITH; }
    "INTO" { return KW_INTO; }
    "GIVING" { return KW_GIVING; }
    "ALL" { return KW_ALL; }
    "EDITED" { return KW_EDITED; }
    "FIRST" { return KW_FIRST; }
    "NONE" { return KW_NONE; }
    "ON" { return KW_ON; }
    "DATA" { return KW_DATA; }
    "STEP" { return KW_STEP; }
    "BOTTOM" { return KW_BOTTOM; }
    "TOP" { return KW_TOP; }
    "ROUTINE" { return KW_ROUTINE; }
    "MODULE" { return KW_MODULE; }
    "IMMEDIATE" { return KW_IMMEDIATE; }
    "PAGE" { return KW_PAGE; }
    "START" { return KW_START; }
    "ROUNDED" { return KW_ROUNDED; }
    "NAME" { return KW_NAME; }
    "MASK" { return KW_MASK; }
    "SCAN" { return KW_SCAN; }
    "RESULT" { return KW_RESULT; }
    "OPTIONAL" { return KW_OPTIONAL; }
    "SUBSTRING" { return KW_SUBSTRING; }
    "SUBSTR" { return KW_SUBSTRING; }
    "LINES" { return KW_LINES; }
    "SAME" { return KW_SAME; }
    "DOEND" { return KW_DOEND; }
    "DO" { return KW_DO; }
    "KEY" { return KW_KEY; }
    "*TRANSLATE" { return KW_SV_TRANSLATE; }
    "SPECIFIED" { return KW_SPECIFIED; }
    "SELECT" { return KW_SELECT; }
    "END-HISTOGRAM" { return KW_END_HISTOGRAM; }
    "RIGHT" { return KW_RIGHT; }
    "LEFT" { return KW_LEFT; }
    "JUSTIFIED" { return KW_JUSTIFIED; }
    "NORMALIZED" { return KW_NORMALIZED; }
    "ENCODED" { return KW_ENCODED; }
    "POSITION" { return KW_POSITION; }
    "IGNORE" { return KW_IGNORE; }
    "REMAINDER" { return KW_REMAINDER; }
    "OPTIONS" { return KW_OPTIONS; }
    "PHYSICAL" { return KW_PHYSICAL; }
    "SEQUENCE" { return KW_SEQUENCE; }
    "LOGICAL" { return KW_LOGICAL; }
    "ASCENDING" { return KW_ASCENDING; }
    "ASC" { return KW_ASCENDING; }
    "DESCENDING" { return KW_DESCENDING; }
    "DESC" { return KW_DESCENDING; }
    "REPOSITION"  { return KW_REPOSITION; }
    "SHARED"      { return KW_SHARED; }
    "HOLD"        { return KW_HOLD; }
    "MODE"        { return KW_MODE; }
    "SPACES"    { return KW_SPACES; }
    "LEAVING"   { return KW_LEAVING; }
    "LEAVE"     { return KW_LEAVING; }
    "NO"        { return KW_NO; }
    "SPACE"     { return KW_SPACE; }
    "REPLACE"   { return KW_REPLACE; }
    "DELIMITERS" { return KW_DELIMITERS; }
    "DELIMITER"  { return KW_DELIMITER; }
    "WORK"      { return KW_WORK; }
    "FILE"      { return KW_FILE; }
    "CLOSE"     { return KW_CLOSE; }

    // Keywords – transaction/error
    "ON" {WhiteSpace}+ "ERROR"  { return KW_ON_ERROR; }
    "BACKOUT"     { return KW_BACKOUT; }
    "COMMIT"      { return KW_COMMIT; }
    "REJECT"      { return KW_REJECT; }
    "ACCEPT"      { return KW_ACCEPT; }
    "RETRY"       { return KW_RETRY; }
    "RELEASE"     { return KW_RELEASE; }
    "TRANSACTION" { return KW_TRANSACTION; }
    "ISN"         { return KW_ISN; }
    "VARIABLE"    { return KW_VARIABLE; }
    "RECORDS"     { return KW_RECORDS; }
    "PASSWORD"    { return KW_PASSWORD; }
    "CIPHER"      { return KW_CIPHER; }
    "MULTI-FETCH" { return KW_MULTI_FETCH; }
    "OFF"         { return KW_OFF; }
    "NUMBER"      { return KW_NUMBER; }
    "UNIQUE"      { return KW_UNIQUE; }
    "COUPLED"     { return KW_COUPLED; }
    "VIA"         { return KW_VIA; }
    "SORTED"      { return KW_SORTED; }
    "RETAIN"      { return KW_RETAIN; }
    "AS"          { return KW_AS; }
    "FOUND"       { return KW_FOUND; }
    "BUT"         { return KW_BUT; }
    "ENTER"       { return KW_ENTER; }
    "EVERY"       { return KW_EVERY; }
    "CONDITION"   { return KW_CONDITION; }
    "ANY"         { return KW_ANY; }
    "ERASE"       { return KW_ERASE; }
    "MAP"         { return KW_MAP; }
    "ALARM"       { return KW_ALARM; }
    "MARK"        { return KW_MARK; }
    "MODIFIED"    { return KW_MODIFIED; }
    "TEXT"        { return KW_TEXT; }
    "WINDOW"      { return KW_WINDOW; }
    "SOUND"       { return KW_SOUND; }
    "RECORD"      { return KW_RECORD; }
    "STATEMENT"   { return KW_STATEMENT; }
    "SET"         { return KW_SET; }
    "SAME"        { return KW_SAME; }
    "UPPER"       { return KW_UPPER; }
    "LOWER"       { return KW_LOWER; }
    "CASE"        { return KW_CASE; }
    "INDEX"       { return KW_INDEX; }
    "FULL"        { return KW_FULL; }
    "LENGTH"      { return KW_LENGTH; }
    "FRAMED"      { return KW_FRAMED; }
    "INCDIC"      { return KW_INCDIC; }
    "CONTROL"     { return KW_CONTROL; }
    "GIVE"        { return KW_GIVE; }
    "KEYS"        { return KW_KEYS; }
    "MAX"         { return KW_MAX; }
    "MIN"         { return KW_MIN; }
    "NMIN"        { return KW_NMIN; }
    "COUNT"       { return KW_COUNT; }
    "NCOUNT"      { return KW_NCOUNT; }
    "OLD"         { return KW_OLD; }
    "AVER"        { return KW_AVER; }
    "NAVER"       { return KW_NAVER; }
    "SUM"         { return KW_SUM; }
    "TOTAL"       { return KW_TOTAL; }

    // System variables — application-related
    "*APPLIC-ID"      { return SV_APPLIC_ID; }
    "*APPLIC-NAME"    { return SV_APPLIC_NAME; }
    "*COM"            { return SV_COM; }
    "*CONVID"         { return SV_CONVID; }
    "*COUNTER"        { return SV_COUNTER; }
    "*CPU-TIME"       { return SV_CPU_TIME; }
    "*CURRENT-UNIT"   { return SV_CURRENT_UNIT; }
    "*DATA"           { return SV_DATA; }
    "*ERROR-LINE"     { return SV_ERROR_LINE; }
    "*ERROR-NR"       { return SV_ERROR_NR; }
    "*ERROR-TA"       { return SV_ERROR_TA; }
    "*ETID"           { return SV_ETID; }
    "*ISN"            { return SV_ISN; }
    "*LBOUND"         { return SV_LBOUND; }
    "*LENGTH"         { return SV_LENGTH; }
    "*LEVEL"          { return SV_LEVEL; }
    "*LIBRARY-ID"     { return SV_LIBRARY_ID; }
    "*LINEX"          { return SV_LINEX; }
    "*LINE-COUNT"     { return SV_LINE_COUNT; }
    "*LINE"           { return SV_LINE; }
    "*LOAD-LIBRARY-ID" { return SV_LOAD_LIBRARY_ID; }
    "*NUMBER"         { return SV_NUMBER; }
    "*OCCURRENCE"     { return SV_OCCURRENCE; }
    "*OCC"            { return SV_OCCURRENCE; }
    "*PAGE-EVENT"     { return SV_PAGE_EVENT; }
    "*PAGE-LEVEL"     { return SV_PAGE_LEVEL; }
    "*PAGE-NUMBER"    { return SV_PAGE_NUMBER; }
    "*PROGRAM"        { return SV_PROGRAM; }
    "*REINPUT-TYPE"   { return SV_REINPUT_TYPE; }
    "*ROWCOUNT"       { return SV_ROWCOUNT; }
    "*STARTUP"        { return SV_STARTUP; }
    "*STEPLIB"        { return SV_STEPLIB; }
    "*SUBROUTINE"     { return SV_SUBROUTINE; }
    "*THIS-OBJECT"    { return SV_THIS_OBJECT; }
    "*TYPE"           { return SV_TYPE; }
    "*UBOUND"         { return SV_UBOUND; }

    // System variables — date/time
    "*DAT4D"          { return SV_DAT4D; }
    "*DAT4E"          { return SV_DAT4E; }
    "*DAT4I"          { return SV_DAT4I; }
    "*DAT4J"          { return SV_DAT4J; }
    "*DAT4U"          { return SV_DAT4U; }
    "*DATD"           { return SV_DATD; }
    "*DATE"           { return SV_DATE; }
    "*DATG"           { return SV_DATG; }
    "*DATI"           { return SV_DATI; }
    "*DATJ"           { return SV_DATJ; }
    "*DATN"           { return SV_DATN; }
    "*DATU"           { return SV_DATU; }
    "*DATV"           { return SV_DATV; }
    "*DATVS"          { return SV_DATVS; }
    "*DATX"           { return SV_DATX; }
    "*TIMESTMP"       { return SV_TIMESTMP; }
    "*TIMD"           { return SV_TIMD; }
    "*TIME-OUT"       { return SV_TIME_OUT; }
    "*TIMN"           { return SV_TIMN; }
    "*TIMX"           { return SV_TIMX; }
    "*TIME"           { return SV_TIME; }

    // System variables — input/output
    "*CURS-COL"       { return SV_CURS_COL; }
    "*CURS-FIELD"     { return SV_CURS_FIELD; }
    "*CURS-LINE"      { return SV_CURS_LINE; }
    "*CURSOR"         { return SV_CURSOR; }
    "*LINESIZE"       { return SV_LINESIZE; }
    "*LOG-LS"         { return SV_LOG_LS; }
    "*LOG-PS"         { return SV_LOG_PS; }
    "*PAGESIZE"       { return SV_PAGESIZE; }
    "*PF-KEY"         { return SV_PF_KEY; }
    "*PF-NAME"        { return SV_PF_NAME; }
    "*WINDOW-LS"      { return SV_WINDOW_LS; }
    "*WINDOW-POS"     { return SV_WINDOW_POS; }
    "*WINDOW-PS"      { return SV_WINDOW_PS; }

    // System variables — Natural environment
    "*BROWSER-IO"     { return SV_BROWSER_IO; }
    "*DEVICE"         { return SV_DEVICE; }
    "*GROUP"          { return SV_GROUP; }
    "*HARDCOPY"       { return SV_HARDCOPY; }
    "*INIT-USER"      { return SV_INIT_USER; }
    "*LANGUAGE"       { return SV_LANGUAGE; }
    "*NATVERS"        { return SV_NATVERS; }
    "*NET-USER"       { return SV_NET_USER; }
    "*PARM-USER"      { return SV_PARM_USER; }
    "*PATCH-LEVEL"    { return SV_PATCH_LEVEL; }
    "*PID"            { return SV_PID; }
    "*SCREEN-IO"      { return SV_SCREEN_IO; }
    "*SERVER-TYPE"    { return SV_SERVER_TYPE; }
    "*UI"             { return SV_UI; }
    "*USER-NAME"      { return SV_USER_NAME; }
    "*USER"           { return SV_USER; }

    // System variables — system environment
    "*CODEPAGE"       { return SV_CODEPAGE; }
    "*HARDWARE"       { return SV_HARDWARE; }
    "*HOSTNAME"       { return SV_HOSTNAME; }
    "*INIT-ID"        { return SV_INIT_ID; }
    "*INIT-PROGRAM"   { return SV_INIT_PROGRAM; }
    "*LOCALE"         { return SV_LOCALE; }
    "*MACHINE-CLASS"  { return SV_MACHINE_CLASS; }
    "*OPSYS"          { return SV_OPSYS; }
    "*OSVERS"         { return SV_OSVERS; }
    "*OS"             { return SV_OS; }
    "*TPSYS"          { return SV_TPSYS; }
    "*TPVERS"         { return SV_TPVERS; }
    "*TP"             { return SV_TP; }
    "*WINMGRVERS"     { return SV_WINMGRVERS; }
    "*WINMGR"         { return SV_WINMGR; }

    // System variables — XML
    "*PARSE-COL"           { return SV_PARSE_COL; }
    "*PARSE-LEVEL"         { return SV_PARSE_LEVEL; }
    "*PARSE-NAMESPACE-URI" { return SV_PARSE_NAMESPACE_URI; }
    "*PARSE-ROW"           { return SV_PARSE_ROW; }
    "*PARSE-TYPE"          { return SV_PARSE_TYPE; }

    // Catch-all for unknown/custom system variables (e.g. *FEHL.NUMMER, group references, *1201 messages)
    // Placed after specific SV_* rules: longest match wins for known vars, this catches the rest.
    "*" {IdentChar}+ { return SYSTEM_VARIABLE; }

    // Operators & punctuation
    "AND" { return KW_AND; }
    "OR" { return KW_OR; }
    "NOT" { return KW_NOT; }
    "EQUALS" { return KW_EQUALS; }
    "EQUAL" { return KW_EQUAL; }
    "LESS" { return KW_LESS; }
    "GREATER" { return KW_GREATER; }
    "THAN" { return KW_THAN; }

    ":=" { return ASSIGN_OP; }
    "LE" { return LE_OP; }
    "<=" { return LE_OP; }
    "GE" { return GE_OP; }
    ">=" { return GE_OP; }
    "LT" { return LT_OP; }
    "<>" { return NEQ_OP; }
    "<" { return LT_OP; }
    "GT" { return GT_OP; }
    ">" { return GT_OP; }
    "EQ" { return EQ_OP; }
    "=" { return EQ_OP; }
    "NE" { return NEQ_OP; }
    "^=" { return NEQ_OP; }
    "^"  { return CARET; }
    "%"  { return PERCENT; }

    // Substitution parameters in copy codes: &1&, &2&, &MYVAR&
    "&" [^&\r\n]* "&" { return SUBST_PARAM; }

    "**" { return DOUBLE_STAR; }
    "+"  { return PLUS; }
    "-"  { return MINUS; }
    "*"  { return STAR; }
    "/"  { return SLASH; }

    // Punctuation
    ":"  { return COLON; }
    "."  { return DOT; }
    ","  { return COMMA; }
    "("  { return LPAREN; }
    ")"  { return RPAREN; }
    "["  { return LBRACKET; }
    "]"  { return RBRACKET; }
    ";"  { return SEMICOLON; }

    // Session parameters
    "AD"  { return SP_AD; }
    "AL"  { return SP_AL; }
    "CD"  { return SP_CD; }
    "DF"  { return SP_DF; }
    "DL"  { return SP_DL; }
    "EM"  { return SP_EM; }
    "ES"  { return SP_ES; }
    "FC"  { return SP_FC; }
    "FL"  { return SP_FL; }
    "GC"  { return SP_GC; }
    "HC"  { return SP_HC; }
    "HW"  { return SP_HW; }
    "IC"  { return SP_IC; }
    "ICU" { return SP_ICU; }
    "IP"  { return SP_IP; }
    "IS"  { return SP_IS; }
    "KD"  { return SP_KD; }
    "LC"  { return SP_LC; }
    "LCU" { return SP_LCU; }
    "LS"  { return SP_LS; }
    "MC"  { return SP_MC; }
    "MP"  { return SP_MP; }
    "MS"  { return SP_MS; }
    "NL"  { return SP_NL; }
    "PC"  { return SP_PC; }
    "PM"  { return SP_PM; }
    "PS"  { return SP_PS; }
    "SF"  { return SP_SF; }
    "SG"  { return SP_SG; }
    "TC"  { return SP_TC; }
    "TCU" { return SP_TCU; }
    "DY"  { return SP_DY; }
    "UC"  { return SP_UC; }
    "ZP"  { return SP_ZP; }

    // END TRANSACTION / END OF TRANSACTION (must come before bare "END")
    "END" {WhiteSpace}+ ("OF" {WhiteSpace}+)? "TRANSACTION" { return KW_END_TRANSACTION; }

    // END
    "END" { return KW_END; }

    // Boolean literals
    "TRUE"  { return KW_TRUE; }
    "FALSE" { return KW_FALSE; }

    // Identifiers — C* prefix is a Natural count-field for periodic groups (e.g. C*SAKSROLLE)
    // C@ prefix is used for some special variables (e.g. C@ADRESSAT)
    // +VAR prefix is used for some variables (e.g. +LOG-SAK-NR).
    // Must start with a letter to avoid +9 (numeric literal with sign) being eaten here.
    // After an IDENTIFIER in NORMAL_CODE, transition to NORMAL_CODE_AFTER_NUMBER so that
    // a following "+IDENT" (e.g. "K+I") is lexed as PLUS IDENTIFIER, not one +IDENT token.
    "C*" {IdentChar}+ { if (yystate() == NORMAL_CODE) yybegin(NORMAL_CODE_AFTER_NUMBER); return IDENTIFIER; }
    "C@" {IdentChar}+ { if (yystate() == NORMAL_CODE) yybegin(NORMAL_CODE_AFTER_NUMBER); return IDENTIFIER; }
    "+" {IdentStart} {IdentChar}* { if (yystate() == NORMAL_CODE) yybegin(NORMAL_CODE_AFTER_NUMBER); return IDENTIFIER; }
    {IdentStart} {IdentChar}* { if (yystate() == NORMAL_CODE) yybegin(NORMAL_CODE_AFTER_NUMBER); return IDENTIFIER; }
    "#" {IdentChar}+ { if (yystate() == NORMAL_CODE) yybegin(NORMAL_CODE_AFTER_NUMBER); return IDENTIFIER; }
    // Underscore-prefixed variables (e.g. _U in DY=_U^)
    "_" {IdentChar}* { return IDENTIFIER; }
    // Backtick-delimited attribute values (e.g. `YE_U^ in DY=`YE_U^)
    // {IdentChar}* stops before ^ and ) since ^ and ) are not IdentChars.
    "`" {IdentChar}* "^"? { return IDENTIFIER; }

    // Fallback
    [^] { return TokenType.BAD_CHARACTER; }
}

<YY_END> {
  // Fallback
  [^] { return TokenType.BAD_CHARACTER; }
}