package plugins.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import plugins.extensions.libs

@Suppress("unused")
class SpringBootAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("jvm-spring-library-convention")
            pluginManager.apply(SpringBootPlugin::class.java)

            // Add the Spring Boot starter and actuator dependencies.
            dependencies {
                add("implementation", libs.findLibrary("spring-boot-starter").get())
                add("implementation", libs.findLibrary("spring-boot-starter-actuator").get())
            }

            // Configure the bootBuildImage task to generate a Docker image with a
            // conventional name and tag based on the project's group, name, and version.
            // Example: com.example/my-app:1.0.0
            tasks.named<BootBuildImage>("bootBuildImage") {
                imageName.set("${project.group}/${project.name}:${project.version}")
            }
        }
    }
}
