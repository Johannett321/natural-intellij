package com.appweb.natural.intellij.lexer

import com.appweb.natural.intellij.psi.NaturalTypes
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

object NaturalTokenTypes {

    // —————————————————————————————————————————————————————————————————————————————————
    // —— Keywords —————————————————————————————————————————————————————————————————————
    // —————————————————————————————————————————————————————————————————————————————————

    // —— Comments —————————————————————————————————————————————————————————————————————
    @JvmField val LINE_COMMENT: IElementType = NaturalTypes.LINE_COMMENT
    @JvmField val COMMENTS = TokenSet.create(LINE_COMMENT)

    // —— Literals —————————————————————————————————————————————————————————————————————
    @JvmField val STRING_LITERAL: IElementType = NaturalTypes.STRING_LITERAL
    @JvmField val NUMBER: IElementType = NaturalTypes.NUMBER
    @JvmField val DATE_LITERAL: IElementType = NaturalTypes.DATE_LITERAL
    @JvmField val TIME_LITERAL: IElementType = NaturalTypes.TIME_LITERAL
    @JvmField val TIMESTAMP_LITERAL: IElementType = NaturalTypes.TIMESTAMP_LITERAL
    @JvmField val HEX_LITERAL: IElementType = NaturalTypes.HEX_LITERAL
    @JvmField val UNICODE_HEX_LITERAL: IElementType = NaturalTypes.UNICODE_HEX_LITERAL

    @JvmField val STRING_LITERALS: TokenSet = TokenSet.create(
        STRING_LITERAL, DATE_LITERAL, TIME_LITERAL,
        TIMESTAMP_LITERAL, HEX_LITERAL, UNICODE_HEX_LITERAL
    )

    // —— Identifiers ——————————————————————————————————————————————————————————————————
    @JvmField val USER_VARIABLE: IElementType = NaturalTypes.USER_VARIABLE
    @JvmField val SYSTEM_VARIABLE: IElementType = NaturalTypes.SYSTEM_VARIABLE
    @JvmField val IDENTIFIER: IElementType = NaturalTypes.IDENTIFIER


    // —— Structural tokens ————————————————————————————————————————————————————————————
    @JvmField val LEVEL_NUMBER: IElementType = NaturalTypes.LEVEL_NUMBER

    // —— Keywords — block terminators (END-xxx) ———————————————————————————————————————
    @JvmField val KW_END_IF: IElementType = NaturalTypes.KW_END_IF
    @JvmField val KW_END_FOR: IElementType = NaturalTypes.KW_END_FOR
    @JvmField val KW_END_REPEAT: IElementType = NaturalTypes.KW_END_REPEAT
    @JvmField val KW_END_DECIDE: IElementType = NaturalTypes.KW_END_DECIDE
    @JvmField val KW_END_DEFINE: IElementType = NaturalTypes.KW_END_DEFINE
    @JvmField val KW_END_SUBROUTINE: IElementType = NaturalTypes.KW_END_SUBROUTINE
    @JvmField val KW_END_FUNCTION: IElementType = NaturalTypes.KW_END_FUNCTION
    @JvmField val KW_END_READ: IElementType = NaturalTypes.KW_END_READ
    @JvmField val KW_END_FIND: IElementType = NaturalTypes.KW_END_FIND
    @JvmField val KW_END_SORT: IElementType = NaturalTypes.KW_END_SORT
    @JvmField val KW_END_BREAK: IElementType = NaturalTypes.KW_END_BREAK
    @JvmField val KW_END_ENDDATA: IElementType = NaturalTypes.KW_END_ENDDATA
    @JvmField val KW_END_ENDFILE: IElementType = NaturalTypes.KW_END_ENDFILE
    @JvmField val KW_END_ENDPAGE: IElementType = NaturalTypes.KW_END_ENDPAGE
    @JvmField val KW_END_ALL: IElementType = NaturalTypes.KW_END_ALL
    @JvmField val KW_END_BEFORE: IElementType = NaturalTypes.KW_END_BEFORE
    @JvmField val KW_END_START: IElementType = NaturalTypes.KW_END_START
    @JvmField val KW_END_TOPPAGE: IElementType = NaturalTypes.KW_END_TOPPAGE
    @JvmField val KW_END_ERROR: IElementType = NaturalTypes.KW_END_ERROR
    @JvmField val KW_END_TRANSACTION: IElementType = NaturalTypes.KW_END_TRANSACTION
    @JvmField val KW_END_NOREC: IElementType = NaturalTypes.KW_END_NOREC
    @JvmField val KW_END_WORK: IElementType = NaturalTypes.KW_END_WORK
    @JvmField val KW_END: IElementType = NaturalTypes.KW_END

    // —— DEFINE openers ———————————————————————————————————————————————————————————————
    @JvmField val KW_DEFINE_DATA: IElementType = NaturalTypes.KW_DEFINE_DATA
    @JvmField val KW_DEFINE: IElementType = NaturalTypes.KW_DEFINE
    @JvmField val KW_SUBROUTINE: IElementType = NaturalTypes.KW_SUBROUTINE
    @JvmField val KW_DEFINE_FUNCTION: IElementType = NaturalTypes.KW_DEFINE_FUNCTION
    @JvmField val KW_DEFINE_PRINTER: IElementType = NaturalTypes.KW_DEFINE_PRINTER
    @JvmField val KW_DEFINE_WINDOW: IElementType = NaturalTypes.KW_DEFINE_WINDOW
    @JvmField val KW_DEFINE_WORK_FILE: IElementType = NaturalTypes.KW_DEFINE_WORK_FILE
    @JvmField val KW_DEFINE_PROTOTYPE: IElementType = NaturalTypes.KW_DEFINE_PROTOTYPE
    @JvmField val KW_DEFINE_CLASS: IElementType = NaturalTypes.KW_DEFINE_CLASS

    // —— Keywords — control flow ——————————————————————————————————————————————————————
    @JvmField val KW_IF: IElementType = NaturalTypes.KW_IF
    @JvmField val KW_THEN: IElementType = NaturalTypes.KW_THEN
    @JvmField val KW_ELSE: IElementType = NaturalTypes.KW_ELSE
    @JvmField val KW_FOR: IElementType = NaturalTypes.KW_FOR
    @JvmField val KW_REPEAT: IElementType = NaturalTypes.KW_REPEAT
    @JvmField val KW_WHILE: IElementType = NaturalTypes.KW_WHILE
    @JvmField val KW_UNTIL: IElementType = NaturalTypes.KW_UNTIL
    @JvmField val KW_DECIDE: IElementType = NaturalTypes.KW_DECIDE
    @JvmField val KW_WHEN: IElementType = NaturalTypes.KW_WHEN
    @JvmField val KW_ESCAPE: IElementType = NaturalTypes.KW_ESCAPE
    @JvmField val KW_PERFORM: IElementType = NaturalTypes.KW_PERFORM
    @JvmField val KW_LOOP: IElementType = NaturalTypes.KW_LOOP
    @JvmField val KW_FETCH: IElementType = NaturalTypes.KW_FETCH
    @JvmField val KW_STOP: IElementType = NaturalTypes.KW_STOP
    @JvmField val KW_TERMINATE: IElementType = NaturalTypes.KW_TERMINATE
    @JvmField val KW_SETTIME: IElementType = NaturalTypes.KW_SETTIME
    @JvmField val KW_ONCE: IElementType = NaturalTypes.KW_ONCE

