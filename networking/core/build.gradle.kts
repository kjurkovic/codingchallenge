plugins {
    id("kjurkovic.android.library")
    id("kjurkovic.android.hilt")
}

dependencies {
    api(libs.retrofit.core)
    implementation(libs.moshi.core)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.retrofit.converterMoshi)
    implementation(libs.okhttp.core)
    implementation(projects.data.common)
    implementation(projects.common.wrappers.dispatchers)
    kapt(libs.moshi.kotlinCodegen)
}
