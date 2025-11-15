plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // KSP MATCHED TO KOTLIN 2.0.21
    id("com.google.devtools.ksp") version "2.0.21-1.0.25" apply false
}
