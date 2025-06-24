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

rootProject.name = "a2a-jvm"
include("a2a-jvm-core")
include("a2a-jvm-transport-jsonrpc")
include("a2a-jvm-client")
include("a2a-jvm-server")
include("a2a-jvm-spring-boot-starter")
include("a2a-jvm-examples")
