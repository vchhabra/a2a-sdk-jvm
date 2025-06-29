package plugins.conventions

import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import java.util.Properties

class SpringBootAppConventionPluginTest : ConventionPluginTest() {

    @Test
    fun `applies spring conventions and configures a boot application`() {
        // 1. Arrange
        setupTestProject(buildKts = """
            plugins {
                id("spring-boot-app-convention")
            }
        """)

        createSourceFile("src/main/kotlin/com/example/Application.kt", """
            package com.example

            import org.springframework.boot.autoconfigure.SpringBootApplication
            import org.springframework.boot.runApplication

            @SpringBootApplication
            class Application

            fun main(args: Array<String>) {
                runApplication<Application>(*args)
            }
        """.trimIndent() + "\n")

        createSourceFile("src/test/kotlin/com/example/ApplicationTest.kt", """
            package com.example

            import org.junit.jupiter.api.Test
            import org.springframework.boot.test.context.SpringBootTest

            @SpringBootTest
            class ApplicationTest {
                @Test
                fun contextLoads() {
                    // This test verifies that the Spring application context can be loaded.
                }
            }
        """.trimIndent() + "\n")

        // 2. Act
        val result = runGradle("build")

        // 3. Assert
        assertEquals(TaskOutcome.SUCCESS, result.task(":build")?.outcome)
        assertEquals(TaskOutcome.SUCCESS, result.task(":test")?.outcome)
        assertEquals(TaskOutcome.SUCCESS, result.task(":detekt")?.outcome)
    }

    @Test
    fun `configures bootBuildImage task with conventional image name`() {
        // 1. Arrange
        setupTestProject(buildKts = """
            import ${BootBuildImage::class.qualifiedName}

            plugins {
                id("spring-boot-app-convention")
            }

            // Add a task to inspect the 'bootBuildImage' configuration
            tasks.register("printImageName") {
                doLast {
                    // Retrieve the configured task and print its imageName property
                    val imageName = tasks.named<BootBuildImage>("bootBuildImage").get().imageName
                    println(">>>DOCKER_IMAGE_NAME:${'$'}{imageName.get()}<<<")
                }
            }
        """)

        // 2. Act
        // Run our inspector task. The project's group and name are derived from
        // the test infrastructure provided by the ConventionPluginTest base class.
        val result = runGradle("printImageName")

        // 3. Assert
        // The convention plugin should set the image name to "<group>/<name>:<version>".
        // We retrieve the group, name, and version from the base class to ensure consistency.
        val props = Properties().apply { load(projectProperties.reader()) }
        val projectGroup = props.getProperty("projectGroupId")
        val projectVersion = props.getProperty("projectVersion")
        val expectedImageName = "$projectGroup/$testProjectName:$projectVersion"

        assertTrue(
            result.output.contains(">>>DOCKER_IMAGE_NAME:$expectedImageName<<<"),
            "The build output should contain the correctly configured Docker image name."
        )
        assertEquals(TaskOutcome.SUCCESS, result.task(":printImageName")?.outcome)
    }
}
