plugins {
    id("kjurkovic.android.feature")
}

android {
    resourcePrefix = "albums"
}

dependencies {
    implementation(projects.data.repo)
}
