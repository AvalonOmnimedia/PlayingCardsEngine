plugins {
    base
    `build-scan`
    kotlin("jvm") version "1.3.11" apply false
    id("org.jetbrains.dokka") version "0.9.17"
    id("com.jfrog.bintray") version "1.8.1"
}

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("com.github.dcendents:android-maven-gradle-plugin:1.4.1")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.0")
    }
}


buildScan {
    setTermsOfServiceUrl("https://gradle.com/terms-of-service")
    setTermsOfServiceAgree("yes")

    publishAlways()
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