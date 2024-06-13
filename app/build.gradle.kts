plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id ("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
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


    //testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //ucrop
    implementation (libs.ucrop)

    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    // Add the dependency for the Firebase SDK for Google Analytics
    implementation(libs.google.firebase.analytics)
    // Add the dependency for the Firebase Authentication library
    implementation(libs.google.firebase.auth)
    // Add the dependency for the Google Play services library
    implementation(libs.play.services.auth)
    // credential manager
    implementation(libs.androidx.credentials.v122)
    implementation(libs.androidx.credentials.play.services.auth.v122)
    implementation (libs.google.googleid)

    //cameraX
    implementation (libs.androidx.camera.core)
    implementation (libs.androidx.camera.camera2)
    implementation (libs.androidx.camera.lifecycle)
    implementation (libs.androidx.camera.video)

    implementation (libs.androidx.camera.view)
    implementation (libs.androidx.camera.extensions)

    // Custom Button Sign In
    implementation (libs.custom.google.signin.button)
}