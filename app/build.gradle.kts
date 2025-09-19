
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
}
val releaseSigningConfig = "release"
val tinyDictVersionName = "1.2.0"

android {
    namespace = "com.efe.tinydict"
    compileSdk = 36
    applicationVariants.all {
        outputs.all {
            val output = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            val appName = "TinyDict"
            output.outputFileName = "$appName-$versionName.apk"
        }
    }

    signingConfigs {
        create(releaseSigningConfig){
           storeFile = file("../keystore.jks")
            storePassword =  System.getenv("TINY_DICT_KEYSTORE_PASSWORD")
            keyAlias =  System.getenv("TINY_DICT_KEY_ALIAS")
            keyPassword =  System.getenv("TINY_DICT_KEYSTORE_PASSWORD")
        }
    }

    defaultConfig {
        applicationId = "com.efe.tinydict"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = tinyDictVersionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val dictionaryApiKey = "\"${System.getenv("DICT_API_KEY")}\""
        buildConfigField(type = "String", name = "API_KEY", value = dictionaryApiKey)
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs[releaseSigningConfig]
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Room dependencies
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //ktor and network
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.cio.engine)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.logging)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
