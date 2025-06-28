pluginManagement {
    includeBuild("../build-infra")
}

// This allows the build-logic build to use the same version catalog
// and repository definitions as the main build.
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
