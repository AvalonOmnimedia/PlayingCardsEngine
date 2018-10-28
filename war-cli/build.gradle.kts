plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":engine"))
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("junit:junit:4.12")
}