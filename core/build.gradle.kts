plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "id.zoneordering.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf("-opt-in=kotlin.RequiresOptIn")
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    // api
    api(libs.androidx.appcompat)
    api(libs.coreKtx)
    api(libs.androidx.constraintlayout)
    api(libs.legacySupport)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    api(libs.material)
    api(libs.glide)

    ////koin
    api(libs.koinCore)
    api(libs.koinAndroid)

    api(libs.androidx.appcompat)

    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    androidTestImplementation(libs.room.test)

    api(libs.retrofit)
    api(libs.retrofit.converter)
    api(libs.logging.interceptor)

    api(libs.kotlinx.coroutine.core)
    api(libs.kotlinx.coroutine.android)
    api(libs.lifecycle.livedata.ktx)

    api(libs.androidx.datastore.preferences)
}