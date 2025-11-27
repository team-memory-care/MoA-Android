plugins {
    alias(libs.plugins.moa.android.library)
    alias(libs.plugins.moa.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.moa.app.data"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.domain)

    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.retrofit.bom))
    implementation(libs.bundles.retrofit)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
}
