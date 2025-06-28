### **ADR-0001: Project Structure and Gradle Build Strategy**

**Status:** Active (2025-06-28)

**Context:**

As the `a2a-jvm` project grows, we need a build system that is scalable, maintainable, and consistent across all modules. Without a clear strategy, we risk facing common issues like:
- Duplicated build logic across `build.gradle.kts` files.
- Inconsistent dependency versions, leading to potential classpath conflicts.
- Difficulty enforcing project-wide standards like code quality checks.
- A steep learning curve for new contributors trying to understand the build.

The goal is to establish a "Don't Repeat Yourself" (DRY) approach for our build logic that promotes consistency and simplifies maintenance.

**Decision:**

We have adopted a modern Gradle architecture that leverages **Composite Builds**, **Convention Plugins**, and a **Version Catalog**. This structure separates concerns, centralizes configuration, and provides a type-safe way to manage our build.

Here is a breakdown of the key components:

**1. Multi-Module Project Structure**

The project is organized into several distinct modules, as defined in `settings.gradle.kts`:
- `:a2a-jvm-core`: Core logic and data models, with minimal dependencies.
- `:a2a-jvm-transport-jsonrpc`: Data contracts specific to the JSON-RPC transport layer.
- `:a2a-jvm-client`: Code specific to the client-side implementation of the SDK.
- `:a2a-jvm-server`: Code specific to the server-side implementation of the SDK.
- `:a2a-jvm-spring-boot-starter`: A starter to simplify integration with the Spring Boot ecosystem.
- `:a2a-jvm-examples`: An example application demonstrating how to use the SDK.

*Rationale:* This modular approach enforces a strong separation of concerns, improves code reusability, and allows for more granular control over dependencies for each part of the SDK.

**2. Gradle Convention Plugins (`build-logic`)**

Instead of putting build logic directly into each module's `build.gradle.kts` file, we extract it into reusable **convention plugins**. These are located in the `build-logic` directory, which is included as a composite build in `settings.gradle.kts`.

Examples of our convention plugins include:
- `jvm-core-library-convention`: For a standard Kotlin/Java library.
- `jvm-spring-library-convention`: For a library that uses the Spring Framework.
- `spring-boot-app-convention`: For a runnable Spring Boot application.
- `detekt-convention`: For applying and configuring Detekt static analysis.

*Rationale:*
- **DRY:** A module can apply a convention with a single line (e.g., `id("jvm-spring-library-convention")`), inheriting all standard configurations for plugins, dependencies, and tasks.
- **Maintainability:** To change the Kotlin version or a linting rule for all modules, we only need to modify the corresponding convention plugin in one place.
- **Testability:** Since convention plugins are self-contained Gradle projects, we can write unit tests for our build logic.

**3. Version Catalog (`gradle/libs.versions.toml`)**

We use a TOML-based Version Catalog to manage all our dependencies and plugins. This file is the single source of truth for all versions and coordinates.

*Rationale:*
- **Centralization:** All external dependency versions are defined in one file, preventing conflicts and making updates simple.
- **Type Safety:** Gradle uses the catalog to generate type-safe accessors (e.g., `libs.spring.boot.starter.web`), which reduces typos and improves the developer experience in the IDE.
- **Clarity:** It separates dependency definitions from the build scripts, making both easier to read.

**4. Centralized Repository Configuration**

In `settings.gradle.kts`, the repositories (like `mavenCentral()`) are defined once within a `dependencyResolutionManagement` block. We use `RepositoriesMode.FAIL_ON_PROJECT_REPOS` to prevent modules from declaring their own repositories.

*Rationale:* This ensures that all dependencies are sourced from a trusted, centrally-defined set of repositories, improving build security and reproducibility.

**5. Composite Build for Infrastructure (`build-infra`)**

We have an additional composite build, `build-infra`, which is loaded *before* `build-logic`. This directory is intended for build-related code or plugins that `build-logic` itself depends on. For example, if we needed a custom plugin to generate build constants that our convention plugins would use, that generator would reside in `build-infra`.

*Rationale:* This provides a clean separation for foundational build infrastructure that is required by our convention plugins, respecting the dependency chain of the build process itself.

**Consequences:**

- **Consistency:** All modules will have a consistent set of plugins, configurations, and tasks applied to them.
- **Improved Developer Experience:** Onboarding is easier, as the build logic is abstracted away. Developers can focus on application code.
- **High Maintainability:** Updating a library or changing a build setting across the entire project is a trivial, one-line change.
- **Learning Curve:** This structure is more advanced than a simple, single `build.gradle` file. New contributors may need a brief introduction to these concepts. This ADR aims to serve that purpose.