    // —— Keywords — data access ———————————————————————————————————————————————————————
    @JvmField val KW_READ: IElementType = NaturalTypes.KW_READ
    @JvmField val KW_FIND: IElementType = NaturalTypes.KW_FIND
    @JvmField val KW_STORE: IElementType = NaturalTypes.KW_STORE
    @JvmField val KW_UPDATE: IElementType = NaturalTypes.KW_UPDATE
    @JvmField val KW_DELETE: IElementType = NaturalTypes.KW_DELETE
    @JvmField val KW_GET: IElementType = NaturalTypes.KW_GET
    @JvmField val KW_SORT: IElementType    = NaturalTypes.KW_SORT
    @JvmField val KW_THEM: IElementType    = NaturalTypes.KW_THEM
    @JvmField val KW_GIVE: IElementType    = NaturalTypes.KW_GIVE
    @JvmField val KW_KEYS: IElementType    = NaturalTypes.KW_KEYS
    @JvmField val KW_MAX: IElementType     = NaturalTypes.KW_MAX
    @JvmField val KW_MIN: IElementType     = NaturalTypes.KW_MIN
    @JvmField val KW_NMIN: IElementType    = NaturalTypes.KW_NMIN
    @JvmField val KW_COUNT: IElementType   = NaturalTypes.KW_COUNT
    @JvmField val KW_NCOUNT: IElementType  = NaturalTypes.KW_NCOUNT
    @JvmField val KW_OLD: IElementType     = NaturalTypes.KW_OLD
    @JvmField val KW_AVER: IElementType    = NaturalTypes.KW_AVER
    @JvmField val KW_NAVER: IElementType   = NaturalTypes.KW_NAVER
    @JvmField val KW_SUM: IElementType     = NaturalTypes.KW_SUM
    @JvmField val KW_TOTAL: IElementType   = NaturalTypes.KW_TOTAL
    @JvmField val KW_HISTOGRAM: IElementType = NaturalTypes.KW_HISTOGRAM

    // —— Keywords — I/O ———————————————————————————————————————————————————————————————
    @JvmField val KW_WRITE: IElementType = NaturalTypes.KW_WRITE
    @JvmField val KW_DISPLAY: IElementType = NaturalTypes.KW_DISPLAY
    @JvmField val KW_PRINT: IElementType = NaturalTypes.KW_PRINT
    @JvmField val KW_INPUT: IElementType = NaturalTypes.KW_INPUT
    @JvmField val KW_REINPUT: IElementType = NaturalTypes.KW_REINPUT
    @JvmField val KW_SKIP: IElementType = NaturalTypes.KW_SKIP
    @JvmField val KW_NEWPAGE: IElementType = NaturalTypes.KW_NEWPAGE
    @JvmField val KW_EJECT: IElementType = NaturalTypes.KW_EJECT
    @JvmField val KW_FORMAT: IElementType = NaturalTypes.KW_FORMAT

    // —— Keywords — data manipulation —————————————————————————————————————————————————
    @JvmField val KW_MOVE: IElementType = NaturalTypes.KW_MOVE
    @JvmField val KW_ASSIGN: IElementType = NaturalTypes.KW_ASSIGN
    @JvmField val KW_COMPUTE: IElementType = NaturalTypes.KW_COMPUTE
    @JvmField val KW_ADD: IElementType = NaturalTypes.KW_ADD
    @JvmField val KW_SUBTRACT: IElementType = NaturalTypes.KW_SUBTRACT
    @JvmField val KW_MULTIPLY: IElementType = NaturalTypes.KW_MULTIPLY
    @JvmField val KW_DIVIDE: IElementType = NaturalTypes.KW_DIVIDE
    @JvmField val KW_COMPRESS: IElementType = NaturalTypes.KW_COMPRESS
    @JvmField val KW_EXAMINE: IElementType = NaturalTypes.KW_EXAMINE
    @JvmField val KW_SEPARATE: IElementType = NaturalTypes.KW_SEPARATE
    @JvmField val KW_TRANSLATE: IElementType = NaturalTypes.KW_TRANSLATE
    @JvmField val KW_RESET: IElementType = NaturalTypes.KW_RESET
    @JvmField val KW_REDUCE: IElementType = NaturalTypes.KW_REDUCE
    @JvmField val KW_EXPAND: IElementType = NaturalTypes.KW_EXPAND
    @JvmField val KW_ARRAY: IElementType = NaturalTypes.KW_ARRAY
    @JvmField val KW_RESIZE: IElementType = NaturalTypes.KW_RESIZE
    @JvmField val KW_STACK: IElementType = NaturalTypes.KW_STACK

    // —— Keywords — calls —————————————————————————————————————————————————————————————
    @JvmField val KW_CALLNAT: IElementType = NaturalTypes.KW_CALLNAT
    @JvmField val KW_CALL: IElementType = NaturalTypes.KW_CALL
    @JvmField val KW_RUN: IElementType = NaturalTypes.KW_RUN
    @JvmField val KW_INCLUDE: IElementType = NaturalTypes.KW_INCLUDE

    // —— Keywords — data definitions ——————————————————————————————————————————————————
    @JvmField val KW_LOCAL: IElementType = NaturalTypes.KW_LOCAL
    @JvmField val KW_GLOBAL: IElementType = NaturalTypes.KW_GLOBAL
    @JvmField val KW_PARAMETER: IElementType = NaturalTypes.KW_PARAMETER
    @JvmField val KW_INDEPENDENT: IElementType = NaturalTypes.KW_INDEPENDENT
    @JvmField val KW_CONTEXT: IElementType = NaturalTypes.KW_CONTEXT
    @JvmField val KW_USING: IElementType = NaturalTypes.KW_USING
    @JvmField val KW_REDEFINE: IElementType = NaturalTypes.KW_REDEFINE
    @JvmField val KW_CONST: IElementType = NaturalTypes.KW_CONST
    @JvmField val KW_DYNAMIC: IElementType = NaturalTypes.KW_DYNAMIC
    @JvmField val KW_INIT: IElementType = NaturalTypes.KW_INIT
    @JvmField val KW_HANDLE_OF: IElementType = NaturalTypes.KW_HANDLE_OF

