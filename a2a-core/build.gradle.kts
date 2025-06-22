plugins {
    `java-library`
}

tasks.withType<Test> {
    useJUnitPlatform()
}
