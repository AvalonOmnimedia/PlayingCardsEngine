plugins {
    base
    `build-scan`
    kotlin("jvm") version "1.2.71" apply false
}

buildScan {
    setTermsOfServiceUrl("https://gradle.com/terms-of-service")
    setTermsOfServiceAgree("yes")

    publishAlways()
}

allprojects {
    group = "com.avalonomnimedia"
    version = "0.1"

    repositories {
        jcenter()
    }
}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}