plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.watchlist"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.watchlist"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

kotlin {
    sourceSets {
        main {
            kotlin.srcDir("src/main/kotlin") //redundant, but good to be explicit
            resources.srcDir("src/main/resources") //redundant, but good to be explicit
        }
        test {
            kotlin.srcDir("src/test/kotlin")  //redundant, but good to be explicit
            resources.srcDir("src/test/resources") //redundant, but good to be explicit
        }
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
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // added by Dalmatia
    // Room Database
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler.v261)
    implementation(libs.androidx.room.ktx)
    // Bcrypt (jBCrypt)
    implementation(libs.jbcrypt)
    // Biometrics
    implementation(libs.androidx.biometric)
}

dependencies {
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.runtime.lint)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.lifecycle.viewmodelcompose)
    kapt(libs.room.compiler)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.vanniktech.android.image.cropper)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.coil.compose)
    implementation(libs.android.youtube.player.core)
}

kapt {
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}