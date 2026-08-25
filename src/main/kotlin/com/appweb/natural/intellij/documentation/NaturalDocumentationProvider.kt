package com.appweb.natural.intellij.documentation

import com.appweb.natural.intellij.psi.NaturalCallnatStatement
import com.appweb.natural.intellij.psi.NaturalDataAreaBlock
import com.appweb.natural.intellij.psi.NaturalIncludeStatement
import com.appweb.natural.intellij.psi.NaturalTypes
import com.appweb.natural.intellij.reference.NaturalDataAreaUtils
import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.ASTNode
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

class NaturalDocumentationProvider : AbstractDocumentationProvider() {

    override fun getCustomDocumentationElement(
        editor: Editor,
        file: PsiFile,
        contextElement: PsiElement?,
        targetOffset: Int
    ): PsiElement? {
        val tokenType = contextElement?.node?.elementType ?: return null

        // Keyword hover
        if (KEYWORD_DOCS.containsKey(tokenType)) return contextElement

        // Hover over the name in `LOCAL/GLOBAL/PARAMETER USING <name>`
        if (tokenType == NaturalTypes.IDENTIFIER || tokenType == NaturalTypes.USER_VARIABLE) {
            val parent = contextElement.parent
            if (parent is NaturalDataAreaBlock &&
                NaturalDataAreaUtils.getUsingIdentifier(parent) == contextElement
            ) return contextElement
        }

        // Hover over the subprogram name in `CALLNAT 'NAME'` or `CALLNAT NAME`
        val isCallnatName = (tokenType == NaturalTypes.STRING_LITERAL ||
                tokenType == NaturalTypes.IDENTIFIER ||
                tokenType == NaturalTypes.USER_VARIABLE) &&
                contextElement.parent is NaturalCallnatStatement &&
                isPrecededBy(contextElement.node, NaturalTypes.KW_CALLNAT)
        if (isCallnatName) return contextElement

        // Hover over the copycode name in `INCLUDE <name>`
        val isIncludeName = (tokenType == NaturalTypes.IDENTIFIER ||
                tokenType == NaturalTypes.USER_VARIABLE) &&
                contextElement.parent is NaturalIncludeStatement &&
                isPrecededBy(contextElement.node, NaturalTypes.KW_INCLUDE)
        if (isIncludeName) return contextElement

        return null
    }

    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        val tokenType = element?.node?.elementType

        // Hover over the USING identifier — resolve to the data area file and show its docs
        if (tokenType == NaturalTypes.IDENTIFIER || tokenType == NaturalTypes.USER_VARIABLE) {
            val parent = element.parent
            if (parent is NaturalDataAreaBlock &&
                NaturalDataAreaUtils.getUsingIdentifier(parent) == element
            ) {
                val dataAreaFile = NaturalDataAreaUtils.resolveDataAreaFile(parent)
                    ?: return "<i>Could not resolve data area file.</i>"
                return NaturalDocUtils.renderDoc(dataAreaFile)
            }
        }

        // Hover over CALLNAT name — resolve to the subprogram file and show its docs
        if (tokenType == NaturalTypes.STRING_LITERAL ||
            tokenType == NaturalTypes.IDENTIFIER ||
            tokenType == NaturalTypes.USER_VARIABLE
        ) {
            val parent = element.parent
            if (parent is NaturalCallnatStatement &&
                isPrecededBy(element.node, NaturalTypes.KW_CALLNAT)
            ) {
                val name = element.text.trim().removeSurrounding("'").removeSurrounding("\"")
                val subprogramFile = NaturalDataAreaUtils.resolveSubprogram(name, element.project, element.containingFile)
                    ?: return "<i>Could not resolve subprogram '$name'.</i>"
                return NaturalDocUtils.renderDoc(subprogramFile)
            }
        }

        // Hover over INCLUDE name — resolve to the copycode file and show its docs
        if (tokenType == NaturalTypes.IDENTIFIER || tokenType == NaturalTypes.USER_VARIABLE) {
            val parent = element.parent
            if (parent is NaturalIncludeStatement &&
                isPrecededBy(element.node, NaturalTypes.KW_INCLUDE)
            ) {
                val copyCodeFile = NaturalDataAreaUtils.resolveCopyCode(element.text, element.project, element.containingFile)
                    ?: return "<i>Could not resolve copycode '${element.text}'.</i>"
                return NaturalDocUtils.renderDoc(copyCodeFile)
            }
        }

