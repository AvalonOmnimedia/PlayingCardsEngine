plugins {
    base
    id("org.jetbrains.kotlin.jvm") version "1.5.20" apply false
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.github.dcendents:android-maven-gradle-plugin:1.4.1")
    }
}

allprojects {
    group = "com.avalonomnimedia"
    version = "0.2"

    repositories {
        mavenCentral()
    }
}