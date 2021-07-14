plugins {
    base
    kotlin("jvm") version "1.3.11" apply false
    id("org.jetbrains.kotlin.jvm") version "1.5.20" apply false
}

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("com.github.dcendents:android-maven-gradle-plugin:1.4.1")
    }
}

allprojects {
    group = "com.avalonomnimedia"
    version = "0.2"

    repositories {
        jcenter()
    }

    apply(plugin="com.github.dcendents.android-maven")
}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}