package com.moa.app.plugin

import com.moa.app.plugin.base.BasePlugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure

class KotlinJvmPlugin : BasePlugin() {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply {
            apply("java-library")
            apply("org.jetbrains.kotlin.jvm")
        }

        extensions.configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(17))
            }
        }
    }
}
