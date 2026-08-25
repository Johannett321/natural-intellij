package com.appweb.natural.intellij.run

import com.intellij.terminal.JBTerminalSystemSettingsProviderBase
import com.jediterm.terminal.TerminalColor

class NaturalTerminalSettingsProvider : JBTerminalSystemSettingsProviderBase() {
    override fun getDefaultBackground(): TerminalColor = TerminalColor.rgb(0xF5, 0xF5, 0xF5)
    override fun shouldFillCharacterBackgroundIncludingLineSpacing(): Boolean = true
}