    // —— Keywords — clauses / modifiers ———————————————————————————————————————————————
    @JvmField val KW_TO: IElementType = NaturalTypes.KW_TO
    @JvmField val KW_FROM: IElementType = NaturalTypes.KW_FROM
    @JvmField val KW_THRU: IElementType = NaturalTypes.KW_THRU
    @JvmField val KW_BY: IElementType = NaturalTypes.KW_BY
    @JvmField val KW_VALUES: IElementType = NaturalTypes.KW_VALUES
    @JvmField val KW_VALUE: IElementType = NaturalTypes.KW_VALUE
    @JvmField val KW_OF: IElementType = NaturalTypes.KW_OF
    @JvmField val KW_VIEW: IElementType = NaturalTypes.KW_VIEW
    @JvmField val KW_IN: IElementType = NaturalTypes.KW_IN
    @JvmField val KW_AT: IElementType = NaturalTypes.KW_AT
    @JvmField val KW_BREAK: IElementType = NaturalTypes.KW_BREAK
    @JvmField val KW_BEFORE: IElementType = NaturalTypes.KW_BEFORE
    @JvmField val KW_PROCESSING: IElementType = NaturalTypes.KW_PROCESSING
    @JvmField val KW_PROCESS: IElementType = NaturalTypes.KW_PROCESS
    @JvmField val KW_COMMAND: IElementType = NaturalTypes.KW_COMMAND
    @JvmField val KW_REQUEST: IElementType = NaturalTypes.KW_REQUEST
    @JvmField val KW_DOCUMENT: IElementType = NaturalTypes.KW_DOCUMENT
    @JvmField val KW_STARTING: IElementType = NaturalTypes.KW_STARTING
    @JvmField val KW_ENDING: IElementType = NaturalTypes.KW_ENDING
    @JvmField val KW_WHERE: IElementType = NaturalTypes.KW_WHERE
    @JvmField val KW_LIMIT: IElementType = NaturalTypes.KW_LIMIT
    @JvmField val KW_WITH: IElementType = NaturalTypes.KW_WITH
    @JvmField val KW_INTO: IElementType = NaturalTypes.KW_INTO
    @JvmField val KW_GIVING: IElementType = NaturalTypes.KW_GIVING
    @JvmField val KW_ALL: IElementType = NaturalTypes.KW_ALL
    @JvmField val KW_EDITED: IElementType = NaturalTypes.KW_EDITED
    @JvmField val KW_FIRST: IElementType = NaturalTypes.KW_FIRST
    @JvmField val KW_NONE: IElementType = NaturalTypes.KW_NONE
    @JvmField val KW_ON: IElementType = NaturalTypes.KW_ON
    @JvmField val KW_DATA: IElementType = NaturalTypes.KW_DATA
    @JvmField val KW_STEP: IElementType = NaturalTypes.KW_STEP
    @JvmField val KW_BOTTOM: IElementType = NaturalTypes.KW_BOTTOM
    @JvmField val KW_TOP: IElementType = NaturalTypes.KW_TOP
    @JvmField val KW_ROUTINE: IElementType = NaturalTypes.KW_ROUTINE
    @JvmField val KW_MODULE: IElementType = NaturalTypes.KW_MODULE
    @JvmField val KW_IMMEDIATE: IElementType = NaturalTypes.KW_IMMEDIATE
    @JvmField val KW_PAGE: IElementType = NaturalTypes.KW_PAGE
    @JvmField val KW_START: IElementType = NaturalTypes.KW_START
    @JvmField val KW_ROUNDED: IElementType = NaturalTypes.KW_ROUNDED
    @JvmField val KW_NAME: IElementType = NaturalTypes.KW_NAME
    @JvmField val KW_MASK: IElementType = NaturalTypes.KW_MASK
    @JvmField val KW_RIGHT: IElementType = NaturalTypes.KW_RIGHT
    @JvmField val KW_LEFT: IElementType = NaturalTypes.KW_LEFT
    @JvmField val KW_JUSTIFIED: IElementType = NaturalTypes.KW_JUSTIFIED
    @JvmField val KW_NORMALIZED: IElementType = NaturalTypes.KW_NORMALIZED
    @JvmField val KW_ENCODED: IElementType = NaturalTypes.KW_ENCODED
    @JvmField val KW_POSITION: IElementType = NaturalTypes.KW_POSITION
    @JvmField val KW_IGNORE: IElementType = NaturalTypes.KW_IGNORE
    @JvmField val KW_REMAINDER: IElementType = NaturalTypes.KW_REMAINDER
    @JvmField val KW_OPTIONS: IElementType = NaturalTypes.KW_OPTIONS

    @JvmField val KW_PHYSICAL: IElementType = NaturalTypes.KW_PHYSICAL
    @JvmField val KW_SEQUENCE: IElementType = NaturalTypes.KW_SEQUENCE
    @JvmField val KW_LOGICAL: IElementType = NaturalTypes.KW_LOGICAL
    @JvmField val KW_ASCENDING: IElementType = NaturalTypes.KW_ASCENDING
    @JvmField val KW_DESCENDING: IElementType = NaturalTypes.KW_DESCENDING
    @JvmField val KW_REPOSITION: IElementType = NaturalTypes.KW_REPOSITION
    @JvmField val KW_SHARED: IElementType = NaturalTypes.KW_SHARED
    @JvmField val KW_HOLD: IElementType = NaturalTypes.KW_HOLD
    @JvmField val KW_MODE: IElementType = NaturalTypes.KW_MODE
    @JvmField val KW_SPACES: IElementType = NaturalTypes.KW_LEAVING
    @JvmField val KW_LEAVING: IElementType = NaturalTypes.KW_LEAVING
    @JvmField val KW_NO: IElementType = NaturalTypes.KW_NO
    @JvmField val KW_SPACE: IElementType = NaturalTypes.KW_SPACE
    @JvmField val KW_REPLACE: IElementType = NaturalTypes.KW_REPLACE
    @JvmField val KW_DELIMITER: IElementType = NaturalTypes.KW_DELIMITER
    @JvmField val KW_DELIMITERS: IElementType = NaturalTypes.KW_DELIMITERS
    @JvmField val KW_PATTERN: IElementType = NaturalTypes.KW_PATTERN
    @JvmField val KW_DIRECTION: IElementType = NaturalTypes.KW_DIRECTION
    @JvmField val KW_BACKWARD: IElementType = NaturalTypes.KW_BACKWARD
    @JvmField val KW_FORWARD: IElementType = NaturalTypes.KW_FORWARD
    @JvmField val KW_WORK: IElementType = NaturalTypes.KW_WORK
    @JvmField val KW_FILE: IElementType = NaturalTypes.KW_FILE
    @JvmField val KW_CLOSE: IElementType = NaturalTypes.KW_CLOSE
    @JvmField val KW_DOWNLOAD: IElementType = NaturalTypes.KW_DOWNLOAD
    @JvmField val KW_SCAN: IElementType = NaturalTypes.KW_SCAN
    @JvmField val KW_RESULT: IElementType = NaturalTypes.KW_RESULT
    @JvmField val KW_OPTIONAL: IElementType = NaturalTypes.KW_OPTIONAL
    @JvmField val KW_SUBSTRING: IElementType = NaturalTypes.KW_SUBSTRING
    @JvmField val KW_LINES: IElementType = NaturalTypes.KW_LINES
    @JvmField val KW_DO: IElementType = NaturalTypes.KW_DO
    @JvmField val KW_DOEND: IElementType = NaturalTypes.KW_DOEND
    @JvmField val KW_KEY: IElementType = NaturalTypes.KW_KEY
    @JvmField val KW_SV_TRANSLATE: IElementType = NaturalTypes.KW_SV_TRANSLATE
    @JvmField val KW_SPECIFIED: IElementType = NaturalTypes.KW_SPECIFIED
    @JvmField val KW_SELECT: IElementType = NaturalTypes.KW_SELECT
    @JvmField val KW_END_HISTOGRAM: IElementType = NaturalTypes.KW_END_HISTOGRAM
    @JvmField val PERCENT: IElementType = NaturalTypes.PERCENT

