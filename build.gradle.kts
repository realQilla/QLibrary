plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.0-beta6"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.14"
}

group = "net.qilla"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
    implementation("net.kyori:adventure-api:4.17.0")
}

tasks.shadowJar {
    archiveClassifier.set("")
}