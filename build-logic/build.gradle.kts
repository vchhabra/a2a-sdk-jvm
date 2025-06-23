import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import plugins.infrastructure.BuildConstantsGeneratorExtension

plugins {
    `kotlin-dsl`
    id("build-constants-generator")
}

gradlePlugin {
    plugins {
        // Register the framework-agnostic core library plugin.
        register("jvm-core-library-convention") {
            id = "jvm-core-library-convention"
            implementationClass = "plugins.conventions.JvmCoreLibraryConventionPlugin"
        }
        // Register the Spring-specific library plugin.
        register("jvm-spring-library-convention") {
            id = "jvm-spring-library-convention"
            implementationClass = "plugins.conventions.JvmSpringLibraryConventionPlugin"
        }
        // Consistent naming for the plugin registration
        register("spring-boot-app-convention") {
            id = "spring-boot-app-convention"
            implementationClass = "plugins.conventions.SpringBootAppConventionPlugin"
        }
        // Register the ktlint convention plugin
        register("ktlint-convention") {
            id = "ktlint-convention"
            implementationClass = "plugins.conventions.KtlintConventionPlugin"
        }
        // Register the jacoco convention plugin
        register("jacoco-convention") {
            id = "jacoco-convention"
            implementationClass = "plugins.conventions.JacocoConventionPlugin"
        }
    }
}

configure<BuildConstantsGeneratorExtension> {
    packageName.set("plugins.conventions")

    val conventionPlugins = project.extensions.getByType<GradlePluginDevelopmentExtension>()
    conventionPluginIds.set(conventionPlugins.plugins.map { it.id })

    val moduleNamesList = project.gradle.parent?.rootProject?.subprojects?.map { it.path } ?: emptyList()
    moduleNames.set(moduleNamesList)
}

dependencies {
    implementation(libs.gradle.plugin.spring.boot)
    implementation(libs.gradle.plugin.kotlin)
    implementation(libs.gradle.plugin.kotlin.spring)
    implementation(libs.detekt.gradle.plugin)
}