        // Keyword hover
        val kwType = element?.node?.elementType
            ?: originalElement?.node?.elementType
            ?: return null
        return KEYWORD_DOCS[kwType]?.let { (title, body) -> format(title, body) }
    }

    /** Returns true if the nearest non-whitespace sibling to the left of [node] has element type [kwType]. */
    private fun isPrecededBy(node: ASTNode, kwType: IElementType): Boolean {
        var prev = node.treePrev
        while (prev != null && prev.elementType == TokenType.WHITE_SPACE) prev = prev.treePrev
        return prev?.elementType == kwType
    }

    private fun format(title: String, body: String): String =
        "<b>$title</b><br><br>$body"

    companion object {
        private fun doc(title: String, body: String) = Pair(title, body)

        private val KEYWORD_DOCS: Map<IElementType, Pair<String, String>> = mapOf(
            // Control flow
            NaturalTypes.KW_IF to doc("IF", "Evaluates a condition and executes the following block if true. Must be closed with <b>END-IF</b>."),
            NaturalTypes.KW_THEN to doc("THEN", "Introduces the block of statements to execute when an <b>IF</b> condition is true."),
            NaturalTypes.KW_ELSE to doc("ELSE", "Introduces the alternative block of statements when an <b>IF</b> condition is false."),
            NaturalTypes.KW_END_IF to doc("END-IF", "Closes an <b>IF</b> statement block."),

            NaturalTypes.KW_FOR to doc("FOR", "Iterates over a range or database records. Close with <b>END-FOR</b>.<br><br><code>FOR #VAR := 1 TO 10</code>"),
            NaturalTypes.KW_END_FOR to doc("END-FOR", "Closes a <b>FOR</b> loop block."),

            NaturalTypes.KW_REPEAT to doc("REPEAT", "Starts an unconditional loop. Exit with <b>ESCAPE</b> or close with <b>END-REPEAT</b>."),
            NaturalTypes.KW_END_REPEAT to doc("END-REPEAT", "Closes a <b>REPEAT</b> loop block."),

            NaturalTypes.KW_WHILE to doc("WHILE", "Continues a <b>REPEAT</b> loop while the condition is true."),
            NaturalTypes.KW_UNTIL to doc("UNTIL", "Continues a <b>REPEAT</b> loop until the condition becomes true."),

            NaturalTypes.KW_DECIDE to doc("DECIDE", "Multi-way branch statement, similar to a switch/case. Close with <b>END-DECIDE</b>.<br><br>Use <b>DECIDE FOR FIRST CONDITION</b> or <b>DECIDE ON FIRST VALUE</b>."),
            NaturalTypes.KW_WHEN to doc("WHEN", "Defines a branch condition inside a <b>DECIDE</b> block."),
            NaturalTypes.KW_END_DECIDE to doc("END-DECIDE", "Closes a <b>DECIDE</b> block."),

            NaturalTypes.KW_ESCAPE to doc("ESCAPE", "Exits from a loop or processing block.<br><br>Common forms:<br>&nbsp;&bull; <b>ESCAPE TOP</b> — jump to the top of the current loop<br>&nbsp;&bull; <b>ESCAPE BOTTOM</b> — exit the current loop<br>&nbsp;&bull; <b>ESCAPE ROUTINE</b> — exit the current subroutine"),
            NaturalTypes.KW_LOOP to doc("LOOP", "Marks the end of a <b>READ</b> or <b>FIND</b> processing loop (alternative to <b>END-READ</b>/<b>END-FIND</b>)."),

            NaturalTypes.KW_FETCH to doc("FETCH", "Transfers control to another Natural program.<br><br><code>FETCH 'PROG-NAME'</code>"),
            NaturalTypes.KW_STOP to doc("STOP", "Terminates execution of the current Natural program and returns to the calling level."),
            NaturalTypes.KW_TERMINATE to doc("TERMINATE", "Terminates the entire Natural session."),

            // Data access
            NaturalTypes.KW_READ to doc("READ", "Reads records sequentially from a database file. Close with <b>END-READ</b>.<br><br><code>READ EMPLOYEES BY NAME</code>"),
            NaturalTypes.KW_END_READ to doc("END-READ", "Closes a <b>READ</b> loop block."),

            NaturalTypes.KW_FIND to doc("FIND", "Select a set of records from the database based on search criteria consisting of fields defined as descriptors (keys)."),
            NaturalTypes.KW_END_FIND to doc("END-FIND", "Closes a <b>FIND</b> loop block."),

            NaturalTypes.KW_STORE to doc("STORE", "Inserts a new record into a database file.<br><br><code>STORE EMPLOYEES</code>"),
            NaturalTypes.KW_UPDATE to doc("UPDATE", "Updates the current record in the database within a <b>READ</b> or <b>FIND</b> loop."),
            NaturalTypes.KW_DELETE to doc("DELETE", "Deletes the current record from the database within a <b>READ</b> or <b>FIND</b> loop."),
            NaturalTypes.KW_GET to doc("GET", "Retrieves a single record from a database file by its internal sequence number (ISN).<br><br><code>GET EMPLOYEES #ISN</code>"),
            NaturalTypes.KW_HISTOGRAM to doc("HISTOGRAM", "Reads descriptor values from a database file without retrieving full records. Useful for browsing index values.<br><br><code>HISTOGRAM EMPLOYEES NAME</code>"),

            // I/O
            NaturalTypes.KW_WRITE to doc("WRITE", "Writes output to a report. Advances to a new line after each statement.<br><br><code>WRITE 'Hello, World!'</code>"),
            NaturalTypes.KW_DISPLAY to doc("DISPLAY", "Formats and displays data in columns with automatic headings. Used for tabular report output."),
            NaturalTypes.KW_PRINT to doc("PRINT", "Writes output to a specific report (printer). Similar to <b>WRITE</b> but targets a named output destination."),
            NaturalTypes.KW_INPUT to doc("INPUT", "Displays a data-entry screen and accepts user input into variables.<br><br><code>INPUT #NAME #AGE</code>"),
            NaturalTypes.KW_REINPUT to doc("REINPUT", "Re-displays an <b>INPUT</b> screen with an error message, keeping the cursor at a specified field.<br><br><code>REINPUT 'Invalid value' MARK *#FIELD</code>"),
            NaturalTypes.KW_SKIP to doc("SKIP", "Skips a number of lines in the output report.<br><br><code>SKIP 2</code>"),
            NaturalTypes.KW_NEWPAGE to doc("NEWPAGE", "Starts a new page in the output report."),
            NaturalTypes.KW_EJECT to doc("EJECT", "Ejects to a new page on a physical printer."),
            NaturalTypes.KW_FORMAT to doc("FORMAT", "Sets global output formatting options for a report, such as line size, page size, and spacing."),

            // Data manipulation
            NaturalTypes.KW_MOVE to doc("MOVE", "Assigns a value to a variable. Supports format conversion.<br><br><code>MOVE 'Hello' TO #VAR</code><br><code>MOVE EDITED #NUM TO #STR</code>"),
            NaturalTypes.KW_ASSIGN to doc("ASSIGN", "Assigns a value or expression result to a variable. Equivalent to the <b>:=</b> operator.<br><br><code>ASSIGN #X = #A + #B</code>"),
            NaturalTypes.KW_COMPUTE to doc("COMPUTE", "Evaluates an arithmetic expression and assigns the result to a variable. Synonym of <b>ASSIGN</b>.<br><br><code>COMPUTE #TOTAL = #PRICE * #QTY</code>"),
            NaturalTypes.KW_ADD to doc("ADD", "Adds values together and stores the result.<br><br><code>ADD 1 TO #COUNTER</code><br><code>ADD #A #B GIVING #SUM</code>"),
            NaturalTypes.KW_SUBTRACT to doc("SUBTRACT", "Subtracts a value from a variable.<br><br><code>SUBTRACT 1 FROM #COUNTER</code>"),
            NaturalTypes.KW_MULTIPLY to doc("MULTIPLY", "Multiplies a variable by a value.<br><br><code>MULTIPLY #PRICE BY 1.1</code>"),
            NaturalTypes.KW_DIVIDE to doc("DIVIDE", "Divides a variable by a value.<br><br><code>DIVIDE 2 INTO #VALUE</code>"),
            NaturalTypes.KW_COMPRESS to doc("COMPRESS", "Concatenates multiple values into a single string variable, removing leading/trailing spaces.<br><br><code>COMPRESS #FIRST #LAST INTO #FULLNAME</code>"),
            NaturalTypes.KW_EXAMINE to doc("EXAMINE", "Searches for a pattern within a string and can replace, count, or delete occurrences.<br><br><code>EXAMINE #TEXT FOR 'foo' REPLACE WITH 'bar'</code>"),
            NaturalTypes.KW_SEPARATE to doc("SEPARATE", "Splits a string into multiple variables using a delimiter.<br><br><code>SEPARATE #CSV BY ',' INTO #A #B #C</code>"),
            NaturalTypes.KW_TRANSLATE to doc("TRANSLATE", "Converts a string to uppercase or lowercase, or substitutes characters.<br><br><code>TRANSLATE #NAME TO UPPER</code>"),
            NaturalTypes.KW_RESET to doc("RESET", "Resets variables to their initial (default) values.<br><br><code>RESET #COUNTER #TOTAL</code>"),
            NaturalTypes.KW_REDUCE to doc("REDUCE", "Reduces the size of a dynamic variable.<br><br><code>REDUCE #DYN TO 0</code>"),
            NaturalTypes.KW_EXPAND to doc("EXPAND", "Increases the allocated size of a dynamic variable.<br><br><code>EXPAND #DYN TO 1000</code>"),
            NaturalTypes.KW_RESIZE to doc("RESIZE", "Resizes a dynamic variable, preserving existing content.<br><br><code>RESIZE #DYN TO 512</code>"),
            NaturalTypes.KW_STACK to doc("STACK", "Pushes data onto the Natural stack to simulate keyboard input or pass data to the next program.<br><br><code>STACK TOP DATA 'INPUT-VALUE'</code>"),

            // Calls
            NaturalTypes.KW_CALLNAT to doc("CALLNAT", "Calls a Natural subprogram, passing parameters by reference.<br><br><code>CALLNAT 'SUBPROG' #PARAM1 #PARAM2</code>"),

            // DEFINE blocks
            NaturalTypes.KW_DEFINE_DATA to doc("DEFINE DATA", "Opens the data definition section of a program. Declares all variables used in the module. Must be closed with <b>END-DEFINE</b>."),
            NaturalTypes.KW_DEFINE to doc("DEFINE", "Begins a <b>DEFINE [SUBROUTINE] name</b> block. The keyword SUBROUTINE is optional. Close with <b>END-SUBROUTINE</b>.<br><br><code>DEFINE SUBROUTINE MY-ROUTINE<br>&nbsp;&nbsp;...<br>END-SUBROUTINE</code>"),
            NaturalTypes.KW_SUBROUTINE to doc("SUBROUTINE", "Optional keyword in <b>DEFINE [SUBROUTINE] name</b>. Defines a named subroutine invoked via <b>PERFORM</b>."),
            NaturalTypes.KW_DEFINE_FUNCTION to doc("DEFINE FUNCTION", "Defines a Natural function that returns a value. Close with <b>END-FUNCTION</b>."),
            NaturalTypes.KW_DEFINE_PRINTER to doc("DEFINE PRINTER", "Configures a named printer/report destination."),
            NaturalTypes.KW_DEFINE_WINDOW to doc("DEFINE WINDOW", "Defines a screen window for use with <b>INPUT</b> statements."),
            NaturalTypes.KW_DEFINE_WORK_FILE to doc("DEFINE WORK FILE", "Defines a work file (sequential file) for reading or writing.<br><br><code>DEFINE WORK FILE 1 'myfile.txt' TYPE 'ASCII'</code>"),
            NaturalTypes.KW_END_DEFINE to doc("END-DEFINE", "Closes a <b>DEFINE DATA</b> block."),
            NaturalTypes.KW_END_SUBROUTINE to doc("END-SUBROUTINE", "Closes a <b>DEFINE SUBROUTINE</b> block."),
            NaturalTypes.KW_END_FUNCTION to doc("END-FUNCTION", "Closes a <b>DEFINE FUNCTION</b> block."),
            NaturalTypes.KW_END to doc("END", "Marks the end of the main program body."),

            // Data definition modifiers
            NaturalTypes.KW_LOCAL to doc("LOCAL", "Declares variables with scope limited to the current program module. Used inside <b>DEFINE DATA</b>."),
            NaturalTypes.KW_GLOBAL to doc("GLOBAL", "References a Global Data Area (GDA) file. Variables are shared across all programs in the session.<br><br><code>GLOBAL USING MY-GDA</code>"),
            NaturalTypes.KW_PARAMETER to doc("PARAMETER", "Declares parameters passed to a subprogram or subroutine. Used inside <b>DEFINE DATA</b>."),
            NaturalTypes.KW_USING to doc("USING", "References an external data area (LDA or PDA) to include its variable declarations.<br><br><code>LOCAL USING MY-LDA</code>"),
            NaturalTypes.KW_REDEFINE to doc("REDEFINE", "Maps an alternative view (different data types) over an existing variable's memory.<br><br><code>1 #BYTES (A4)<br>REDEFINE #BYTES<br>&nbsp;&nbsp;2 #INT (I4)</code>"),
            NaturalTypes.KW_CONST to doc("CONST", "Declares a variable as a constant with an initial value that cannot be changed at runtime."),
            NaturalTypes.KW_DYNAMIC to doc("DYNAMIC", "Declares a variable with dynamically allocated length that grows as needed."),
            NaturalTypes.KW_INIT to doc("INIT", "Specifies an initial value for a variable in <b>DEFINE DATA</b>.<br><br><code>1 #COUNTER (I4) INIT &lt;0&gt;</code>"),

            // Transaction / error handling
            NaturalTypes.KW_ON_ERROR to doc("ON ERROR", "Defines an error-handling block that executes when a runtime error occurs. Close with <b>END-ERROR</b>."),
            NaturalTypes.KW_END_ERROR to doc("END-ERROR", "Closes an <b>ON ERROR</b> block."),
            NaturalTypes.KW_BACKOUT to doc("BACKOUT", "Rolls back all database changes made since the last <b>COMMIT</b> or program start.<br><br><code>BACKOUT TRANSACTION</code>"),
            NaturalTypes.KW_COMMIT to doc("COMMIT", "Permanently saves all database changes made since the last commit point.<br><br><code>COMMIT</code>"),
            NaturalTypes.KW_REJECT to doc("REJECT", "Signals that the current record in a <b>READ</b>/<b>FIND</b> loop should be excluded from processing (used with <b>AT BREAK</b>)."),
            NaturalTypes.KW_ACCEPT to doc("ACCEPT", "Signals that the current record in a <b>READ</b>/<b>FIND</b> loop should be accepted for processing (used with <b>AT BREAK</b>)."),
            NaturalTypes.KW_RETRY to doc("RETRY", "Within an <b>ON ERROR</b> block, retries the statement that caused the error."),
            NaturalTypes.KW_RELEASE to doc("RELEASE", "Releases a held ISN (database record lock) without updating or deleting the record."),

            // Clauses / modifiers
            NaturalTypes.KW_TO to doc("TO", "Specifies the target or upper bound in various statements.<br><br>&nbsp;&bull; <b>MOVE x TO #VAR</b><br>&nbsp;&bull; <b>FOR #I := 1 TO 10</b>"),
            NaturalTypes.KW_FROM to doc("FROM", "Specifies the source or starting point.<br><br>&nbsp;&bull; <b>SUBTRACT 5 FROM #VAR</b><br>&nbsp;&bull; <b>READ ... FROM ...</b>"),
            NaturalTypes.KW_BY to doc("BY", "Specifies the step increment in a <b>FOR</b> loop, or the search descriptor in <b>READ</b>/<b>FIND</b>.<br><br><code>FOR #I := 1 TO 100 BY 5</code>"),
            NaturalTypes.KW_THRU to doc("THRU", "Specifies the end of a range in <b>READ</b> or <b>FIND</b> statements (synonym of <b>TO</b> in range context).<br><br><code>READ EMPLOYEES BY NAME FROM 'A' THRU 'Z'</code>"),
            NaturalTypes.KW_WHERE to doc("WHERE", "Adds a filter condition to a <b>READ</b> or <b>FIND</b> loop that is evaluated for each record after retrieval."),
            NaturalTypes.KW_LIMIT to doc("LIMIT", "Restricts the maximum number of records processed in a <b>READ</b> or <b>FIND</b> loop.<br><br><code>FIND (100) EMPLOYEES WITH NAME = 'SMITH'</code>"),
            NaturalTypes.KW_WITH to doc("WITH", "Specifies the search criterion in a <b>FIND</b> statement.<br><br><code>FIND EMPLOYEES WITH NAME = 'SMITH'</code>"),
            NaturalTypes.KW_INTO to doc("INTO", "Specifies the target variable(s) for a <b>SEPARATE</b> or <b>COMPRESS</b> operation."),
            NaturalTypes.KW_GIVING to doc("GIVING", "Specifies a variable to receive a result, e.g., remainder in <b>DIVIDE</b> or match count in <b>EXAMINE</b>."),
            NaturalTypes.KW_ALL to doc("ALL", "Modifier meaning 'all occurrences'. Used in <b>EXAMINE</b>, <b>FIND ALL</b>, etc."),
            NaturalTypes.KW_FIRST to doc("FIRST", "Used in <b>DECIDE FOR FIRST CONDITION</b> — processes only the first matching branch."),
            NaturalTypes.KW_NONE to doc("NONE", "In a <b>DECIDE</b> block, the <b>WHEN NONE</b> branch executes when no other condition matches (default case)."),
            NaturalTypes.KW_ON to doc("ON", "Used in <b>DECIDE ON FIRST VALUE</b> — selects a branch based on a variable's value."),
            NaturalTypes.KW_AT to doc("AT", "Introduces break processing clauses within loops.<br><br>&nbsp;&bull; <b>AT BREAK OF #FIELD</b> — executes when a control field changes<br>&nbsp;&bull; <b>AT TOP OF PAGE</b> — executes at the top of each report page<br>&nbsp;&bull; <b>AT END OF DATA</b> — executes after all records are processed"),
            NaturalTypes.KW_BREAK to doc("BREAK", "Used in <b>AT BREAK OF #FIELD</b> to detect control breaks in sorted report output."),
            NaturalTypes.KW_BEFORE to doc("BEFORE", "Used in <b>AT BREAK ... BEFORE</b> to execute break logic before the triggering record is processed."),
            NaturalTypes.KW_TOP to doc("TOP", "Used in <b>AT TOP OF PAGE</b> for header processing, or <b>ESCAPE TOP</b> to jump to loop start."),
            NaturalTypes.KW_BOTTOM to doc("BOTTOM", "Used in <b>ESCAPE BOTTOM</b> to exit the current loop."),
            NaturalTypes.KW_STEP to doc("STEP", "Synonym for <b>BY</b> — specifies the increment in a <b>FOR</b> loop."),
            NaturalTypes.KW_IN to doc("IN", "Specifies the file/DDM name in certain database statements.<br><br><code>FIND EMPLOYEES IN STAFFDDM WITH ...</code>"),
            NaturalTypes.KW_OF to doc("OF", "Used in <b>AT BREAK OF #FIELD</b> and other clause constructions."),
            NaturalTypes.KW_VALUE to doc("VALUE", "Used in <b>DECIDE ON FIRST VALUE OF #VAR</b>."),
            NaturalTypes.KW_DATA to doc("DATA", "Part of <b>DEFINE DATA</b> — opens the variable declaration section."),
            NaturalTypes.KW_STARTING to doc("STARTING", "Specifies the starting value in a <b>READ</b> range.<br><br><code>READ EMPLOYEES BY NAME STARTING FROM 'S'</code>"),
            NaturalTypes.KW_ENDING to doc("ENDING", "Specifies the ending value in a <b>READ</b> range.<br><br><code>READ EMPLOYEES BY NAME ENDING AT 'T'</code>"),
            NaturalTypes.KW_PROCESSING to doc("PROCESSING", "Used in <b>AT BREAK ... BEFORE PROCESSING</b>."),
            NaturalTypes.KW_ROUNDED to doc("ROUNDED", "Modifier for arithmetic operations — rounds the result to the number of decimal places of the target variable."),
            NaturalTypes.KW_IGNORE to doc("IGNORE", "In a <b>DECIDE</b> block, <b>WHEN ANY IGNORE</b> means do nothing for the matching condition."),
            NaturalTypes.KW_REMAINDER to doc("REMAINDER", "In a <b>DIVIDE</b> statement, captures the remainder of the division.<br><br><code>DIVIDE 3 INTO #VAR REMAINDER #REM</code>"),
            NaturalTypes.KW_OPTIONS to doc("OPTIONS", "Specifies runtime session options.<br><br><code>OPTIONS REINPUT-WRITING-LOCK OFF</code>"),
            NaturalTypes.KW_EDITED to doc("EDITED", "In <b>MOVE EDITED</b>, applies an edit mask during the move operation."),
            NaturalTypes.KW_PHYSICAL to doc("PHYSICAL", "In <b>READ PHYSICAL</b>, reads records in physical (storage) sequence rather than by descriptor."),
            NaturalTypes.KW_SEQUENCE to doc("SEQUENCE", "Modifier in <b>READ ... IN SEQUENCE</b> or <b>HISTOGRAM ... IN SEQUENCE</b>."),
            NaturalTypes.KW_LOGICAL to doc("LOGICAL", "In <b>READ LOGICAL</b>, reads records in descriptor (index) sequence."),

            // End terminators
            NaturalTypes.KW_END_SORT to doc("END-SORT", "Closes a <b>SORT</b> block."),
            NaturalTypes.KW_END_BREAK to doc("END-BREAK", "Closes an <b>AT BREAK</b> block."),
            NaturalTypes.KW_END_ENDDATA to doc("END-ENDDATA", "Closes an <b>AT END OF DATA</b> block."),
            NaturalTypes.KW_END_ENDPAGE to doc("END-ENDPAGE", "Closes an <b>AT END OF PAGE</b> block."),
            NaturalTypes.KW_END_ALL to doc("END-ALL", "Closes an <b>AT START OF DATA</b> or similar block."),
            NaturalTypes.KW_END_BEFORE to doc("END-BEFORE", "Closes an <b>AT BREAK BEFORE</b> block."),
            NaturalTypes.KW_END_START to doc("END-START", "Closes an <b>AT START OF DATA</b> block."),
            NaturalTypes.KW_END_TOPPAGE to doc("END-TOPPAGE", "Closes an <b>AT TOP OF PAGE</b> block."),
            NaturalTypes.KW_END_TRANSACTION to doc("END-TRANSACTION", "Closes an <b>ON END OF TRANSACTION</b> block."),
            NaturalTypes.KW_END_NOREC to doc("END-NOREC", "Closes a <b>FIND ... RECORDS IN FILE</b> block for the no-records case."),

            // Logical operators
            NaturalTypes.KW_AND to doc("AND", "Logical AND operator. Combines conditions where both must be true.<br><br><code>IF #A = 1 AND #B = 2</code>"),
            NaturalTypes.KW_OR to doc("OR", "Logical OR operator. Combines conditions where at least one must be true.<br><br><code>IF #A = 1 OR #B = 2</code>"),
            NaturalTypes.KW_NOT to doc("NOT", "Logical NOT operator. Negates a condition.<br><br><code>IF NOT (#A = 1)</code>"),
            NaturalTypes.KW_EQUALS to doc("EQUALS", "Comparison keyword meaning equal to (=). Synonym: <b>EQ</b>.<br><br><code>IF #A EQUALS 1</code>"),
            NaturalTypes.KW_LESS to doc("LESS", "Comparison keyword — used in <b>LESS THAN</b> or <b>LESS EQUAL</b>.<br><br><code>IF #A LESS THAN 10</code>"),
            NaturalTypes.KW_GREATER to doc("GREATER", "Comparison keyword — used in <b>GREATER THAN</b> or <b>GREATER EQUAL</b>.<br><br><code>IF #A GREATER THAN 10</code>"),
            NaturalTypes.KW_THAN to doc("THAN", "Part of comparison keywords <b>LESS THAN</b> / <b>GREATER THAN</b>."),

            // System variables — application-related
            NaturalTypes.SV_APPLIC_ID to doc("*APPLIC-ID (A8)", "ID of the library to which the user is currently logged on."),
            NaturalTypes.SV_APPLIC_NAME to doc("*APPLIC-NAME (A32)", "Name of the library to which the user is currently logged on. Contains <b>SYSTEM</b> if Natural Security is not installed."),
            NaturalTypes.SV_COM to doc("*COM (A128)", "Communication area used for processing data outside active screen windows. Available in all programs in the same session."),
            NaturalTypes.SV_CONVID to doc("*CONVID (I4)", "Conversation ID for the current remote procedure call session."),
            NaturalTypes.SV_COUNTER to doc("*COUNTER (P10)", "Number of times a processing loop initiated by a FIND, READ, HISTOGRAM, or PARSE statement has been entered."),
            NaturalTypes.SV_CPU_TIME to doc("*CPU-TIME (I4)", "CPU time used by the current Natural process, measured in units of 10 milliseconds."),
            NaturalTypes.SV_CURRENT_UNIT to doc("*CURRENT-UNIT (A32)", "Name of the currently executing function, subroutine, or object."),
            NaturalTypes.SV_DATA to doc("*DATA (N3)", "Number of data elements in the Natural stack currently available for INPUT."),
            NaturalTypes.SV_ERROR_LINE to doc("*ERROR-LINE (N4)", "Source-code line number of the statement that caused the current error."),
            NaturalTypes.SV_ERROR_NR to doc("*ERROR-NR (N7)", "Error number that triggered the active ON ERROR condition."),
            NaturalTypes.SV_ERROR_TA to doc("*ERROR-TA (A8)", "Name of the error transaction program that will receive control when an error occurs."),
            NaturalTypes.SV_ETID to doc("*ETID (A8)", "Current transaction data identifier (ET ID) for Adabas."),
            NaturalTypes.SV_ISN to doc("*ISN (P10)", "Adabas internal sequence number (ISN) of the record currently being processed in a READ or FIND loop."),
            NaturalTypes.SV_LBOUND to doc("*LBOUND (I4)", "Lower boundary index value of the current array dimension."),
            NaturalTypes.SV_LENGTH to doc("*LENGTH (I4)", "Currently used length of a dynamic variable, in code units."),
            NaturalTypes.SV_LEVEL to doc("*LEVEL (N2)", "Nesting level of the currently executing program. Contains 99 if the level exceeds the maximum."),
            NaturalTypes.SV_LIBRARY_ID to doc("*LIBRARY-ID (A8)", "ID of the current library as set by the LOGON command."),
            NaturalTypes.SV_LINE to doc("*LINE (I4)", "Line number of the statement currently executing in the Natural object."),
            NaturalTypes.SV_LINEX to doc("*LINEX (A100)", "Line numbers across all nested INCLUDE levels, separated by slashes."),
            NaturalTypes.SV_LOAD_LIBRARY_ID to doc("*LOAD-LIBRARY-ID (A8)", "Library ID from which the currently executing object was loaded."),
            NaturalTypes.SV_NUMBER to doc("*NUMBER (P10)", "Number of records selected by FIND, values returned by HISTOGRAM, or LOB offset."),
            NaturalTypes.SV_OCCURRENCE to doc("*OCCURRENCE (I4)", "Current number of occurrences in an array field."),
            NaturalTypes.SV_PAGE_EVENT to doc("*PAGE-EVENT (U dynamic)", "Name of the current event from Natural for Ajax."),
            NaturalTypes.SV_PAGE_LEVEL to doc("*PAGE-LEVEL (I4)", "Nesting level of active PROCESS PAGE MODAL blocks."),
            NaturalTypes.SV_PROGRAM to doc("*PROGRAM (A8)", "Name of the Natural object currently executing."),
            NaturalTypes.SV_REINPUT_TYPE to doc("*REINPUT-TYPE (A16)", "Indicates whether REINPUT or PROCESS PAGE UPDATE is possible in the current context."),
            NaturalTypes.SV_ROWCOUNT to doc("*ROWCOUNT (I4)", "Number of rows deleted, updated, or inserted by the most recent SQL statement."),
            NaturalTypes.SV_STARTUP to doc("*STARTUP (A8)", "Name of the program to execute at the command prompt. Its content is modifiable."),
            NaturalTypes.SV_STEPLIB to doc("*STEPLIB (A8)", "Name of the steplib library concatenated to the current library for object lookup."),
            NaturalTypes.SV_SUBROUTINE to doc("*SUBROUTINE (A32)", "Name of the external subroutine currently executing."),
            NaturalTypes.SV_THIS_OBJECT to doc("*THIS-OBJECT (handle of object)", "Handle to the currently active object, or NULL-HANDLE if no object is active."),
            NaturalTypes.SV_TYPE to doc("*TYPE (A32)", "Type of the currently executing Natural object (e.g. PROGRAM, FUNCTION, SUBROUTINE)."),
            NaturalTypes.SV_UBOUND to doc("*UBOUND (I4)", "Upper boundary index value of the current array dimension."),

            // System variables — date/time
            NaturalTypes.SV_DATD to doc("*DATD (A8)", "Current date in <b>DD.MM.YY</b> format."),
            NaturalTypes.SV_DAT4D to doc("*DAT4D (A10)", "Current date in <b>DD.MM.YYYY</b> format (4-digit year)."),
            NaturalTypes.SV_DATE to doc("*DATE (A8)", "Current date in <b>DD/MM/YY</b> format."),
            NaturalTypes.SV_DAT4E to doc("*DAT4E (A10)", "Current date in <b>DD/MM/YYYY</b> format (4-digit year)."),
            NaturalTypes.SV_DATG to doc("*DATG (A15)", "Current date in full Gregorian format including the month name, e.g. <b>31 January 2025</b>."),
            NaturalTypes.SV_DATI to doc("*DATI (A8)", "Current date in <b>YY-MM-DD</b> format."),
            NaturalTypes.SV_DAT4I to doc("*DAT4I (A10)", "Current date in <b>YYYY-MM-DD</b> format (4-digit year, ISO 8601)."),
            NaturalTypes.SV_DATJ to doc("*DATJ (A5)", "Current date in <b>YYJJJ</b> Julian format."),
            NaturalTypes.SV_DAT4J to doc("*DAT4J (A7)", "Current date in <b>YYYYJJJ</b> Julian format (4-digit year)."),
            NaturalTypes.SV_DATN to doc("*DATN (N8)", "Current date as an 8-digit numeric value in <b>YYYYMMDD</b> format."),
            NaturalTypes.SV_DATU to doc("*DATU (A8)", "Current date in <b>MM/DD/YY</b> format (US format)."),
            NaturalTypes.SV_DAT4U to doc("*DAT4U (A10)", "Current date in <b>MM/DD/YYYY</b> format (US format, 4-digit year)."),
            NaturalTypes.SV_DATV to doc("*DATV (A11)", "Current date in <b>DD-MON-YYYY</b> format, e.g. <b>31-JAN-2025</b>."),
            NaturalTypes.SV_DATVS to doc("*DATVS (A9)", "Current date in <b>DDMONYYYY</b> format (no separators), e.g. <b>31JAN2025</b>."),
            NaturalTypes.SV_DATX to doc("*DATX (D)", "Current date in Natural internal date format. Equivalent to <code>D'today'</code>."),
            NaturalTypes.SV_TIMD to doc("*TIMD (N7)", "Elapsed time since the last SETTIME statement in <b>HHIISST</b> numeric format."),
            NaturalTypes.SV_TIME to doc("*TIME (A10)", "Current time of day in <b>HH:II:SS.T</b> format."),
            NaturalTypes.SV_TIME_OUT to doc("*TIME-OUT (N5)", "Number of seconds remaining before the current transaction times out."),
            NaturalTypes.SV_TIMESTMP to doc("*TIMESTMP (B8)", "Machine-internal store clock value in GMT (binary, 8 bytes). Used for high-precision timestamps."),
            NaturalTypes.SV_TIMN to doc("*TIMN (N7)", "Current time of day as a 7-digit numeric value in <b>HHIISST</b> format."),
            NaturalTypes.SV_TIMX to doc("*TIMX (T)", "Current time in Natural internal time format. Equivalent to <code>T'now'</code>."),

            // System variables — input/output
            NaturalTypes.SV_CURS_COL to doc("*CURS-COL (P3)", "Column number of the cursor's current position within the active window."),
            NaturalTypes.SV_CURS_FIELD to doc("*CURS-FIELD (I4)", "Internal field address identifying the field where the cursor is located. Used with the POS function."),
            NaturalTypes.SV_CURS_LINE to doc("*CURS-LINE (P3)", "Line number of the cursor's current position within the active window."),
            NaturalTypes.SV_CURSOR to doc("*CURSOR (N6)", "Cursor position on the input screen when ENTER or a function key is pressed. Legacy variable; prefer *CURS-LINE/*CURS-COL."),
            NaturalTypes.SV_LINE_COUNT to doc("*LINE-COUNT (P5)", "Current line number within the output page. Incremented by output statements such as WRITE and DISPLAY."),
            NaturalTypes.SV_LINESIZE to doc("*LINESIZE (N7)", "Physical line size of the I/O device from which Natural was invoked."),
            NaturalTypes.SV_LOG_LS to doc("*LOG-LS (N3)", "Logical line size of the primary report page."),
            NaturalTypes.SV_LOG_PS to doc("*LOG-PS (N3)", "Logical page size of the primary report page."),
            NaturalTypes.SV_PAGE_NUMBER to doc("*PAGE-NUMBER (P5)", "Current page number of the output report. Can be modified by the program."),
            NaturalTypes.SV_PAGESIZE to doc("*PAGESIZE (N7)", "Physical page size of the I/O device from which Natural was invoked."),
            NaturalTypes.SV_PF_KEY to doc("*PF-KEY (A4)", "Identification of the last key pressed, e.g. PF1–PF48, ENTR, CLR, PEN, PGDN, PGUP, PA1–PA3."),
            NaturalTypes.SV_PF_NAME to doc("*PF-NAME (A10)", "Name assigned to the last pressed function key via the NAMED clause of SET KEY."),
            NaturalTypes.SV_WINDOW_LS to doc("*WINDOW-LS (N3)", "Logical line size of the current window, excluding frame borders."),
            NaturalTypes.SV_WINDOW_POS to doc("*WINDOW-POS (N6)", "Position of the upper-left corner of the current window, counted in characters."),
            NaturalTypes.SV_WINDOW_PS to doc("*WINDOW-PS (N3)", "Logical page size of the current window, excluding frame borders."),

            // System variables — Natural environment
            NaturalTypes.SV_BROWSER_IO to doc("*BROWSER-IO (A8)", "Indicates whether the application is running in a web browser. Contains <b>WEB</b>, <b>RICHGUI</b>, or is empty for non-browser environments."),
            NaturalTypes.SV_DEVICE to doc("*DEVICE (A8)", "Device type or mode from which Natural was invoked, e.g. <b>BATCH</b>, <b>VIDEO</b>, <b>TTY</b>, <b>PC</b>."),
            NaturalTypes.SV_GROUP to doc("*GROUP (A8)", "Under Natural Security, contains the group ID or user ID used for library linkage."),
            NaturalTypes.SV_HARDCOPY to doc("*HARDCOPY (A8)", "Name of the hardcopy device set by the terminal command %H."),
            NaturalTypes.SV_INIT_USER to doc("*INIT-USER (A8)", "User ID of the current user. Content varies by platform and execution mode."),
            NaturalTypes.SV_LANGUAGE to doc("*LANGUAGE (I1)", "Language indicator code used for edit masks, error messages, and language-specific objects."),
            NaturalTypes.SV_NATVERS to doc("*NATVERS (A8)", "Natural version number, excluding cumulative fix information."),
            NaturalTypes.SV_NET_USER to doc("*NET-USER (A253)", "Network user name. Identical in value to *USER."),
            NaturalTypes.SV_PARM_USER to doc("*PARM-USER (A253)", "Name of the parameter module currently in use."),
            NaturalTypes.SV_PATCH_LEVEL to doc("*PATCH-LEVEL (A8)", "Current cumulative fix number applied to the Natural installation."),
            NaturalTypes.SV_PID to doc("*PID (A32)", "Unique session ID for the current Natural session."),
            NaturalTypes.SV_SCREEN_IO to doc("*SCREEN-IO (L)", "Boolean indicating whether screen I/O is possible in the current environment (<b>TRUE</b> or <b>FALSE</b>)."),
            NaturalTypes.SV_SERVER_TYPE to doc("*SERVER-TYPE (A32)", "Indicates the server type, e.g. <b>DB2-SP</b>, <b>DEVELOP</b>, <b>RPC</b>, <b>WEBIO</b>, or blank."),
            NaturalTypes.SV_UI to doc("*UI (A16)", "Indicates the user interface type: <b>CHARACTER</b> or <b>GUI</b>."),
            NaturalTypes.SV_USER to doc("*USER (A8)", "User ID of the currently logged-on user as set by Natural Security. Read-only."),
            NaturalTypes.SV_USER_NAME to doc("*USER-NAME (A32)", "Full name of the currently logged-on user. Contains <b>SYSTEM</b> if Natural Security is not active."),

            // System variables — system environment
            NaturalTypes.SV_CODEPAGE to doc("*CODEPAGE (A64)", "IANA name of the default code page used internally by Natural for conversions to and from Unicode."),
            NaturalTypes.SV_HARDWARE to doc("*HARDWARE (A16)", "Name of the hardware platform on which Natural is running."),
            NaturalTypes.SV_HOSTNAME to doc("*HOSTNAME (A64)", "Network host name of the machine where Natural is executing."),
            NaturalTypes.SV_INIT_ID to doc("*INIT-ID (A8)", "Terminal ID or session identifier, depending on the execution context (batch, interactive, server)."),
            NaturalTypes.SV_INIT_PROGRAM to doc("*INIT-PROGRAM (A8)", "Name of the program (transaction) that is currently executing as Natural."),
            NaturalTypes.SV_LOCALE to doc("*LOCALE (A8)", "Language and country of the current locale, which specifies the Unicode collation sequence."),
            NaturalTypes.SV_MACHINE_CLASS to doc("*MACHINE-CLASS (A16)", "Platform category: <b>MAINFRAME</b>, <b>PC</b>, <b>UNIX</b>, or <b>VMS</b>."),
            NaturalTypes.SV_OPSYS to doc("*OPSYS (A8)", "Natural's internal designation for the operating system. Legacy variable; prefer *OS, *HARDWARE, or *MACHINE-CLASS."),
            NaturalTypes.SV_OS to doc("*OS (A32)", "Name of the operating system under which Natural is running."),
            NaturalTypes.SV_OSVERS to doc("*OSVERS (A16)", "Version number of the operating system under which Natural is running."),
            NaturalTypes.SV_TP to doc("*TP (A8)", "Name of the TP (transaction processing) subsystem under which Natural is running."),
            NaturalTypes.SV_TPSYS to doc("*TPSYS (A8)", "TP monitor or environment name, e.g. <b>CICS</b>, <b>COMPLETE</b>, <b>IMS/DC</b>, <b>TSO</b>."),
            NaturalTypes.SV_TPVERS to doc("*TPVERS (A8)", "Version of the transaction processing subsystem in use."),
            NaturalTypes.SV_WINMGR to doc("*WINMGR (A16)", "Name of the window manager being used, if a graphical interface is active."),
            NaturalTypes.SV_WINMGRVERS to doc("*WINMGRVERS (A16)", "Version number of the window manager being used, if a graphical interface is active."),

            // System variables — XML
            NaturalTypes.SV_PARSE_COL to doc("*PARSE-COL (I4)", "Column in the XML source where the parser is currently positioned. Valid only inside a PARSE loop."),
            NaturalTypes.SV_PARSE_LEVEL to doc("*PARSE-LEVEL (I4)", "Nesting level of the currently parsed XML element. Valid only inside a PARSE loop."),
            NaturalTypes.SV_PARSE_NAMESPACE_URI to doc("*PARSE-NAMESPACE-URI (A dynamic)", "Namespace URI of the current XML element or attribute. Empty if no namespace applies. Valid only inside a PARSE loop."),
            NaturalTypes.SV_PARSE_ROW to doc("*PARSE-ROW (I4)", "Row in the XML source where the parser is currently positioned. Valid only inside a PARSE loop."),
            NaturalTypes.SV_PARSE_TYPE to doc("*PARSE-TYPE (A1)", "Type of data delivered by the current PARSE iteration: <b>?</b> (processing instruction), <b>!</b> (comment), <b>C</b> (CDATA), <b>T</b> (text), <b>@</b> (attribute), <b>/</b> (end tag), or <b>$</b> (start tag)."),

            // Data types
            NaturalTypes.DT_ALPHANUMERIC to doc("A — Alphanumeric", "Fixed-length alphanumeric (character) variable.<br><br><code>1 #NAME (A30)</code><br>Maximum length: 1,073,741,824 bytes."),
            NaturalTypes.DT_BINARY to doc("B — Binary", "Fixed-length binary (byte array) variable.<br><br><code>1 #DATA (B8)</code>"),
            NaturalTypes.DT_CV to doc("C — Dynamic Unicode String", "Dynamic variable-length Unicode/alphanumeric string (Code-Value).<br><br><code>1 #TEXT (C) DYNAMIC</code>"),
            NaturalTypes.DT_DATE to doc("D — Date", "Date variable storing year, month, and day.<br><br><code>1 #TODAY (D)</code><br>Literal format: <code>D'2025-01-31'</code>"),
            NaturalTypes.DT_FLOAT to doc("F — Float", "Floating-point numeric variable.<br><br><code>1 #VALUE (F8)</code><br>Sizes: F4 (single precision), F8 (double precision)."),
            NaturalTypes.DT_INT to doc("I — Integer", "Fixed-point integer variable.<br><br><code>1 #COUNTER (I4)</code><br>Sizes: I1 (−128..127), I2 (−32768..32767), I4 (±2B), I8 (±9×10¹⁸)."),
            NaturalTypes.DT_LOG to doc("L — Logical", "Boolean variable holding TRUE or FALSE.<br><br><code>1 #FLAG (L)</code>"),
            NaturalTypes.DT_NUMBER to doc("N — Numeric Unpacked", "Unpacked decimal numeric variable.<br><br><code>1 #AMOUNT (N7.2)</code><br>Format: total digits dot decimal digits."),
            NaturalTypes.DT_OBJECT to doc("O — Object Handle", "Object handle variable for Natural Business Services or NaturalX COM objects."),
            NaturalTypes.DT_PACK to doc("P — Packed Decimal", "Packed decimal numeric variable (BCD storage). More compact than N.<br><br><code>1 #TOTAL (P9.2)</code>"),
            NaturalTypes.DT_TIME to doc("T — Time", "Time variable storing hours, minutes, seconds, and tenths.<br><br><code>1 #NOW (T)</code><br>Literal format: <code>T'12:30:00'</code>"),
            NaturalTypes.DT_UNICODE to doc("U — Unicode", "Fixed-length Unicode (UTF-16) variable.<br><br><code>1 #LABEL (U20)</code>"),
        )
    }
}
