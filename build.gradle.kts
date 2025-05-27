plugins {
    application

    kotlin("jvm") version "2.1.0"
    kotlin("kapt") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"

    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "in.porter"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val mcpVersion = "0.4.0"
val slf4jVersion = "2.0.9"
val ktorVersion = "3.1.1"
val anthropicVersion = "0.8.0"

dependencies {
    implementation("com.anthropic:anthropic-java:$anthropicVersion")
    implementation("io.modelcontextprotocol:kotlin-sdk:$mcpVersion")
    implementation("org.slf4j:slf4j-nop:$slf4jVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-sse:$ktorVersion")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("in.porter.mcp.server.app.MainRunnerKt")
}
