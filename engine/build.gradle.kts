import com.jfrog.bintray.gradle.BintrayExtension

plugins {
    `maven-publish`
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("com.jfrog.bintray")
}

fun findProperty(s: String) = project.findProperty(s) as String?

val publicationName = "playing-card-engine"
bintray {
    user = "joelshea" //findProperty("bintrayUser")
    key = "c0124969daef539707d0240ceaa9a8389dc91041" //findProperty("bintrayApiKey")
    publish = true
    setPublications(publicationName)
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = "playing-card-engine"
        userOrg = "avalonomnimedia"
        websiteUrl = "https://avalonomnimedia.com"
        githubRepo = "AvalonOmnimedia/PlayingCardsEngine"
        vcsUrl = "https://github.com/AvalonOmnimedia/PlayingCardsEngine.git"
        description = "Simple Lib for TLS/SSL socket handling written in Kotlin"
        setLabels("kotlin")
        setLicenses("MIT")
        desc = description
    })
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    testImplementation("io.mockk:mockk:1.8.13.kotlin13")
    testImplementation("junit:junit:4.12")
}

val dokka by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class) {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    classifier = "javadoc"
    from(dokka)
}

val sourcesJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles sources JAR"
    classifier = "sources"
    from(java.sourceSets["main"].allSource)
}

fun MavenPom.addDependencies() = withXml {
    asNode().appendNode("dependencies").let { depNode ->
        configurations.compile.allDependencies.forEach {
            depNode.appendNode("dependency").apply {
                appendNode("groupId", it.group)
                appendNode("artifactId", it.name)
                appendNode("version", it.version)
            }
        }
    }
}

publishing {
    publications {
        create(publicationName, MavenPublication::class.java) {
            from(components["java"])
            artifactId = "playing-card-engine"
            artifact(dokkaJar)
            artifact(sourcesJar)
        }
    }
    repositories {
        maven {
            url = uri("$buildDir/repository")
        }
    }
}