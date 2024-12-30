import org.gradle.internal.classpath.Instrumented.systemProperty
import java.net.URI

plugins {
    application
    kotlin("jvm") version "2.1.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("plugin.serialization") version "2.0.21"
    id("maven-publish")
}

group = "re.alwyn974.omniflow"
// x-release-please-start-version
version = "1.0.2"
// x-release-please-end

repositories {
    mavenCentral()
    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("io.minio:minio:8.5.13")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("com.xenomachina:kotlin-argparser:2.0.7")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.2")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")
    implementation("org.slf4j:slf4j-simple:2.0.3")

    val ktorVersion = "3.0.3"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    testImplementation(kotlin("test"))
}

application {
    mainClass = "re.alwyn974.omniflow.MainKt"
    systemProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>().configureEach {
    jvmTargetValidationMode.set(org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode.WARNING)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = URI("https://maven.pkg.github.com/StreetLess/OmniFlow")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}