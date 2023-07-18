pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Ziuq"
include(":androidApp")
include(":shared")