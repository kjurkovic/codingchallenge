package com.kjurkovic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

@Suppress("UnstableApiUsage")
fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = app.compileSdk

        defaultConfig {
            minSdk = app.minSdk
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
            isCoreLibraryDesugaringEnabled = true
        }

        kotlinOptions {
            // Treat all Kotlin warnings as errors (disabled by default)
            allWarningsAsErrors = properties["warningsAsErrors"] as? Boolean ?: false

            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xopt-in=kotlin.RequiresOptIn",
                // Enable experimental coroutines APIs, including Flow
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlinx.coroutines.FlowPreview",
                "-Xopt-in=kotlin.Experimental",
            )

            // Set JVM target to 11
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }

    val libs = extensions.getByType(VersionCatalogsExtension::class).named("libs")

    dependencies {
        add("coreLibraryDesugaring", libs.findDependency("android.desugarJdkLibs").get())
    }
}

private fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

/**
 * Configure Compose-specific options
 */
@Suppress("UnstableApiUsage")
fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("compose").get().toString()
        }
    }
}