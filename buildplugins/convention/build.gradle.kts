plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.gradlePlugins.android)
    implementation(libs.gradlePlugins.kotlin)
}

gradlePlugin {
    plugins {
        register("javaLibrary") {
            id = "kjurkovic.java.library"
            implementationClass = "JavaLibraryConventionPlugin"
        }
        register("androidLibrary") {
            id = "kjurkovic.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "kjurkovic.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "kjurkovic.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "kjurkovic.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "kjurkovic.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("feature") {
            id = "kjurkovic.android.feature"
            implementationClass = "FeatureConventionPlugin"
        }
        register("api") {
            id = "kjurkovic.android.api"
            implementationClass = "ApiConventionPlugin"
        }
        register("repo") {
            id = "kjurkovic.android.repo"
            implementationClass = "RepoConventionPlugin"
        }
        register("datasource") {
            id = "kjurkovic.android.datasource"
            implementationClass = "DatasourceConventionPlugin"
        }
    }
}
