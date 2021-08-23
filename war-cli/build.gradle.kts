import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
}

application {
    mainClassName = "com.avalonomnimedia.war.cli.MainKt"
}

dependencies {
    implementation(project(":war-core"))
    implementation(project(":engine"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.0.0")

    implementation("com.github.ajalt:clikt:1.5.0")

    testImplementation("io.mockk:mockk:1.8.13.kotlin13")
    testImplementation("junit:junit:4.12")
}

repositories {
    mavenCentral()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}