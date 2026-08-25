package com.appweb.natural.intellij.completion.keywordproviders

import com.appweb.natural.intellij.completion.isAfterEscapeDirection
import com.appweb.natural.intellij.completion.isAfterEscapeKeyword
import com.appweb.natural.intellij.completion.isInsideDefineDataBlock
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext

class NaturalKeywordCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        if (isInsideDefineDataBlock(parameters)) return
        if (isAfterEscapeKeyword(parameters) || isAfterEscapeDirection(parameters)) return
        result.addElement(LookupElementBuilder.create("ACCEPT"))
        result.addElement(LookupElementBuilder.create("ADD"))
        result.addElement(LookupElementBuilder.create("ASSIGN"))
        result.addElement(LookupElementBuilder.create("AT"))
        result.addElement(LookupElementBuilder.create("BACKOUT"))
        result.addElement(LookupElementBuilder.create("BROWSE"))
        result.addElement(LookupElementBuilder.create("CALL"))
        result.addElement(LookupElementBuilder.create("CALLDBPROC"))
        result.addElement(LookupElementBuilder.create("CALLNAT"))
        result.addElement(LookupElementBuilder.create("CLOSE"))
        result.addElement(LookupElementBuilder.create("COMMIT"))
        result.addElement(LookupElementBuilder.create("COMPOSE"))
        result.addElement(LookupElementBuilder.create("COMPRESS"))
        result.addElement(LookupElementBuilder.create("COMPUTE"))
        result.addElement(LookupElementBuilder.create("CREATE"))
        result.addElement(LookupElementBuilder.create("DECIDE"))
        result.addElement(LookupElementBuilder.create("DEFINE"))
        result.addElement(LookupElementBuilder.create("DELETE"))
        result.addElement(LookupElementBuilder.create("DISPLAY"))
        result.addElement(LookupElementBuilder.create("DIVIDE"))
        result.addElement(LookupElementBuilder.create("DOWNLOAD"))
        result.addElement(LookupElementBuilder.create("EJECT"))
        result.addElement(LookupElementBuilder.create("END"))
        result.addElement(LookupElementBuilder.create("ESCAPE"))
        result.addElement(LookupElementBuilder.create("EXAMINE"))
        result.addElement(LookupElementBuilder.create("EXPAND"))
        result.addElement(LookupElementBuilder.create("FETCH"))
        result.addElement(LookupElementBuilder.create("FIND"))
        result.addElement(LookupElementBuilder.create("FOR"))
        result.addElement(LookupElementBuilder.create("FORMAT"))
        result.addElement(LookupElementBuilder.create("GET"))
        result.addElement(LookupElementBuilder.create("HISTOGRAM"))
        result.addElement(LookupElementBuilder.create("IF"))
        result.addElement(LookupElementBuilder.create("IGNORE"))
        result.addElement(LookupElementBuilder.create("INCCONT"))
        result.addElement(LookupElementBuilder.create("INCLUDE"))
        result.addElement(LookupElementBuilder.create("INPUT"))
        result.addElement(LookupElementBuilder.create("INSERT"))
        result.addElement(LookupElementBuilder.create("LIMIT"))
        result.addElement(LookupElementBuilder.create("MAP"))
        result.addElement(LookupElementBuilder.create("MOVE"))
        result.addElement(LookupElementBuilder.create("MULTIPLY"))
        result.addElement(LookupElementBuilder.create("NEWPAGE"))
        result.addElement(LookupElementBuilder.create("ON"))
        result.addElement(LookupElementBuilder.create("OPEN"))
        result.addElement(LookupElementBuilder.create("OPTIONS"))
        result.addElement(LookupElementBuilder.create("PARSE"))
        result.addElement(LookupElementBuilder.create("PASSW"))
        result.addElement(LookupElementBuilder.create("PERFORM"))
        result.addElement(LookupElementBuilder.create("PRINT"))
        result.addElement(LookupElementBuilder.create("PROCESS"))
        result.addElement(LookupElementBuilder.create("READ"))
        result.addElement(LookupElementBuilder.create("READLOB"))
        result.addElement(LookupElementBuilder.create("REDEFINE"))
        result.addElement(LookupElementBuilder.create("REDUCE"))
        result.addElement(LookupElementBuilder.create("REINPUT"))
        result.addElement(LookupElementBuilder.create("REJECT"))
        result.addElement(LookupElementBuilder.create("RELEASE"))
        result.addElement(LookupElementBuilder.create("REPEAT"))
        result.addElement(LookupElementBuilder.create("REQUEST"))
        result.addElement(LookupElementBuilder.create("RESET"))
        result.addElement(LookupElementBuilder.create("RESIZE"))
        result.addElement(LookupElementBuilder.create("ROLLBACK"))
        result.addElement(LookupElementBuilder.create("RUN"))
        result.addElement(LookupElementBuilder.create("SELECT"))
        result.addElement(LookupElementBuilder.create("SEND"))
        result.addElement(LookupElementBuilder.create("SEPARATE"))
        result.addElement(LookupElementBuilder.create("SET"))
        result.addElement(LookupElementBuilder.create("SETTIME"))
        result.addElement(LookupElementBuilder.create("SKIP"))
        result.addElement(LookupElementBuilder.create("STACK"))
        result.addElement(LookupElementBuilder.create("STOP"))
        result.addElement(LookupElementBuilder.create("STORE"))
        result.addElement(LookupElementBuilder.create("SUBTRACT"))
        result.addElement(LookupElementBuilder.create("SUSPEND"))
        result.addElement(LookupElementBuilder.create("TERMINATE"))
        result.addElement(LookupElementBuilder.create("TOP"))
        result.addElement(LookupElementBuilder.create("UPDATE"))
        result.addElement(LookupElementBuilder.create("UPDATELOB"))
        result.addElement(LookupElementBuilder.create("UPLOAD"))
        result.addElement(LookupElementBuilder.create("WRITE"))
    }
}