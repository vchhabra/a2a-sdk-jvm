package plugins.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class JvmSpringLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            // Apply the base core library convention first.
            pluginManager.apply(BuildConstants.Plugins.ConventionPlugins.JVM_CORE_LIBRARY_CONVENTION)

            // Now, add the Spring-specific bits.
            val libs = extensions.getByType<VersionCatalogsExtension>().named(BuildConstants.LIBS_CATALOG)
            dependencies {
                add("api", platform(libs.findLibrary(BuildConstants.Libraries.SPRING_BOOT_DEPENDENCIES).get()))
                add("testImplementation", libs.findLibrary(BuildConstants.Libraries.SPRING_BOOT_STARTER_TEST).get())
            }
        }
    }
}
