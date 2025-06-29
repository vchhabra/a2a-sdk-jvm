plugins {
    // This module is pure and framework-agnostic.
    alias(libs.plugins.jvm.core.library.convention)
}

dependencies {
    // --- Main Source Dependencies ---
    // No additional dependencies needed for our pure core module.
}

dependencies {
    // --- Test Source Dependencies ---
    // We declare these dependencies under 'testImplementation' so they are
    // only available during test compilation and execution, and are not
    // included as part of the final library artifact (JAR).

    // Import the Spring Boot BOM to manage test dependency versions. This is
    // scoped to 'testImplementation' so it does not affect downstream consumers.
    testImplementation(platform(libs.spring.boot.dependencies))

    // This starter transitively brings in JUnit 5, AssertJ, Mockito, and other essential testing utilities.
    testImplementation(libs.spring.boot.starter.test)

    // Explicitly add the JUnit Platform Launcher to align its version with the spring BOM.
    // This is the key to resolving the "unaligned versions" error.
    testImplementation("org.junit.platform:junit-platform-launcher")

    // We need Jackson specifically to test our JSON serialization/deserialization logic.
    // The version is managed by the Spring Boot BOM.
    testImplementation(libs.jackson.databind)
}
