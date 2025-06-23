package plugins.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

@Suppress("unused")
class JacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply(JacocoPlugin::class.java)

            // Programmatically access the version catalog to get the jacoco version.
            // The 'libs' accessor is not available in precompiled script plugins.
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val jacocoVersion = libs.findVersion("jacoco").get().toString()

            extensions.configure<JacocoPluginExtension> {
                toolVersion = jacocoVersion
            }

            tasks.withType<Test> {
                finalizedBy(tasks.register<JacocoReport>("jacocoTestReportFor${name.replaceFirstChar { it.uppercase() }}") {
                    dependsOn(this@withType)
                    reports {
                        xml.required.set(true)
                        csv.required.set(false)
                        html.required.set(true)
                    }
                    classDirectories.setFrom(
                        files(
                            classDirectories.files.map {
                                fileTree(it) {
                                    exclude(
                                        "**/io/github/vchhabra/a2a_jvm/a2a_examples/**"
                                    )
                                }
                            }
                        )
                    )
                    sourceDirectories.setFrom(files("${project.projectDir}/src/main/java", "${project.projectDir}/src/main/kotlin"))
                    executionData.setFrom(file("${layout.buildDirectory.get()}/jacoco/${name}.exec"))
                })
            }
        }
    }
}
