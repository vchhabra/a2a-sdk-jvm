import plugins.conventions.BuildConstants

plugins {
    // This module is part of the Spring ecosystem.
    alias(libs.plugins.jvm.spring.library.convention)
}

dependencies {
    api(project(BuildConstants.Modules.A2A_JVM_CLIENT))
    api(project(BuildConstants.Modules.A2A_JVM_SERVER))
    api(libs.spring.boot.autoconfigure)
}