    // —— Keywords — transaction / error ———————————————————————————————————————————————
    @JvmField val KW_ON_ERROR: IElementType = NaturalTypes.KW_ON_ERROR
    @JvmField val KW_BACKOUT: IElementType = NaturalTypes.KW_BACKOUT
    @JvmField val KW_COMMIT: IElementType = NaturalTypes.KW_COMMIT
    @JvmField val KW_REJECT: IElementType = NaturalTypes.KW_REJECT
    @JvmField val KW_ACCEPT: IElementType = NaturalTypes.KW_ACCEPT
    @JvmField val KW_RETRY: IElementType = NaturalTypes.KW_RETRY
    @JvmField val KW_RELEASE: IElementType = NaturalTypes.KW_RELEASE
    @JvmField val KW_TRANSACTION: IElementType = NaturalTypes.KW_TRANSACTION
    @JvmField val KW_ISN: IElementType = NaturalTypes.KW_ISN
    @JvmField val KW_VARIABLE: IElementType = NaturalTypes.KW_VARIABLE
    @JvmField val KW_RECORDS: IElementType = NaturalTypes.KW_RECORDS
    @JvmField val KW_PASSWORD: IElementType = NaturalTypes.KW_PASSWORD
    @JvmField val KW_CIPHER: IElementType = NaturalTypes.KW_CIPHER
    @JvmField val KW_MULTI_FETCH: IElementType = NaturalTypes.KW_MULTI_FETCH
    @JvmField val KW_OFF: IElementType = NaturalTypes.KW_OFF
    @JvmField val KW_NUMBER: IElementType = NaturalTypes.KW_NUMBER
    @JvmField val KW_UNIQUE: IElementType = NaturalTypes.KW_UNIQUE
    @JvmField val KW_COUPLED: IElementType = NaturalTypes.KW_COUPLED
    @JvmField val KW_VIA: IElementType = NaturalTypes.KW_VIA
    @JvmField val KW_SORTED: IElementType = NaturalTypes.KW_SORTED
    @JvmField val KW_RETAIN: IElementType = NaturalTypes.KW_RETAIN
    @JvmField val KW_AS: IElementType = NaturalTypes.KW_AS
    @JvmField val KW_FOUND: IElementType = NaturalTypes.KW_FOUND
    @JvmField val KW_BUT: IElementType = NaturalTypes.KW_BUT
    @JvmField val KW_ENTER: IElementType = NaturalTypes.KW_ENTER
    @JvmField val KW_EVERY: IElementType = NaturalTypes.KW_EVERY
    @JvmField val KW_CONDITION: IElementType = NaturalTypes.KW_CONDITION
    @JvmField val KW_ANY: IElementType = NaturalTypes.KW_ANY
    @JvmField val KW_ERASE: IElementType = NaturalTypes.KW_ERASE
    @JvmField val KW_MAP: IElementType = NaturalTypes.KW_MAP
    @JvmField val KW_ALARM: IElementType = NaturalTypes.KW_ALARM
    @JvmField val KW_HELP: IElementType = NaturalTypes.KW_HELP
    @JvmField val KW_MARK: IElementType = NaturalTypes.KW_MARK
    @JvmField val KW_MODIFIED: IElementType = NaturalTypes.KW_MODIFIED
    @JvmField val KW_TEXT: IElementType = NaturalTypes.KW_TEXT
    @JvmField val KW_WINDOW: IElementType = NaturalTypes.KW_WINDOW
    @JvmField val KW_SOUND: IElementType = NaturalTypes.KW_SOUND
    @JvmField val KW_RECORD: IElementType = NaturalTypes.KW_RECORD
    @JvmField val KW_STATEMENT: IElementType = NaturalTypes.KW_STATEMENT
    @JvmField val KW_SET: IElementType = NaturalTypes.KW_SET
    @JvmField val KW_SAME: IElementType = NaturalTypes.KW_SAME
    @JvmField val KW_TRUE: IElementType = NaturalTypes.KW_TRUE
    @JvmField val KW_FALSE: IElementType = NaturalTypes.KW_FALSE
    @JvmField val KW_UPPER: IElementType = NaturalTypes.KW_UPPER
    @JvmField val KW_LOWER: IElementType = NaturalTypes.KW_LOWER
    @JvmField val KW_CASE: IElementType = NaturalTypes.KW_CASE
    @JvmField val KW_INDEX: IElementType = NaturalTypes.KW_INDEX
    @JvmField val KW_FULL: IElementType = NaturalTypes.KW_FULL
    @JvmField val KW_LENGTH: IElementType = NaturalTypes.KW_LENGTH
    @JvmField val KW_FRAMED: IElementType = NaturalTypes.KW_FRAMED
    @JvmField val KW_INCDIC: IElementType = NaturalTypes.KW_INCDIC
    @JvmField val KW_CONTROL: IElementType = NaturalTypes.KW_CONTROL
    @JvmField val CARET: IElementType = NaturalTypes.CARET
    @JvmField val SUBST_PARAM: IElementType = NaturalTypes.SUBST_PARAM
    @JvmField val SEMICOLON: IElementType = NaturalTypes.SEMICOLON

    // —— Whitespace (includes SEMICOLON which acts as statement separator) ————————————
    @JvmField val WHITESPACE: TokenSet = TokenSet.create(
        com.intellij.psi.TokenType.WHITE_SPACE,
        SEMICOLON
    )

