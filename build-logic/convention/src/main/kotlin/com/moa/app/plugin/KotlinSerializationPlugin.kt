package com.moa.app.plugin

import com.moa.app.plugin.base.BasePlugin
import org.gradle.api.Project

class KotlinSerializationPlugin : BasePlugin() {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply {
            apply("kotlin.serialization.json")
        }

        addLibraries("implementation", "kotlinx.serialization.json")
    }
}
