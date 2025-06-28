// This file is for plugins that should be applied to the root project.
plugins {
    // Apply the Gradle Doctor plugin using its alias from the version catalog.
    // This plugin is applied only to the root project, but it analyzes all subprojects.
    alias(libs.plugins.gradle.doctor)
}