    // —— System Variables —————————————————————————————————————————————————————————————
    // Application-related
    @JvmField val SV_APPLIC_ID: IElementType       = NaturalTypes.SV_APPLIC_ID
    @JvmField val SV_APPLIC_NAME: IElementType     = NaturalTypes.SV_APPLIC_NAME
    @JvmField val SV_COM: IElementType             = NaturalTypes.SV_COM
    @JvmField val SV_CONVID: IElementType          = NaturalTypes.SV_CONVID
    @JvmField val SV_COUNTER: IElementType         = NaturalTypes.SV_COUNTER
    @JvmField val SV_CPU_TIME: IElementType        = NaturalTypes.SV_CPU_TIME
    @JvmField val SV_CURRENT_UNIT: IElementType    = NaturalTypes.SV_CURRENT_UNIT
    @JvmField val SV_DATA: IElementType            = NaturalTypes.SV_DATA
    @JvmField val SV_ERROR_LINE: IElementType      = NaturalTypes.SV_ERROR_LINE
    @JvmField val SV_ERROR_NR: IElementType        = NaturalTypes.SV_ERROR_NR
    @JvmField val SV_ERROR_TA: IElementType        = NaturalTypes.SV_ERROR_TA
    @JvmField val SV_ETID: IElementType            = NaturalTypes.SV_ETID
    @JvmField val SV_ISN: IElementType             = NaturalTypes.SV_ISN
    @JvmField val SV_LBOUND: IElementType          = NaturalTypes.SV_LBOUND
    @JvmField val SV_LENGTH: IElementType          = NaturalTypes.SV_LENGTH
    @JvmField val SV_LEVEL: IElementType           = NaturalTypes.SV_LEVEL
    @JvmField val SV_LIBRARY_ID: IElementType      = NaturalTypes.SV_LIBRARY_ID
    @JvmField val SV_LINE: IElementType            = NaturalTypes.SV_LINE
    @JvmField val SV_LINEX: IElementType           = NaturalTypes.SV_LINEX
    @JvmField val SV_LOAD_LIBRARY_ID: IElementType = NaturalTypes.SV_LOAD_LIBRARY_ID
    @JvmField val SV_NUMBER: IElementType          = NaturalTypes.SV_NUMBER
    @JvmField val SV_OCCURRENCE: IElementType      = NaturalTypes.SV_OCCURRENCE
    @JvmField val SV_PAGE_EVENT: IElementType      = NaturalTypes.SV_PAGE_EVENT
    @JvmField val SV_PAGE_LEVEL: IElementType      = NaturalTypes.SV_PAGE_LEVEL
    @JvmField val SV_PROGRAM: IElementType         = NaturalTypes.SV_PROGRAM
    @JvmField val SV_REINPUT_TYPE: IElementType    = NaturalTypes.SV_REINPUT_TYPE
    @JvmField val SV_ROWCOUNT: IElementType        = NaturalTypes.SV_ROWCOUNT
    @JvmField val SV_STARTUP: IElementType         = NaturalTypes.SV_STARTUP
    @JvmField val SV_STEPLIB: IElementType         = NaturalTypes.SV_STEPLIB
    @JvmField val SV_SUBROUTINE: IElementType      = NaturalTypes.SV_SUBROUTINE
    @JvmField val SV_THIS_OBJECT: IElementType     = NaturalTypes.SV_THIS_OBJECT
    @JvmField val SV_TYPE: IElementType            = NaturalTypes.SV_TYPE
    @JvmField val SV_UBOUND: IElementType          = NaturalTypes.SV_UBOUND
    // Date/Time
    @JvmField val SV_DATD: IElementType   = NaturalTypes.SV_DATD
    @JvmField val SV_DAT4D: IElementType  = NaturalTypes.SV_DAT4D
    @JvmField val SV_DATE: IElementType   = NaturalTypes.SV_DATE
    @JvmField val SV_DAT4E: IElementType  = NaturalTypes.SV_DAT4E
    @JvmField val SV_DATG: IElementType   = NaturalTypes.SV_DATG
    @JvmField val SV_DATI: IElementType   = NaturalTypes.SV_DATI
    @JvmField val SV_DAT4I: IElementType  = NaturalTypes.SV_DAT4I
    @JvmField val SV_DATJ: IElementType   = NaturalTypes.SV_DATJ
    @JvmField val SV_DAT4J: IElementType  = NaturalTypes.SV_DAT4J
    @JvmField val SV_DATN: IElementType   = NaturalTypes.SV_DATN
    @JvmField val SV_DATU: IElementType   = NaturalTypes.SV_DATU
    @JvmField val SV_DAT4U: IElementType  = NaturalTypes.SV_DAT4U
    @JvmField val SV_DATV: IElementType   = NaturalTypes.SV_DATV
    @JvmField val SV_DATVS: IElementType  = NaturalTypes.SV_DATVS
    @JvmField val SV_DATX: IElementType   = NaturalTypes.SV_DATX
    @JvmField val SV_TIMD: IElementType   = NaturalTypes.SV_TIMD
    @JvmField val SV_TIME: IElementType   = NaturalTypes.SV_TIME
    @JvmField val SV_TIME_OUT: IElementType  = NaturalTypes.SV_TIME_OUT
    @JvmField val SV_TIMESTMP: IElementType  = NaturalTypes.SV_TIMESTMP
    @JvmField val SV_TIMN: IElementType   = NaturalTypes.SV_TIMN
    @JvmField val SV_TIMX: IElementType   = NaturalTypes.SV_TIMX
    // Input/Output
    @JvmField val SV_CURS_COL: IElementType   = NaturalTypes.SV_CURS_COL
    @JvmField val SV_CURS_FIELD: IElementType = NaturalTypes.SV_CURS_FIELD
    @JvmField val SV_CURS_LINE: IElementType  = NaturalTypes.SV_CURS_LINE
    @JvmField val SV_CURSOR: IElementType     = NaturalTypes.SV_CURSOR
    @JvmField val SV_LINE_COUNT: IElementType = NaturalTypes.SV_LINE_COUNT
    @JvmField val SV_LINESIZE: IElementType   = NaturalTypes.SV_LINESIZE
    @JvmField val SV_LOG_LS: IElementType     = NaturalTypes.SV_LOG_LS
    @JvmField val SV_LOG_PS: IElementType     = NaturalTypes.SV_LOG_PS
    @JvmField val SV_PAGE_NUMBER: IElementType = NaturalTypes.SV_PAGE_NUMBER
    @JvmField val SV_PAGESIZE: IElementType   = NaturalTypes.SV_PAGESIZE
    @JvmField val SV_PF_KEY: IElementType     = NaturalTypes.SV_PF_KEY
    @JvmField val SV_PF_NAME: IElementType    = NaturalTypes.SV_PF_NAME
    @JvmField val SV_WINDOW_LS: IElementType  = NaturalTypes.SV_WINDOW_LS
    @JvmField val SV_WINDOW_POS: IElementType = NaturalTypes.SV_WINDOW_POS
    @JvmField val SV_WINDOW_PS: IElementType  = NaturalTypes.SV_WINDOW_PS
    // Natural environment
    @JvmField val SV_BROWSER_IO: IElementType  = NaturalTypes.SV_BROWSER_IO
    @JvmField val SV_DEVICE: IElementType      = NaturalTypes.SV_DEVICE
    @JvmField val SV_GROUP: IElementType       = NaturalTypes.SV_GROUP
    @JvmField val SV_HARDCOPY: IElementType    = NaturalTypes.SV_HARDCOPY
    @JvmField val SV_INIT_USER: IElementType   = NaturalTypes.SV_INIT_USER
    @JvmField val SV_LANGUAGE: IElementType    = NaturalTypes.SV_LANGUAGE
    @JvmField val SV_NATVERS: IElementType     = NaturalTypes.SV_NATVERS
    @JvmField val SV_NET_USER: IElementType    = NaturalTypes.SV_NET_USER
    @JvmField val SV_PARM_USER: IElementType   = NaturalTypes.SV_PARM_USER
    @JvmField val SV_PATCH_LEVEL: IElementType = NaturalTypes.SV_PATCH_LEVEL
    @JvmField val SV_PID: IElementType         = NaturalTypes.SV_PID
    @JvmField val SV_SCREEN_IO: IElementType   = NaturalTypes.SV_SCREEN_IO
    @JvmField val SV_SERVER_TYPE: IElementType = NaturalTypes.SV_SERVER_TYPE
    @JvmField val SV_UI: IElementType          = NaturalTypes.SV_UI
    @JvmField val SV_USER: IElementType        = NaturalTypes.SV_USER
    @JvmField val SV_USER_NAME: IElementType   = NaturalTypes.SV_USER_NAME
    // System environment
    @JvmField val SV_CODEPAGE: IElementType      = NaturalTypes.SV_CODEPAGE
    @JvmField val SV_HARDWARE: IElementType      = NaturalTypes.SV_HARDWARE
    @JvmField val SV_HOSTNAME: IElementType      = NaturalTypes.SV_HOSTNAME
    @JvmField val SV_INIT_ID: IElementType       = NaturalTypes.SV_INIT_ID
    @JvmField val SV_INIT_PROGRAM: IElementType  = NaturalTypes.SV_INIT_PROGRAM
    @JvmField val SV_LOCALE: IElementType        = NaturalTypes.SV_LOCALE
    @JvmField val SV_MACHINE_CLASS: IElementType = NaturalTypes.SV_MACHINE_CLASS
    @JvmField val SV_OPSYS: IElementType         = NaturalTypes.SV_OPSYS
    @JvmField val SV_OS: IElementType            = NaturalTypes.SV_OS
    @JvmField val SV_OSVERS: IElementType        = NaturalTypes.SV_OSVERS
    @JvmField val SV_TP: IElementType            = NaturalTypes.SV_TP
    @JvmField val SV_TPSYS: IElementType         = NaturalTypes.SV_TPSYS
    @JvmField val SV_TPVERS: IElementType        = NaturalTypes.SV_TPVERS
    @JvmField val SV_WINMGR: IElementType        = NaturalTypes.SV_WINMGR
    @JvmField val SV_WINMGRVERS: IElementType    = NaturalTypes.SV_WINMGRVERS
    // XML
    @JvmField val SV_PARSE_COL: IElementType           = NaturalTypes.SV_PARSE_COL
    @JvmField val SV_PARSE_LEVEL: IElementType         = NaturalTypes.SV_PARSE_LEVEL
    @JvmField val SV_PARSE_NAMESPACE_URI: IElementType = NaturalTypes.SV_PARSE_NAMESPACE_URI
    @JvmField val SV_PARSE_ROW: IElementType           = NaturalTypes.SV_PARSE_ROW
    @JvmField val SV_PARSE_TYPE: IElementType          = NaturalTypes.SV_PARSE_TYPE


