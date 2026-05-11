package com.moa.app.plugin

import com.android.build.api.dsl.CommonExtension
import com.moa.app.plugin.base.BasePlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

class AndroidComposePlugin : BasePlugin() {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

        extensions.configure<CommonExtension> {
            buildFeatures.compose = true
        }

        extensions.configure<ComposeCompilerGradlePluginExtension> {
            includeSourceInformation.set(true)
        }

        addPlatform("implementation", "compose.bom")
        addBundles("implementation", "compose")
        addBundles("debugImplementation", "compose.debug")
    }
}
