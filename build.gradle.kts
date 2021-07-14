plugins {
    base
    id("org.jetbrains.dokka") version "0.9.17"
    id("com.jfrog.bintray") version "1.8.1"
    id("org.jetbrains.kotlin.multiplatform") version "1.5.20" apply false
    id("org.jetbrains.kotlin.jvm") version "1.5.20" apply false
}

buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.0")
    }
}

allprojects {
    group = "com.avalonomnimedia"
    version = "0.2"

    repositories {
        mavenCentral()
    }
}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}