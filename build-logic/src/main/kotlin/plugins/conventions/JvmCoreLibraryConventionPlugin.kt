package plugins.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import plugins.conventions.BuildConstants

@Suppress("unused")
class JvmCoreLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            /*
             * Apply the essential plugins for a JVM library in this project.
             * Fix: Apply core Gradle plugins by their string ID. Do not use a version catalog alias.
             */
            pluginManager.apply("java-library")
            pluginManager.apply(BuildConstants.Plugins.KOTLIN_JVM)
            pluginManager.apply(BuildConstants.Plugins.ConventionPlugins.KTLINT_CONVENTION)
            pluginManager.apply(BuildConstants.Plugins.ConventionPlugins.JACOCO_CONVENTION)

        }
    }
}
