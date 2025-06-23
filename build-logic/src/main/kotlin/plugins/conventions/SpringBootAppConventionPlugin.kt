package plugins.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class SpringBootAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            // Apply the Spring library convention as a base.
            pluginManager.apply(BuildConstants.Plugins.ConventionPlugins.JVM_SPRING_LIBRARY_CONVENTION)

            // Apply the specific plugins for a runnable Spring Boot application.
            pluginManager.apply(BuildConstants.Plugins.SPRING_BOOT)
            pluginManager.apply(BuildConstants.Plugins.KOTLIN_SPRING)
        }
    }
}
