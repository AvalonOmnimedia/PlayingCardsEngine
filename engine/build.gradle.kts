plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

repositories {
    mavenCentral()
}

val mockVersion: String by rootProject.extra

kotlin {
    jvm()
    js {
        nodejs()
        browser()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
                implementation ("org.jetbrains.kotlin:kotlin-reflect")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-test-common")
                implementation ("org.jetbrains.kotlin:kotlin-test-annotations-common")
                //implementation ("io.mockk:mockk-common:$mockVersion")
                implementation ("io.mockk:mockk:$mockVersion")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-test")
                implementation ("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-stdlib-js")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-test-js")
            }
        }
    }
}