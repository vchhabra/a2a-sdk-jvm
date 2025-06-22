plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

rootProject.name = "a2a-sdk-jvm"
include("a2a-core")
include("a2a-client")
include("a2a-server")
include("a2a-spring-boot-starter")
include("a2a-examples")
