package com.moa.app.plugin

import com.android.build.gradle.BaseExtension
import com.moa.app.plugin.base.BasePlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

class AndroidComposePlugin : BasePlugin() {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply {
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        extensions.getByType<BaseExtension>().apply {
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