    // —— Operators ————————————————————————————————————————————————————————————————————
    @JvmField val KW_AND: IElementType = NaturalTypes.KW_AND
    @JvmField val KW_OR: IElementType = NaturalTypes.KW_OR
    @JvmField val KW_NOT: IElementType = NaturalTypes.KW_NOT
    @JvmField val KW_EQUALS: IElementType = NaturalTypes.KW_EQUALS
    @JvmField val KW_EQUAL: IElementType = NaturalTypes.KW_EQUAL
    @JvmField val KW_LESS: IElementType = NaturalTypes.KW_LESS
    @JvmField val KW_GREATER: IElementType = NaturalTypes.KW_GREATER
    @JvmField val KW_THAN: IElementType = NaturalTypes.KW_THAN

    @JvmField val ASSIGN_OP: IElementType = NaturalTypes.ASSIGN_OP
    @JvmField val LE_OP: IElementType = NaturalTypes.LE_OP
    @JvmField val GE_OP: IElementType = NaturalTypes.GE_OP
    @JvmField val LT_OP: IElementType = NaturalTypes.LT_OP
    @JvmField val GT_OP: IElementType = NaturalTypes.GT_OP
    @JvmField val EQ_OP: IElementType = NaturalTypes.EQ_OP
    @JvmField val NEQ_OP: IElementType = NaturalTypes.NEQ_OP

    @JvmField val DOUBLE_STAR: IElementType = NaturalTypes.DOUBLE_STAR
    @JvmField val PLUS: IElementType = NaturalTypes.PLUS
    @JvmField val MINUS: IElementType = NaturalTypes.MINUS
    @JvmField val STAR: IElementType = NaturalTypes.STAR
    @JvmField val SLASH: IElementType = NaturalTypes.SLASH

    // —— Punctuation ——————————————————————————————————————————————————————————————————
    @JvmField val COLON: IElementType = NaturalTypes.COLON
    @JvmField val DOT: IElementType = NaturalTypes.DOT
    @JvmField val COMMA: IElementType = NaturalTypes.COMMA

    @JvmField val LPAREN: IElementType = NaturalTypes.LPAREN
    @JvmField val RPAREN: IElementType = NaturalTypes.RPAREN
    @JvmField val LBRACKET: IElementType = NaturalTypes.LBRACKET
    @JvmField val RBRACKET: IElementType = NaturalTypes.RBRACKET

    // —— Data types ———————————————————————————————————————————————————————————————————
    @JvmField val DT_ALPHANUMERIC: IElementType = NaturalTypes.DT_ALPHANUMERIC;
    @JvmField val DT_BINARY: IElementType = NaturalTypes.DT_BINARY;
    @JvmField val DT_CV: IElementType = NaturalTypes.DT_CV;
    @JvmField val DT_DATE: IElementType = NaturalTypes.DT_DATE;
    @JvmField val DT_FLOAT: IElementType = NaturalTypes.DT_FLOAT;
    @JvmField val DT_INT: IElementType = NaturalTypes.DT_INT;
    @JvmField val DT_LOG: IElementType = NaturalTypes.DT_LOG;
    @JvmField val DT_NUMBER: IElementType = NaturalTypes.DT_NUMBER;
    @JvmField val DT_OBJECT: IElementType = NaturalTypes.DT_OBJECT;
    @JvmField val DT_PACK: IElementType = NaturalTypes.DT_PACK;
    @JvmField val DT_TIME: IElementType = NaturalTypes.DT_TIME;
    @JvmField val DT_UNICODE: IElementType = NaturalTypes.DT_UNICODE;

