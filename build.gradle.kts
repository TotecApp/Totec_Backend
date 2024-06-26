
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val mysqlVersion:String by project
val koinKtor: String by project
val hikaricpVersion: String by project
val exposed_version: String by project

plugins {
    kotlin("jvm") version "1.9.24"
    id("io.ktor.plugin") version "2.3.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.24"
}

group = "ds.com"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("com.h2database:h2:2.1.214")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.insert-koin:koin-ktor:$koinKtor")
    implementation("com.zaxxer:HikariCP:$hikaricpVersion")
    implementation("io.ktor:ktor-server-cors:$ktor_version")
    implementation("org.xerial:sqlite-jdbc:3.45.3.0")
    implementation("org.postgresql:postgresql:42.5.1")
    implementation("io.ktor:ktor-server-sessions:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("io.kotest:kotest-runner-junit5:5.9.0")
    testImplementation("io.kotest:kotest-assertions-core:5.9.0")
    testImplementation("io.kotest:kotest-property:5.9.0")
    testImplementation("io.mockk:mockk:1.13.10")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
