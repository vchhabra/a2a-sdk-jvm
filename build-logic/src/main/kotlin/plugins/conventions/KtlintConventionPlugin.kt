package plugins.conventions

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

@Suppress("unused")
class KtlintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply(DetektPlugin::class)

            extensions.configure<DetektExtension> {
                buildUponDefaultConfig = true
                config.setFrom(files("$rootDir/tools/detekt/detekt.yml"))
            }

            tasks.withType<Detekt>().configureEach {
                reports {
                    xml.required.set(true)
                    html.required.set(true)
                    txt.required.set(true)
                    sarif.required.set(true)
                }
            }

            tasks.named("check").configure {
                dependsOn(tasks.withType<Detekt>())
            }
        }
    }
}
