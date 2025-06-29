package plugins.conventions

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import plugins.extensions.libs

@Suppress("unused")
class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            plugins.withId("org.jetbrains.kotlin.jvm") {
                pluginManager.apply(DetektPlugin::class.java)

                dependencies {
                    libs.findLibrary("detekt-formatting").ifPresent {
                        add("detektPlugins", it)
                    }
                }

                extensions.configure<DetektExtension> {
                    buildUponDefaultConfig = true

                    val detektConfigFile = file("tools/detekt/detekt.yml")
                    if (detektConfigFile.exists()) {
                        config.setFrom(detektConfigFile)
                    }

                    // The modern, lazy-evaluated `.set()` and `.value()` methods for wiring Gradle
                    // properties fail to compile due to a binary incompatibility between the Kotlin
                    // version used in the build script (2.x) and the Detekt plugin version (1.23.x).
                    // To work around this, we use direct, imperative property assignment. This is
                    // a temporary measure until a stable version of Detekt compatible with
                    // Kotlin 2.x is available.
                    if (project.hasProperty("detekt.autoCorrect")) {
                        autoCorrect = project.property("detekt.autoCorrect").toString().toBoolean()
                    }
                }

                tasks.withType<Detekt>().configureEach {
                    reports {
                        xml.required.set(true)
                        html.required.set(true)
                        txt.required.set(false)
                        sarif.required.set(false)
                    }
                }
            }
        }
    }
}
