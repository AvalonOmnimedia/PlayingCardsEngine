import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
    application
    kotlin("jvm")
}

dependencies {
    implementation(project(":engine"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    implementation("com.github.ajalt.clikt:clikt:3.4.0")

    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("junit:junit:4.13.2")
}