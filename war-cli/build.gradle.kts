plugins {
    application
    kotlin("jvm")
}

dependencies {
    implementation(project(":engine"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC.2")

    implementation("com.github.ajalt.clikt:clikt:4.4.0")

    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation("junit:junit:4.13.2")
}