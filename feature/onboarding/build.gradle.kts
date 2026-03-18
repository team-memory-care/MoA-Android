plugins {
    alias(libs.plugins.moa.android.library)
    alias(libs.plugins.moa.android.compose)
    alias(libs.plugins.moa.android.hilt)
}

android {
    namespace = "com.moa.app.feature.onboarding"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.navigation)
    implementation(projects.core.ui)
    implementation(projects.domain)

    implementation(libs.timber)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
