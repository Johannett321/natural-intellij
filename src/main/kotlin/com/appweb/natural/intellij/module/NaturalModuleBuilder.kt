package com.appweb.natural.intellij.module

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.projectRoots.SdkTypeId
import com.intellij.openapi.roots.ModifiableRootModel

class NaturalModuleBuilder : ModuleBuilder() {

    override fun getModuleType(): ModuleType<*> = NaturalModuleType.getInstance()

    override fun isSuitableSdkType(sdkType: SdkTypeId): Boolean = false

    override fun setupRootModel(modifiableRootModel: ModifiableRootModel) {
        val contentEntry = doAddContentEntry(modifiableRootModel) ?: return
        val rootDir = contentEntry.file ?: return
        contentEntry.addSourceFolder(rootDir, /* isTestSource = */ false)
    }
}
