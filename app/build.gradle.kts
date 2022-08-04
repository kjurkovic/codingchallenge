import com.kjurkovic.app

plugins {
    id("kjurkovic.android.application.compose")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {

    defaultConfig {
        applicationId = app.applicationId

        versionCode = 1
        versionName = app.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isDebuggable = true
        }
    }

    flavorDimensions.add("version")
    productFlavors {
        create("dev") {
            applicationIdSuffix = ".dev"

            buildConfigField("String", "API_URL", "\"https://rss.applemarketingtools.com\"")
        }

        create("prod") {
            buildConfigField("String", "API_URL", "\"https://rss.applemarketingtools.com\"")
        }
    }
}

dependencies {

    coreLibraryDesugaring(libs.android.desugarJdkLibs)

    implementation(libs.androidx.core)
    implementation(libs.kotlin.core)
    implementation(libs.kotlin.reflect)
    implementation(libs.hilt.android)
    implementation(libs.bundles.compose)
    implementation(libs.androidx.activity.activityCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.savedState)
    implementation(libs.androidx.navigation.navigationCompose)

    implementation(projects.common.annotations)
    implementation(projects.common.framework)
    implementation(projects.common.ui)
    implementation(projects.common.wrappers.dispatchers)
    implementation(projects.common.wrappers.logging)

    implementation(projects.data.common)
    implementation(projects.data.database)
    implementation(projects.data.datasource)

    implementation(projects.features.albums)

    implementation(projects.networking.core)
    implementation(projects.networking.services)

    kapt(libs.hilt.compiler)
    kapt(libs.hilt.androidCompiler)
}