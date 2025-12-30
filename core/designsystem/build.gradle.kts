plugins {
    alias(libs.plugins.moa.android.library)
    alias(libs.plugins.moa.android.compose)
}

android {
    namespace = "com.moa.app.designsystem"
}

dependencies {
    implementation(libs.kotlinx.collections.immutable)
}
