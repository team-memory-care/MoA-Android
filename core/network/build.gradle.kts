import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.moa.android.library)
    alias(libs.plugins.moa.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.moa.app.network"

    val localProperties = Properties().apply {
        val propFile = rootProject.file("local.properties")
        if (propFile.exists()) {
            propFile.inputStream().use(::load)
        }
    }

    buildTypes {
        debug {
            val devUrl = localProperties["moa.dev.url"] as? String
            buildConfigField("String", "BASE_URL", "\"$devUrl\"")
        }

        release {
            val prodUrl = localProperties["moa.prod.url"] as? String
            buildConfigField("String", "BASE_URL", "\"$prodUrl\"")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true

            all {
                it.useJUnitPlatform()
            }
        }
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.retrofit.bom))
    implementation(libs.bundles.retrofit)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)

    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.assertj.core)

    testImplementation(libs.bundles.retrofit)
    testImplementation(libs.bundles.okhttp)
}
