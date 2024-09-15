import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ktlint)
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

    // Read local properties file
    val localProps =
        Properties().apply {
            val localPropertiesFile = rootProject.file("local.properties")
            if (localPropertiesFile.exists()) {
                localPropertiesFile.inputStream().use { load(it) }
            }
        }

    // Access credentials
    val spotifyGrantType: String? = localProps.getProperty("spotify_grant_type")
    val spotifyClientId: String? = localProps.getProperty("spotify_client_id")
    val spotifyClientSecret: String? = localProps.getProperty("spotify_client_secret")

    defaultConfig {
        buildConfigField("String", "SPOTIFY_GRANT_TYPE", "\"${spotifyGrantType}\"")
        buildConfigField("String", "SPOTIFY_CLIENT_ID", "\"${spotifyClientId}\"")
        buildConfigField("String", "SPOTIFY_CLIENT_SECRET", "\"${spotifyClientSecret}\"")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
                filter {
                    excludeTestsMatching("com.example.note.database.*")
                }
                testLogging {
                    events =
                        setOf(
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    kotlin {
        jvmToolchain(17)
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
    implementation(libs.kotlin.gradle.plugin)

    // Android Core Library
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.test)
    androidTestImplementation(libs.androidx.test)
    androidTestImplementation(libs.androidx.junit.ktx)

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
    testImplementation(libs.robolectric.test)
    testImplementation(libs.androidx.junit.ktx)

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
    implementation(libs.logging.interceptor)

    // glide
    implementation(libs.glide)

    // spotify android sdk
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
//    implementation(libs.spotify.android.auth.sdk)
}

// Ktlint configuration
ktlint {
    version.set("1.3.1")
    debug.set(true)
    android.set(true)
    ignoreFailures.set(false)
    outputToConsole.set(true)
}

// =========================GRADLE TASK======================
tasks.register("renameApks") {
    val buildDirPath =
        layout.buildDirectory.asFile
            .get()
            .absolutePath
    val outputDir = "$buildDirPath/outputs/apk"

    doLast {
        val date = SimpleDateFormat("yyyy-MM-dd").format(Date())

        android.applicationVariants.all { variant ->
            val variantName = variant.name
            val versionName = variant.versionName
            val apkFilesDir = file("$outputDir/$variantName")
            var isRenameSuccess = false
            apkFilesDir.listFiles()?.forEach { apkFile ->
                if (apkFile.extension == "apk") {
                    val newFileName = "$variantName-$date-$versionName.apk"
                    val newFile = File(apkFilesDir, newFileName)
                    println("Renaming APK: ${apkFile.name} to ${newFile.name}")
                    isRenameSuccess = apkFile.renameTo(newFile)
                }
            }
            isRenameSuccess
        }
    }
}

tasks.register("printVersionName") {
    println(android.defaultConfig.versionName)
}
