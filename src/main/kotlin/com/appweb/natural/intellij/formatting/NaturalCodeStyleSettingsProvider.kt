package com.appweb.natural.intellij.formatting

import com.appweb.natural.intellij.language.NaturalLanguage
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider

class NaturalLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {

    override fun getLanguage() = NaturalLanguage.INSTANCE

    override fun getCodeSample(settingsType: SettingsType) = ""

    override fun customizeDefaults(
        commonSettings: CommonCodeStyleSettings,
        indentOptions: CommonCodeStyleSettings.IndentOptions
    ) {
        commonSettings.RIGHT_MARGIN = 80
        // Apply to all Natural files including LightVirtualFile-backed ones (NDS-listed modules),
        // which fall back to these language defaults rather than picking up project settings.
        indentOptions.USE_TAB_CHARACTER = false
        indentOptions.INDENT_SIZE = 2
        indentOptions.TAB_SIZE = 2
        indentOptions.CONTINUATION_INDENT_SIZE = 2
    }
}
