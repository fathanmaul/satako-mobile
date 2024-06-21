plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id ("androidx.navigation.safeargs")
    id("kotlin-parcelize")
}

android {
    namespace = "dev.capstone.satako_mobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.capstone.satako_mobile"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"${project.findProperty("BASE_URL")}\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    //flexbox
    implementation (libs.flexbox)

    //glide
    implementation(libs.glide)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //datastore
    implementation(libs.androidx.datastore.preferences)


    // Espresso core
    androidTestImplementation (libs.androidx.espresso.core)
    androidTestImplementation (libs.androidx.espresso.intents)
    androidTestImplementation (libs.androidx.espresso.contrib)
    implementation(libs.androidx.espresso.idling.resource)

    // JUnit
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit)

    // Fragment testing
    debugImplementation (libs.androidx.fragment.testing)

    // AndroidX Test - Core library
    androidTestImplementation (libs.androidx.core)
    androidTestImplementation (libs.androidx.rules)
    androidTestImplementation (libs.androidx.runner)

    //ucrop
    implementation (libs.ucrop)

    //cameraX
    implementation (libs.androidx.camera.core)
    implementation (libs.androidx.camera.camera2)
    implementation (libs.androidx.camera.lifecycle)
    implementation (libs.androidx.camera.video)

    implementation (libs.androidx.camera.view)
    implementation (libs.androidx.camera.extensions)

    // Splash API
    implementation (libs.androidx.core.splashscreen)
}