package plugins.conventions

import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JvmSpringLibraryConventionPluginTest : ConventionPluginTest() {

    @Test
    fun `applies spring library conventions`() {
        // 1. Setup: Apply the convention plugin by its ID.
        setupTestProject(
            buildKts = """
                plugins {
                    id("jvm-spring-library-convention")
                }
            """
        )
        // Add dummy source files to allow the build to run successfully.
        createSourceFile("src/main/kotlin/com/example/Dummy.kt", DUMMY_KT_SOURCE)
        createSourceFile("src/test/kotlin/com/example/DummyTest.kt", DUMMY_TEST_KT_SOURCE)

        // 2. Execution
        val result = runGradle("build")

        // 3. Verification
        // A successful build implies that the core convention plugin was applied,
        // dependencies were resolved, and compilation was successful.
        assertEquals(TaskOutcome.SUCCESS, result.task(":build")?.outcome)
        assertEquals(TaskOutcome.SUCCESS, result.task(":compileKotlin")?.outcome)
        assertEquals(TaskOutcome.SUCCESS, result.task(":test")?.outcome)
    }
}
