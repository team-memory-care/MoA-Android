import java.util.Properties

plugins {
    alias(libs.plugins.moa.android.application)
    alias(libs.plugins.moa.android.hilt)
}

android {
    namespace = "com.moa.app"

    defaultConfig {
        applicationId = "com.biggun.moa"
    }

    val localProperties = Properties().apply {
        val propFile = rootProject.file("local.properties")
        if (propFile.exists()) {
            propFile.inputStream().use(::load)
        }
    }

    val gradleProperties = Properties().apply {
        val propFile = rootProject.file("gradle.properties")
        if (propFile.exists()) {
            propFile.inputStream().use(::load)
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file(
                gradleProperties["RELEASE_STORE_FILE"] as? String
                    ?: System.getenv("RELEASE_STORE_FILE")
                    ?: localProperties["release.keystore.path"] as? String
                    ?: "${rootProject.projectDir}/app/keystore/release.jks",
            )
            storePassword = gradleProperties["RELEASE_STORE_PASSWORD"] as? String
                ?: System.getenv("RELEASE_STORE_PASSWORD")
                ?: localProperties["release.keystore.password"] as? String
            keyAlias = gradleProperties["RELEASE_KEY_ALIAS"] as? String
                ?: System.getenv("RELEASE_KEY_ALIAS")
                ?: localProperties["release.key.alias"] as? String
            keyPassword = gradleProperties["RELEASE_KEY_PASSWORD"] as? String
                ?: System.getenv("RELEASE_KEY_PASSWORD")
                ?: localProperties["release.key.password"] as? String
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.browser)

    implementation(libs.timber)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)

    implementation(projects.core.datastore)
    implementation(projects.core.designsystem)
    implementation(projects.core.navigation)
    implementation(projects.core.network)
    implementation(projects.domain)
    implementation(projects.data)
    implementation(projects.feature.onboarding)
    implementation(projects.feature.senior)
    implementation(projects.feature.report)
    implementation(projects.feature.guardian)
}
