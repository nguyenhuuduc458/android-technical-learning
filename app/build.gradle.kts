
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.note"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.note"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Always show the result of every unit test, even if it passes.
    testOptions.unitTests {
        isIncludeAndroidResources = true
        beforeEvaluate {
            println(">> Running unit test:")
        }
        all { test ->
            with(test) {
                testLogging {
                    events = setOf(
                        org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                        org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
                        org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                        org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT,
                        org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR,
                    )
                }
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // App dependencies
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)

    // Android Core Library
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    // Local unit tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.constraintlayout.compose)
    testImplementation(libs.google.truth)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.koin.androidx.test)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.junit.test)
    testImplementation(libs.mockk.lib)
    androidTestImplementation(libs.android.mockk)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.complier)
    implementation(libs.room.ktx)

    // koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android.compat)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.navigation)
    implementation(libs.koin.androidx.workmanager)
    implementation(libs.koin.androidx.test)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // glide
    implementation(libs.glide)
}

//sourceSets {
//    val test by getting {
//        java.srcDirs("src/test/java")
//    }
//}

tasks.register<Test>("runUnitTests") {
    description = "Runs all unit tests with detailed output"
    group = "verification"

    // Use the default test sources and classpath
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    // Configure logging to show detailed test output in the console
    testLogging {
        events("PASSED", "FAILED", "SKIPPED")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL // Show full stack traces
        showStandardStreams = true  // Print standard output and error streams
    }

    // Optionally: set to stop execution on the first test failure (useful for debugging)
    failFast = false
}