plugins {
    alias(libs.plugins.moa.android.library)
    alias(libs.plugins.moa.android.compose)
}

android {
    namespace = "com.moa.app.ui"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
}