    // —— Session Parameters ———————————————————————————————————————————————————————————
    @JvmField val SP_AD: IElementType = NaturalTypes.SP_AD
    @JvmField val SP_AL: IElementType = NaturalTypes.SP_AL
    @JvmField val SP_CD: IElementType = NaturalTypes.SP_CD
    @JvmField val SP_DF: IElementType = NaturalTypes.SP_DF
    @JvmField val SP_DL: IElementType = NaturalTypes.SP_DL
    @JvmField val SP_EM: IElementType = NaturalTypes.SP_EM
    @JvmField val SP_ES: IElementType = NaturalTypes.SP_ES
    @JvmField val SP_FC: IElementType = NaturalTypes.SP_FC
    @JvmField val SP_FL: IElementType = NaturalTypes.SP_FL
    @JvmField val SP_GC: IElementType = NaturalTypes.SP_GC
    @JvmField val SP_HC: IElementType = NaturalTypes.SP_HC
    @JvmField val SP_HW: IElementType = NaturalTypes.SP_HW
    @JvmField val SP_IC: IElementType = NaturalTypes.SP_IC
    @JvmField val SP_ICU: IElementType = NaturalTypes.SP_ICU
    @JvmField val SP_IP: IElementType = NaturalTypes.SP_IP
    @JvmField val SP_IS: IElementType = NaturalTypes.SP_IS
    @JvmField val SP_KD: IElementType = NaturalTypes.SP_KD
    @JvmField val SP_LC: IElementType = NaturalTypes.SP_LC
    @JvmField val SP_LCU: IElementType = NaturalTypes.SP_LCU
    @JvmField val SP_LS: IElementType = NaturalTypes.SP_LS
    @JvmField val SP_MC: IElementType = NaturalTypes.SP_MC
    @JvmField val SP_MP: IElementType = NaturalTypes.SP_MP
    @JvmField val SP_MS: IElementType = NaturalTypes.SP_MS
    @JvmField val SP_NL: IElementType = NaturalTypes.SP_NL
    @JvmField val SP_PC: IElementType = NaturalTypes.SP_PC
    @JvmField val SP_PM: IElementType = NaturalTypes.SP_PM
    @JvmField val SP_PS: IElementType = NaturalTypes.SP_PS
    @JvmField val SP_SF: IElementType = NaturalTypes.SP_SF
    @JvmField val SP_SG: IElementType = NaturalTypes.SP_SG
    @JvmField val SP_TC: IElementType = NaturalTypes.SP_TC
    @JvmField val SP_TCU: IElementType = NaturalTypes.SP_TCU
    @JvmField val SP_DY: IElementType = NaturalTypes.SP_DY
    @JvmField val SP_UC: IElementType = NaturalTypes.SP_UC
    @JvmField val SP_ZP: IElementType = NaturalTypes.SP_ZP

    // —— Keywords — bad character —————————————————————————————————————————————————————

    // —————————————————————————————————————————————————————————————————————————————————
    // —— Token sets ———————————————————————————————————————————————————————————————————
    // —————————————————————————————————————————————————————————————————————————————————


    @JvmField val KEYWORDS = TokenSet.create(
        KW_DEFINE_DATA, KW_END_DEFINE, KW_END, KW_CALLNAT, KW_INCLUDE, KW_READ, KW_FIND, KW_STORE,
        KW_UPDATE, KW_DELETE, KW_GET, KW_SORT, KW_THEM, KW_GIVE, KW_KEYS,
        KW_MAX, KW_MIN, KW_NMIN, KW_COUNT, KW_NCOUNT, KW_OLD, KW_AVER, KW_NAVER, KW_SUM, KW_TOTAL,
        KW_HISTOGRAM, KW_WRITE, KW_DISPLAY,
        KW_PRINT, KW_INPUT, KW_REINPUT, KW_SKIP, KW_NEWPAGE, KW_EJECT, KW_FORMAT,
        KW_END_IF, KW_END_FOR, KW_END_REPEAT, KW_END_DECIDE, KW_END_DEFINE,
        KW_END_SUBROUTINE, KW_END_FUNCTION, KW_END_READ, KW_END_FIND, KW_END_SORT,
        KW_END_BREAK, KW_END_ENDDATA, KW_END_ENDFILE, KW_END_ENDPAGE, KW_END_ALL, KW_END_BEFORE,
        KW_END_START, KW_END_TOPPAGE, KW_END_ERROR, KW_END_TRANSACTION, KW_END_NOREC, KW_END_WORK,
        KW_END, KW_IF, KW_THEN, KW_ELSE, KW_FOR, KW_REPEAT, KW_WHILE, KW_UNTIL,
        KW_DECIDE, KW_WHEN, KW_ESCAPE, KW_PERFORM, KW_LOOP, KW_FETCH, KW_STOP, KW_TERMINATE, KW_SETTIME,
        KW_DEFINE, KW_SUBROUTINE, KW_DEFINE_FUNCTION, KW_DEFINE_PRINTER, KW_DEFINE_WINDOW,
        KW_DEFINE_WORK_FILE, KW_DEFINE_PROTOTYPE, KW_DEFINE_CLASS, KW_MOVE, KW_ASSIGN,
        KW_COMPUTE, KW_ADD, KW_SUBTRACT, KW_MULTIPLY, KW_DIVIDE, KW_COMPRESS, KW_EXAMINE,
        KW_SEPARATE, KW_TRANSLATE, KW_RESET, KW_REDUCE, KW_EXPAND, KW_RESIZE, KW_STACK,
        KW_ON_ERROR, KW_BACKOUT, KW_COMMIT, KW_REJECT, KW_ACCEPT, KW_RETRY, KW_RELEASE, KW_END_TRANSACTION,
        KW_ISN, KW_VARIABLE, KW_RECORDS, KW_PASSWORD, KW_CIPHER, KW_MULTI_FETCH, KW_OFF,
        KW_NUMBER, KW_UNIQUE, KW_COUPLED, KW_VIA, KW_SORTED, KW_RETAIN, KW_AS,
        KW_FOUND, KW_BUT, KW_ENTER, KW_EVERY, KW_CONDITION, KW_ANY,
        KW_ERASE, KW_MAP, KW_ALARM, KW_MARK, KW_MODIFIED, KW_TEXT, KW_WINDOW, KW_SOUND,
        KW_RECORD, KW_STATEMENT, KW_SET, KW_SAME,
        KW_TRUE, KW_FALSE, KW_UPPER, KW_LOWER, KW_CASE, KW_INDEX, KW_FULL, KW_LENGTH, KW_FRAMED,
        KW_INCDIC, KW_CONTROL, KW_IMMEDIATE,
        KW_CALL, KW_RUN, KW_ONCE, KW_JUSTIFIED,
        KW_PROCESS, KW_COMMAND, KW_REQUEST, KW_DOCUMENT,
        KW_INDEPENDENT, KW_CONTEXT, KW_HANDLE_OF, KW_ARRAY,
        KW_ROUTINE, KW_MODULE, KW_RESULT, KW_OPTIONAL, KW_SUBSTRING,
        KW_LINES, KW_DO, KW_DOEND, KW_KEY, KW_SPECIFIED, KW_SELECT,
        KW_END_HISTOGRAM, KW_TRANSACTION, KW_HELP
    )

