package com.moa.app.plugin.base

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

abstract class AndroidBasePlugin : BasePlugin() {
    protected fun Project.configureAndroidBase() {
        extensions.configure<CommonExtension> {
            compileSdk = libs.findVersion("compileSdk").get().requiredVersion.toInt()

            defaultConfig.apply {
                minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
            }

            compileOptions.apply {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            packaging.apply {
                resources.apply {
                    excludes.add("META-INF/AL2.0")
                    excludes.add("META-INF/LGPL2.1")
                    excludes.add("kotlin/reflect/*")
                }
            }
        }
    }
}
