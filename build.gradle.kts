allprojects {
    repositories {
        google()
        mavenCentral()
        maven { setUrl ("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.gradlePlugins.android)
        classpath(libs.gradlePlugins.hilt)
        classpath(libs.gradlePlugins.kotlin)
        classpath(libs.gradlePlugins.realm)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}