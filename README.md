# A2A-JVM Reference Implementation

![Java](https://img.shields.io/badge/Java-17-blue?logo=java&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-2.1-blue?logo=kotlin&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.x-brightgreen?logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-8.9-blue?logo=gradle&logoColor=white)

![Build Status](https://img.shields.io/github/actions/workflow/status/vchhabra/a2a-jvm/ci.yml?branch=main&style=for-the-badge)
![Code Coverage](https://img.shields.io/codecov/c/github/vchhabra/a2a-jvm?style=for-the-badge&token=YOUR_CODECOV_TOKEN)
![License](https://img.shields.io/github/license/vchhabra/a2a-jvm?style=for-the-badge)

A reference implementation of a modern JVM application, showcasing best practices in building scalable and maintainable backend services using a curated technology stack. This project is built with Gradle, using convention plugins to enforce consistency and streamline multi-project builds.

## ✨ Features

-   **Modular Architecture**: A multi-project Gradle build that promotes separation of concerns.
-   **Convention Plugins**: Centralized build logic using custom Gradle convention plugins for DRY configurations.
-   **Spring Boot 3**: Built on the latest generation of the Spring Framework for robust application development.
-   **Code Quality Suite**: Integrated static analysis with Detekt and test coverage with JaCoCo.
-   **Containerization**: Simplified Docker image creation using Spring Boot's built-in Buildpacks support.

## 🛠️ Technologies & Stack

-   **Backend**: Spring Boot, Spring MVC
-   **Language**: Kotlin & Java (SDK 17)
-   **Build Tool**: Gradle with Kotlin DSL
-   **Testing**: JUnit 5, Mockito
-   **Code Quality**: Detekt, JaCoCo
-   **Containerization**: Docker

## 🚀 Getting Started

### Prerequisites

-   Java Development Kit (JDK) 17 or later
-   Docker Desktop (for building container images)

### Building the Project

Clone the repository and build the project using the Gradle wrapper:

```bash
git clone https://github.com/vchhabra/a2a-jvm.git
cd a2a-jvm
./gradlew build
```

### Running Tests

Execute all unit and integration tests:

```bash
bash ./gradlew test
```

### Generating a Docker Image

The project uses a convention plugin to automatically configure Docker image creation. To build an image for the example application, run:

```bash
# This will create an image named com.example/a2a-jvm-examples:1.0.0
./gradlew :a2a-jvm-examples:bootBuildImage
```

## 📊 Code Quality Reports

This project uses Detekt for static analysis and JaCoCo for test coverage.

-   **Detekt Reports**: Find issues and code smells.
    ```bash
    ./gradlew detekt
    ```
    Reports are generated in `build/reports/detekt/`.

-   **JaCoCo Test Coverage**: View the test coverage report.
    ```bash
    ./gradlew jacocoTestReport
    ```
    The main report is available at `build/reports/jacoco/test/html/index.html`.

You can also run a full quality check, which includes tests and all reports, with a single command:

```bash
./gradlew check
```

## 🤝 Contributing

Contributions are welcome! Please refer to the `CONTRIBUTING.md` file for guidelines.

## 📄 License

This project is licensed under the **Apache 2.0 License**. See the [LICENSE](LICENSE) file for details.