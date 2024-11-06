plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.desafiotopdarkcliente"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.desafiotopdarkcliente"
        minSdk = 27
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Para usar RecyclerView
    //implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation(libs.androidx.recyclerview)
    //implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation(libs.androidx.recyclerview.selection)

    //Para usar las corrutinas
    //implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation(libs.kotlinx.coroutines.android)

    //Para usar Retrofit
    // implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation(libs.retrofit)
    // implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(libs.converter.gson)

    // Para usar lifecycle
    // implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // implementation("androidx.lifecyle:lifecycle-livedata-ktx:2.6.2")
    //implementation(libs.androidx.lifecycle.livedata.ktx)
   }