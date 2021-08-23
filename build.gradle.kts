plugins {
    base
    id("org.jetbrains.kotlin.multiplatform") version "1.5.20" apply false
    id("org.jetbrains.kotlin.jvm") version "1.5.20" apply false
}

var mockVersion: String by extra { "1.12.0" }

buildscript {
    repositories {
        jcenter()
        mavenCentral()
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