package com.moa.app.plugin.base

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

abstract class AndroidBasePlugin : BasePlugin() {
    protected fun Project.configureAndroidBase() {
        extensions.getByType<BaseExtension>().apply {
            setCompileSdkVersion(libs.findVersion("compileSdk").get().requiredVersion.toInt())

            defaultConfig {
                minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
                targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            packagingOptions {
                resources {
                    excludes.add("META-INF/AL2.0")
                    excludes.add("META-INF/LGPL2.1")
                    excludes.add("kotlin/reflect/*")
                }
            }
        }

        extensions.getByType<KotlinAndroidProjectExtension>().apply {
            jvmToolchain(17)
        }
    }
}
