package plugins.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.allopen.gradle.SpringGradleSubplugin
import plugins.extensions.libs


@Suppress("unused")
class JvmSpringLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            // Apply the base core library convention first.
            pluginManager.apply(JvmCoreLibraryConventionPlugin::class.java)
            // Apply the kotlin-spring plugin by its class. This is more robust
            // than applying by ID, as it doesn't require the consuming project
            // to have the plugin on its buildscript classpath.
            pluginManager.apply(SpringGradleSubplugin::class.java)

            dependencies {
                // By adding the Spring Boot BOM to the 'api' configuration, its dependency
                // constraints are made available to all consumers of this library, as well
                // as to the test configurations within the library itself. This ensures
                // consistent versions across the project.
                add("api", platform(libs.findLibrary("spring-boot-dependencies").get()))
                // Add the Spring Boot starter for testing.
                add("testImplementation", libs.findLibrary("spring-boot-starter-test").get())
            }
        }
    }
}
