plugins {
    `kotlin-dsl`
}

group = "com.moa.app.buildlogic"

kotlin {
    jvmToolchain(17)
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("android-application") {
            id = "moa.android.application"
            implementationClass = "com.moa.app.plugin.AndroidApplicationPlugin"
        }

        register("android-library") {
            id = "moa.android.library"
            implementationClass = "com.moa.app.plugin.AndroidLibraryPlugin"
        }

        register("android-compose") {
            id = "moa.android.compose"
            implementationClass = "com.moa.app.plugin.AndroidComposePlugin"
        }

        register("android-hilt") {
            id = "moa.android.hilt"
            implementationClass = "com.moa.app.plugin.AndroidHiltPlugin"
        }

        register("kotlin-jvm") {
            id = "moa.kotlin.jvm"
            implementationClass = "com.moa.app.plugin.KotlinJvmPlugin"
        }

        register("kotlin-serialization") {
            id = "moa.kotlin.serialization"
            implementationClass = "com.moa.app.plugin.KotlinSerializationPlugin"
        }
    }
}
