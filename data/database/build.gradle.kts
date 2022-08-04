
plugins {
    id("kjurkovic.android.library")
    id("kjurkovic.android.hilt")
    id("io.realm.kotlin")
}

dependencies {
    implementation(libs.realm)
    implementation(projects.data.common)
}
