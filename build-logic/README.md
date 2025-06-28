# Build Logic

This module contains custom Gradle convention plugins that enforce consistent standards across all subprojects. Using convention plugins helps keep our build scripts DRY (Don't Repeat Yourself) and makes it easy to manage shared configurations like Kotlin versions, testing frameworks, and static analysis tools.

## How to Use

To apply a convention to a subproject, add the corresponding plugin ID to the `plugins { ... }` block of its `build.gradle.kts` file.

## Available Conventions

### `jvm-core-library-convention`

**Plugin ID:** `jvm-core-library-convention`

This is the base convention for any pure JVM library module that does not require framework-specific configuration. It is suitable for modules containing core business logic, data models, or utilities.

**What it does:**
*   Applies the `org.jetbrains.kotlin.jvm` plugin.
*   Configures the Kotlin JVM target to version 17.
*   Configures the JUnit 5 testing framework.
*   Integrates the JaCoCo plugin for test coverage reporting.
*   Applies our project-wide Detekt configuration for static code analysis.

**Example Usage:**
```kotlin
// in my-library-module/build.gradle.kts
plugins {
    id("jvm-core-library-convention")
}
```

### `jvm-spring-library-convention`

**Plugin ID:** `jvm-spring-library-convention`

This convention is for library modules that are part of a Spring ecosystem but are not executable applications themselves. It's ideal for modules containing shared services, components, or repositories.

**What it does:**
*   Applies all conventions from `jvm-core-library-convention`.
*   Applies the `kotlin-spring` plugin to automatically open Spring-annotated classes for proxying.
*   Imports the official Spring Boot dependency BOM (`spring-boot-dependencies`) to ensure consistent dependency versions.
*   Adds the `spring-boot-starter-test` dependency for testing Spring components.

**Example Usage:**
```kotlin
// in my-spring-library-module/build.gradle.kts
plugins {
    id("jvm-spring-library-convention")
}
```

### `spring-boot-app-convention`

**Plugin ID:** `spring-boot-app-convention`

This convention is designed specifically for executable Spring Boot application modules. It builds upon the `jvm-spring-library-convention` and adds the final configuration needed to create a runnable application.

**What it does:**
*   Applies all conventions from `jvm-spring-library-convention`.
*   Applies the official `org.springframework.boot` and `io.spring.dependency-management` plugins.
*   Configures the `bootJar` task to build an executable JAR and disables the standard `jar` task.

**Example Usage:**
```kotlin
// in my-app-module/build.gradle.kts
plugins {
    id("spring-boot-app-convention")
}
```

## Overriding Convention Settings

Some conventions expose a custom extension to allow for project-specific configuration.

### JaCoCo Coverage Threshold

All convention plugins that apply the `JacocoConventionPlugin` (including `jvm-core-library-convention`) give you access to the `jacocoConvention` extension block. You can use this to override the default test coverage threshold of 80%.

**Example Usage:**
```kotlin
import java.math.BigDecimal

// in my-library-module/build.gradle.kts
plugins {
    id("jvm-core-library-convention")
}

// Override the default JaCoCo coverage threshold for this module
jacocoConvention {
    coverageThreshold.set(BigDecimal("0.75"))
}
```
