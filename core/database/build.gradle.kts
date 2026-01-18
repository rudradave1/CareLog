plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // ✅ REQUIRED for Kotlin 2.0 + Compose
    alias(libs.plugins.kotlin.compose)
    // ✅ Correct kapt usage
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.rudra.database"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

// Annotation processor (Room)
    kapt(libs.androidx.room.compiler)

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

}