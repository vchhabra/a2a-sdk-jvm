import plugins.conventions.BuildConstants

plugins {
    // This module is part of the Spring ecosystem.
    alias(libs.plugins.jvm.spring.library.convention)
}

dependencies {
    implementation(project(BuildConstants.Modules.A2A_CORE))
    implementation(libs.spring.boot.starter.web)
}
