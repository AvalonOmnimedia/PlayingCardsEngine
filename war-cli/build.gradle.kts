import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":engine"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:0.30.2")

    testImplementation("junit:junit:4.12")
}

kotlin {
    experimental.coroutines = Coroutines.ENABLE
}