    @JvmField val MODIFIERS = TokenSet.create(
        KW_LOCAL, KW_GLOBAL, KW_PARAMETER, KW_USING, KW_REDEFINE, KW_CONST, KW_DYNAMIC, KW_VIEW,
        KW_INIT, KW_TO, KW_FROM, KW_THRU, KW_BY, KW_VALUE, KW_OF, KW_IN, KW_AT, KW_BREAK,
        KW_BEFORE, KW_PROCESSING, KW_STARTING, KW_ENDING, KW_WHERE, KW_LIMIT, KW_WITH,
        KW_INTO, KW_GIVING, KW_ALL, KW_EDITED, KW_FIRST, KW_NONE, KW_ON, KW_DATA, KW_STEP,
        KW_BOTTOM, KW_TOP, KW_PAGE, KW_START, KW_ROUNDED, KW_NAME, KW_IGNORE, KW_REMAINDER, KW_OPTIONS, KW_PHYSICAL, KW_VALUES,
        KW_MASK, KW_RIGHT, KW_LEFT, KW_JUSTIFIED, KW_NORMALIZED, KW_ENCODED, KW_POSITION,
        KW_SEQUENCE, KW_LOGICAL, KW_ASCENDING, KW_DESCENDING, KW_REPOSITION, KW_SHARED,
        KW_HOLD, KW_MODE, KW_SPACES, KW_LEAVING, KW_NO, KW_SPACE, KW_REPLACE, KW_DELIMITER,
        KW_DELIMITERS, KW_PATTERN, KW_DIRECTION, KW_BACKWARD, KW_FORWARD,
        KW_WORK, KW_FILE, KW_CLOSE, KW_DOWNLOAD, KW_SCAN
    )

    @JvmField val LOGICAL = TokenSet.create(
        KW_AND, KW_OR, KW_NOT, KW_EQUALS, KW_EQUAL, KW_LESS, KW_GREATER, KW_THAN
    )

    @JvmField val DATA_TYPES = TokenSet.create(
        DT_ALPHANUMERIC, DT_BINARY, DT_CV, DT_DATE, DT_FLOAT, DT_INT, DT_LOG,
        DT_NUMBER, DT_OBJECT, DT_PACK, DT_TIME, DT_UNICODE
    )

    @JvmField val SESSION_PARAMS = TokenSet.create(
        SP_AD, SP_AL, SP_CD, SP_DF, SP_DL, SP_EM, SP_ES, SP_FC, SP_FL, SP_GC, SP_HC,
        SP_HW, SP_IC, SP_ICU, SP_IP, SP_IS, SP_KD, SP_LC, SP_LCU, SP_LS, SP_MC, SP_MP,
        SP_MS, SP_NL, SP_PC, SP_PM, SP_PS, SP_SF, SP_SG, SP_TC, SP_TCU, SP_DY, SP_UC, SP_ZP
    )

    @JvmField val SYSTEM_VARIABLES = TokenSet.create(
        // Application-related
        SV_APPLIC_ID, SV_APPLIC_NAME, SV_COM, SV_CONVID, SV_COUNTER, SV_CPU_TIME,
        SV_CURRENT_UNIT, SV_DATA, SV_ERROR_LINE, SV_ERROR_NR, SV_ERROR_TA, SV_ETID,
        SV_ISN, SV_LBOUND, SV_LENGTH, SV_LEVEL, SV_LIBRARY_ID, SV_LINE, SV_LINEX,
        SV_LOAD_LIBRARY_ID, SV_NUMBER, SV_OCCURRENCE, SV_PAGE_EVENT, SV_PAGE_LEVEL,
        SV_PROGRAM, SV_REINPUT_TYPE, SV_ROWCOUNT, SV_STARTUP, SV_STEPLIB,
        SV_SUBROUTINE, SV_THIS_OBJECT, SV_TYPE, SV_UBOUND,
        // Date/Time
        SV_DATD, SV_DAT4D, SV_DATE, SV_DAT4E, SV_DATG, SV_DATI, SV_DAT4I,
        SV_DATJ, SV_DAT4J, SV_DATN, SV_DATU, SV_DAT4U, SV_DATV, SV_DATVS, SV_DATX,
        SV_TIMD, SV_TIME, SV_TIME_OUT, SV_TIMESTMP, SV_TIMN, SV_TIMX,
        // Input/Output
        SV_CURS_COL, SV_CURS_FIELD, SV_CURS_LINE, SV_CURSOR, SV_LINE_COUNT,
        SV_LINESIZE, SV_LOG_LS, SV_LOG_PS, SV_PAGE_NUMBER, SV_PAGESIZE,
        SV_PF_KEY, SV_PF_NAME, SV_WINDOW_LS, SV_WINDOW_POS, SV_WINDOW_PS,
        // Natural environment
        SV_BROWSER_IO, SV_DEVICE, SV_GROUP, SV_HARDCOPY, SV_INIT_USER, SV_LANGUAGE,
        SV_NATVERS, SV_NET_USER, SV_PARM_USER, SV_PATCH_LEVEL, SV_PID, SV_SCREEN_IO,
        SV_SERVER_TYPE, SV_UI, SV_USER, SV_USER_NAME,
        // System environment
        SV_CODEPAGE, SV_HARDWARE, SV_HOSTNAME, SV_INIT_ID, SV_INIT_PROGRAM, SV_LOCALE,
        SV_MACHINE_CLASS, SV_OPSYS, SV_OS, SV_OSVERS, SV_TP, SV_TPSYS, SV_TPVERS,
        SV_WINMGR, SV_WINMGRVERS,
        // XML
        SV_PARSE_COL, SV_PARSE_LEVEL, SV_PARSE_NAMESPACE_URI, SV_PARSE_ROW, SV_PARSE_TYPE,
        // *TRANSLATE (declared as KW_SV_TRANSLATE)
        KW_SV_TRANSLATE
    )
}