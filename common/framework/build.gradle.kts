plugins {
    id("kjurkovic.android.library.compose")
    id("kjurkovic.android.hilt")
}

dependencies {
    implementation(libs.javax.inject)
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity.activityCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.compose.foundation)
    implementation(libs.hilt.navigationCompose)
    implementation(projects.common.wrappers.dispatchers)
    implementation(projects.data.common)

}