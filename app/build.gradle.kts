plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.rickandmorty"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.rickandmorty"
        minSdk = 24
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
        compose = true
    }
    testOptions {
        unitTests {
            all {
                // Add jvmArgs here for unit tests
                it.jvmArgs("-XX:+EnableDynamicAgentLoading")
                it.testLogging {
                    events("passed", "failed", "skipped", "standardOut", "standardError")
                    showStandardStreams = true
                }
            }
        }
    }
    packagingOptions {
        resources {
            excludes += arrayOf("META-INF/LICENSE*", "META-INF/NOTICE*", "META-INF/DEPENDENCIES")
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(project(":network"))
    implementation(libs.coil.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.android.navigation)
    implementation(libs.androidx.material)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test.v173)
    testImplementation(libs.turbine)
    testImplementation(libs.mockito.core)
    androidTestImplementation("io.mockk:mockk-android:1.13.5")
    testImplementation(libs.mockitoKotlin)
    androidTestImplementation(libs.mockitoKotlin)
    testImplementation("org.mockito:mockito-inline:4.1.0")
    androidTestImplementation("org.mockito:mockito-inline:4.1.0")
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation("androidx.navigation:navigation-testing:2.8.6")
    // ...with Kotlin.
    kaptTest(libs.hilt.android.compiler)


    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.51.1")
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:2.51.1")

}
kapt {
    correctErrorTypes = true
}