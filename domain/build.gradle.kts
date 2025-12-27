plugins {
    alias(libs.plugins.moa.kotlin.jvm)
}

dependencies {
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.serialization.json)
}
