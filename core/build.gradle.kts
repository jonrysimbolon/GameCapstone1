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


    /*implementation("com.android.tools.utp:android-test-plugin-host-retention:31.5.0")
    implementation("io.grpc:grpc-netty:1.57.0")
    implementation("io.netty:netty-handler:4.1.94.Final")
    implementation("io.netty:netty-codec-http2:4.1.100.Final")
    implementation("io.netty:netty-codec-http:4.1.108.Final")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.20")
    implementation("com.google.guava:guava:32.0.0-jre")*/
}