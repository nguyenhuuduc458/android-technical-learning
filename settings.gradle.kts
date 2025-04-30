pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        flatDir {
            dirs("libs") // Adds the 'libs' directory as a repository
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Note"
include(":app")
