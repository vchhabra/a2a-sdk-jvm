### **ADR-0001: Project Structure and Gradle Build Strategy**
**Status:** Active
**Context:** As the project grows, we need a build system that is scalable, maintainable, and consistent across all modules. Without a clear strategy, we risk facing common issues like: `a2a-jvm`
- Duplicated build logic across files. `build.gradle.kts`
- Inconsistent dependency versions, leading to potential classpath conflicts.
- Difficulty in enforcing project-wide standards like code quality checks or test coverage.
- A steep learning curve for new contributors trying to understand the build.

The goal is to establish a "Don't Repeat Yourself" (DRY) approach for our build logic that promotes consistency and simplifies maintenance.
**Decision:** We have adopted a modern Gradle architecture that leverages **Composite Builds**, **Convention Plugins**, and a **Version Catalog**. This structure separates concerns, centralizes configuration, and provides a type-safe way to manage our build.
Here is a breakdown of the key components:
**1. Multi-Module Project Structure** The project is organized into several distinct modules, as defined in : `settings.gradle.kts`
- : Core logic and data models, likely with minimal dependencies. `a2a-jvm-core`
- : The module that is responsible for the JSON-RPC-specific data contracts. `a2a-jvm-transport-jsonrpc`
- : Code specific to the client-side implementation of the SDK. `a2a-jvm-client`
- : Code specific to the server-side implementation of the SDK. `a2a-jvm-server`
- : A dedicated starter to simplify integration with the Spring Boot ecosystem. `a2a-jvm-spring-boot-starter`
- : An example application demonstrating how to use the SDK. `a2a-jvm-examples`

**Rationale:** This modular approach enforces a strong separation of concerns, improves code reusability, and allows for more granular control over dependencies for each part of the SDK.
**2. Gradle Convention Plugins ()`build-logic`** Instead of putting build logic directly into each module's file, we extract it into reusable **convention plugins**. These are located in the directory, which is included as a composite build via in . `build.gradle.kts``build-logic``includeBuild("build-logic")``settings.gradle.kts`
Examples of conventions we use are:
- : For a standard Kotlin/Java library. `jvm-core-library-convention`
- : For a library that is part of the Spring ecosystem. `jvm-spring-library-convention`
- : For a runnable Spring Boot application. `spring-boot-app-convention`
- : For applying and configuring static analysis tools like Detekt/Ktlint. `ktlint-convention`

**Rationale:**
- **DRY:** A module can apply a convention with a single line (e.g., ), inheriting all the standard configuration for plugins, dependencies, and tasks. `alias(libs.plugins.jvm.spring.library.convention)`
- **Maintainability:** To change the Kotlin version or a linting rule for all modules, we only need to modify the corresponding convention plugin in one place.
- **Testability:** Since convention plugins are self-contained Gradle projects, we can write tests for our build logic itself.

**3. Version Catalog ()`gradle/libs.versions.toml`** We use a TOML-based Version Catalog to manage all of our dependencies and plugins. This file acts as the single source of truth for all versions and coordinates.
**Rationale:**
- **Centralization:** All external dependency versions are defined in one file, preventing conflicts and making updates simple.
- **Type Safety:** Gradle uses the catalog to generate type-safe accessors (e.g., ), which reduces typos and improves the developer experience in the IDE. `libs.spring.boot.starter.web`
- **Clarity:** It separates dependency definitions from the build scripts, making both easier to read.

**4. Centralized Repository Configuration** In , the repositories (like ) are defined once within a `dependencyResolutionManagement` block. The setting is used to prevent modules from declaring their own repositories. `settings.gradle.kts``mavenCentral()``RepositoriesMode.FAIL_ON_PROJECT_REPOS`
**Rationale:**
- **Security & Predictability:** This ensures that all dependencies are sourced from a trusted, centrally-defined set of repositories, improving build security and reproducibility.

**5. Composite Build for Infrastructure ()`build-infra`** We have an additional composite build, , which is loaded _before_ . This directory is intended for build-related code or plugins that itself depends on. `build-infra``build-logic``build-logic`
**Rationale:** This provides a clean separation for foundational build infrastructure that is required by our convention plugins, respecting the dependency chain of the build process itself.
**Consequences:**
- **Consistency:** All modules will have a consistent set of plugins, configurations, and tasks applied to them, leading to predictable behavior.
- **Improved Developer Experience:** Onboarding is easier, as the build logic is abstracted away. Developers can focus on application code, knowing that build and quality checks are handled by the conventions.
- **High Maintainability:** Updating a library or changing a build setting across the entire multi-module project is a trivial, one-line change in the version catalog or the relevant convention plugin.
- **Learning Curve:** This structure is more advanced than a simple, single file. New contributors may need a brief introduction to the concepts of convention plugins and version catalogs. This document aims to serve that purpose. `build.gradle`
