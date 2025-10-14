plugins {
    alias(libs.plugins.moa.android.library)
}

android {
    namespace = "com.moa.app.data"
}

dependencies {
    implementation(projects.domain)
}
