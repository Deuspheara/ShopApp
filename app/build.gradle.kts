import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.dagger.hilt)
}

val shopApiUrl: String = gradleLocalProperties(
    providers = project.providers,
    projectRootDir = project.rootDir
).getProperty("SHOP_API_URL")

android {
    namespace = "fr.deuspheara.eshopapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "fr.deuspheara.eshopapp"
        minSdk = 26
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
            buildConfigField("String", "SHOP_API_URL", shopApiUrl)
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "SHOP_API_URL", shopApiUrl)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in="
                    + "kotlinx.coroutines.ExperimentalCoroutinesApi,"
                    + "kotlin.contracts.ExperimentalContracts,"
                    + "kotlinx.coroutines.FlowPreview,"
                    + "androidx.compose.material3.ExperimentalMaterial3Api,"
                    + "androidx.compose.animation.ExperimentalAnimationApi,"
                    + "androidx.compose.ui.ExperimentalComposeUiApi,"
                    + "androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi,"
                    + "androidx.compose.foundation.ExperimentalFoundationApi,"
        )
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/gradle/incremental.annotation.processors"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
    buildToolsVersion = "34.0.0"
    ndkVersion = "25.2.9519653"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation(libs.androidx.compose.materialWindow)

    implementation(libs.bundles.paging)

    //region Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)
    androidTestImplementation(libs.bundles.compose.test)
    //endregion

    //region Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.testing)
    //endregion


    //region Retrofit
    implementation(libs.bundles.networking)
    //endregion

    //region Room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)
    androidTestImplementation(libs.room.testing)
    //endregion

    //region Datastore
    implementation(libs.bundles.datastore)
    //endregion

    //region Arrow
    implementation(libs.bundles.arrow)
    //endregion


    debugImplementation(libs.leak.canary)
}