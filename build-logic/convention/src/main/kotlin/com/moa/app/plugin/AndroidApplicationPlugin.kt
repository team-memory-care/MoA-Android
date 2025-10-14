package com.moa.app.plugin

import com.moa.app.plugin.base.AndroidBasePlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class AndroidApplicationPlugin : AndroidBasePlugin() {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
        }

        apply<AndroidComposePlugin>()
        apply<AndroidHiltPlugin>()
        configureAndroidBase()
    }
}
