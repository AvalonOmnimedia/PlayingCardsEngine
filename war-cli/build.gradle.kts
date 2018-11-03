import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
    application
    kotlin("jvm")
}

application {
    mainClassName = "com.avalonomnimedia.war.cli.MainKt"
}

dependencies {
    implementation(project(":engine"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.0.0")

    implementation("com.github.ajalt:clikt:1.5.0")

    testImplementation("junit:junit:4.12")
}