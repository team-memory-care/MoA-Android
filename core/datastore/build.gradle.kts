plugins {
    alias(libs.plugins.moa.android.library)
    alias(libs.plugins.moa.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.moa.app.datastore"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.datastore)
    implementation(libs.timber)
}
