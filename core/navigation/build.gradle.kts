plugins {
    alias(libs.plugins.moa.kotlin.jvm)
    alias(libs.plugins.moa.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
