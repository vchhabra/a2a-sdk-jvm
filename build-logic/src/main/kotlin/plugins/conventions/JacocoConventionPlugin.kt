package plugins.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport
import plugins.extensions.JacocoConventionExtension
import plugins.extensions.libs
import java.math.BigDecimal

@Suppress("unused")
class JacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            // Create the custom extension to allow for per-project configuration.
            val jacocoConventionExtension = extensions.create(
                "jacocoConvention",
                JacocoConventionExtension::class.java
            )

            pluginManager.apply(JacocoPlugin::class.java)

            extensions.configure<JacocoPluginExtension> {
                toolVersion = libs.findVersion("jacoco").get().toString()
            }

            tasks.named<JacocoReport>("jacocoTestReport") {
                dependsOn(tasks.withType<Test>())
                reports {
                    xml.required.set(true)
                    html.required.set(true)
                }
            }

            tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
                violationRules {
                    rule {
                        element = "CLASS"
                        excludes = listOf("*Application", "*ApplicationKt")

                        limit {
                            counter = "LINE"
                            value = "COVEREDRATIO"
                            // Read the threshold from our custom extension property.
                            // If not set by a project, it will use the default from the extension.
                            minimum = jacocoConventionExtension.coverageThreshold.get()
                        }
                    }
                }
            }
        }
    }
}
