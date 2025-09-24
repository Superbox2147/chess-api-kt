plugins {
    kotlin("jvm")
    application
}

group = "chess.api"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("chess.api.MainKt")
    applicationDefaultJvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(project(":"))
}

tasks.test {
    useJUnitPlatform()
}