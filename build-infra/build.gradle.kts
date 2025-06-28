import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Use 'kotlin-dsl' for a module that defines a Gradle plugin.
    `kotlin-dsl`
}

// Register the custom plugin provided by this module.
gradlePlugin {
    plugins {
        register("build-constants-generator") {
            id = "build-constants-generator"
            implementationClass = "plugins.infrastructure.BuildConstantsGeneratorPlugin"
        }
    }
}

// Use a different name for the catalog handle (than `libs`) to avoid shadowing.
val libsCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

// Configure the Java toolchain.
val javaVersion = libsCatalog.findVersion("java").get().toString().toInt()
extensions.configure<JavaPluginExtension> {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

// Configure test tasks.
tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
    // Adds the necessary Kotlin Gradle Plugin APIs to the classpath.
    implementation(kotlin("gradle-plugin"))

    // The 'kotlin-dsl' plugin includes testkit automatically, but being explicit is fine.
    testImplementation(gradleTestKit())

    testImplementation(platform(libs.junit.jupiter.bom))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.platform:junit-platform-launcher")
}
