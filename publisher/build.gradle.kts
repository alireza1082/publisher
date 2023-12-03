import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    id("maven-publish")
    id("com.gradle.plugin-publish") version "1.2.1"
    id("java-gradle-plugin")
    `java-gradle-plugin`
    kotlin("jvm") version "1.8.21"
//    `kotlin-dsl`
}

group = "ir.sharif.publisher"

version = "0.0.9"

gradlePlugin {
    website.set("https://sharif.ir")
    vcsUrl.set(website.get())
    plugins {
        create("publishingPlugin") {
            id = "ir.sharif.publisher"
            implementationClass = "ir.sharif.publisher.PublishTask"
            displayName = "Publisher"
        }
    }
}

publishing {
    publications {
//        it.create<MavenPublication>("maven") {
//            artifactId = artifact
//            afterEvaluate {
//                from(components["release"])
//            }
//        }
    }
}

dependencies {
    implementation(gradleApi())
}