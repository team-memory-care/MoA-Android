package com.moa.app.plugin

import com.moa.app.plugin.base.AndroidBasePlugin
import org.gradle.api.Project

class AndroidLibraryPlugin : AndroidBasePlugin() {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }

        configureAndroidBase()
    }
}
