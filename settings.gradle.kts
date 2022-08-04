@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("buildplugins")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "AppleAlbums"

include(":app")

include(":common:framework")
include(":common:annotations")
include(":common:ui")
include(":common:wrappers:dispatchers")
include(":common:wrappers:logging")

include(":data:common")
include(":data:database")
include(":data:repo")
include(":data:datasource")

include(":networking:core")
include(":networking:services")

include(":features:albums")
