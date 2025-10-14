package com.moa.app.plugin

import com.moa.app.plugin.base.BasePlugin
import org.gradle.api.Project

class AndroidHiltPlugin : BasePlugin() {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply {
            apply("dagger.hilt.android.plugin")
            apply("com.google.devtools.ksp")
        }

        addLibraries("implementation", "hilt.android")
        addLibraries("ksp", "hilt.compiler")
    }
}
