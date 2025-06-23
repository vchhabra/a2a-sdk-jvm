plugins {
    `kotlin-dsl`
}

dependencies {
    // Add the Kotlin Gradle Plugin as a dependency so we can use its API
    implementation(libs.gradle.plugin.kotlin)

    // Use the consistent camelCase accessors for our test dependencies
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

gradlePlugin {
    plugins {
        register("build-constants-generator") {
            id = "build-constants-generator"
            implementationClass = "plugins.infrastructure.BuildConstantsGeneratorPlugin"
        }
    }
}

// Ensure the test task uses JUnit 5
tasks.withType<Test> {
    useJUnitPlatform()
}
