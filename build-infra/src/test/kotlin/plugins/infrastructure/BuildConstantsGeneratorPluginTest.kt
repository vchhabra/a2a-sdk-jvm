package plugins.infrastructure

import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.io.File

class BuildConstantsGeneratorPluginTest {

    @Test
    fun `plugin registers task`() {
        // Create a test project and apply the plugin
        val project = ProjectBuilder.builder().build()

        // It's good practice to create a dummy versions.toml for tests
        // that rely on it, to avoid depending on the root project's file.
        val tomlFile = File(project.projectDir, "gradle/libs.versions.toml")
        tomlFile.parentFile.mkdirs()
        tomlFile.writeText("""
            [versions]
            junit = "5.10.0"

            [libraries]
            junit-jupiter-api = { module = "org.junit.jupiter:junit-jupiter-api", version.ref = "junit" }

            [plugins]
            jvm = { id = "org.jetbrains.kotlin.jvm", version = "1.9.22" }
        """.trimIndent())

        // Apply the plugin using its ID
        project.pluginManager.apply("build-constants-generator")

        // Configure the extension
        project.extensions.configure(BuildConstantsGeneratorExtension::class.java) {
            it.packageName.set("com.example.test")
        }

        // Find the task and verify it exists
        val task = project.tasks.findByName("generateBuildConstants")
        assertNotNull(task)
    }
}
