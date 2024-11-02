plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "re.alwyn974.omniflow"
version = "0.0.1"

repositories {
    mavenCentral()
    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("io.minio:minio:8.5.13")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    implementation("com.xenomachina:kotlin-argparser:2.0.7")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.2")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")
    implementation("org.slf4j:slf4j-simple:2.0.3")

    testImplementation(kotlin("test"))
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
