plugins {
    id("kjurkovic.android.datasource")
}

dependencies {
    api(projects.data.repo)
    api(projects.networking.services)
    implementation(libs.realm)
}
