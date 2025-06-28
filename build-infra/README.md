# Build Infrastructure (`build-infra`)

This module provides foundational plugins for the project's build system. It is designed to be a dependency for other build-logic modules (like the main `:build-logic` module), offering low-level utilities and breaking potential circular dependencies.

## Plugins

### `build-constants-generator`

This is a local plugin that generates a Kotlin file containing type-safe constants for various project elements, such as module paths and convention plugin IDs.

#### Purpose

In a multi-module Gradle project, build scripts often need to reference other modules (e.g., `project(":a2a-jvm-core")`) or convention plugins by their ID string (e.g., `"jvm-core-library-convention"`). Using raw strings is fragile and error-prone:
*   Typos are not caught by the compiler.
*   Renaming a module or plugin requires a manual "find and replace," which is unreliable.

This plugin solves these problems by generating a `BuildConstants.kt` file, allowing build scripts to use type-safe constants instead of strings.

#### Generated Code Example

The plugin generates an object structure like this, which becomes available on the classpath for all build scripts:

```kotlin
package plugins.conventions

object BuildConstants {
    object Modules {
        const val A2A_JVM_CORE = ":a2a-jvm-core"
        const val A2A_JVM_CLIENT = ":a2a-jvm-client"
        // ... and so on for all modules
    }

    object Plugins {
        const val JVM_CORE_LIBRARY_CONVENTION = "jvm-core-library-convention"
        const val JVM_SPRING_LIBRARY_CONVENTION = "jvm-spring-library-convention"
        // ... and so on for all convention plugins
    }
}
```

#### Usage in Build Scripts

With the generated constants, you can write much cleaner and safer build logic:

```kotlin
// Instead of: implementation(project(":a2a-jvm-core"))
implementation(project(BuildConstants.Modules.A2A_JVM_CORE))

// Instead of: plugins.apply("jvm-core-library-convention")
plugins.apply(BuildConstants.Plugins.JVM_CORE_LIBRARY_CONVENTION)
```

#### Configuration

The plugin is applied and configured within the `:build-logic` module's `build.gradle.kts` file. It automatically discovers the subproject modules and registered convention plugins to generate the constants.