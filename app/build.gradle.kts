plugins {
    alias(libs.plugins.moa.android.application)
    alias(libs.plugins.moa.android.hilt)
}

android {
    namespace = "com.moa.app"

    defaultConfig {
        applicationId = "com.moa.app"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

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
    implementation(projects.feature.guardian)
}
