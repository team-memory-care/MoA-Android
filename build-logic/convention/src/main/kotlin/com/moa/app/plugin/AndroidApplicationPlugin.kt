package com.moa.app.plugin

import com.android.build.api.dsl.ApplicationExtension
import com.moa.app.plugin.base.AndroidBasePlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidApplicationPlugin : AndroidBasePlugin() {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply("com.android.application")

        apply<AndroidComposePlugin>()
        apply<AndroidHiltPlugin>()
        configureAndroidBase()

        extensions.configure<ApplicationExtension> {
            defaultConfig {
                targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
                versionCode = libs.findVersion("versionCode").get().requiredVersion.toInt()
                versionName = libs.findVersion("versionName").get().requiredVersion
            }
        }
    }
}
