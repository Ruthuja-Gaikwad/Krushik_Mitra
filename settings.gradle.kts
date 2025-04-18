pluginManagement {
    repositories {
        google()                  // Required for plugins like Google Services
        mavenCentral()            // Central Maven repository
        gradlePluginPortal()      // Gradle Plugin Portal
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // Disallow project-level repos
    repositories {
        google()                 // Required for Firebase and Play Services
        mavenCentral()           // Maven dependencies
    }
}

rootProject.name = "Krushik Mitr"
include(":app")
