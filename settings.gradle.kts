pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

/*
 * It's important to include `build-infra` _before_ `build-logic`,
 * as `build-logic` depends on the plugin defined in `build-infra`.
 */
includeBuild("build-infra")
// This line enables the convention plugins from your build-logic directory
includeBuild("build-logic")

rootProject.name = "a2a-sdk-jvm"
include("a2a-core")
include("a2a-client")
include("a2a-server")
include("a2a-spring-boot-starter")
include("a2a-examples")
