plugins {
    `maven-publish`
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("junit:junit:4.12")
}

publishing {
    publications {
        create("default", MavenPublication::class.java) {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("$buildDir/repository")
        }
    }
}