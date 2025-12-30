package com.moa.app.plugin.base

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

abstract class BasePlugin : Plugin<Project> {

    protected val Project.libs: VersionCatalog
        get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

    abstract override fun apply(target: Project)

    protected fun Project.addLibraries(configuration: String, vararg libraryAliases: String) {
        dependencies {
            libraryAliases.forEach { alias ->
                add(
                    configurationName = configuration,
                    dependencyNotation = libs.findLibrary(alias).get(),
                )
            }
        }
    }

    protected fun Project.addBundles(configuration: String, vararg bundleAliases: String) {
        dependencies {
            bundleAliases.forEach { alias ->
                add(
                    configurationName = configuration,
                    dependencyNotation = libs.findBundle(alias).get()
                )
            }
        }
    }

    protected fun Project.addPlatform(configuration: String, platformAlias: String) {
        dependencies {
            add(
                configurationName = configuration,
                dependencyNotation = platform(libs.findLibrary(platformAlias).get())
            )
        }
    }
}
