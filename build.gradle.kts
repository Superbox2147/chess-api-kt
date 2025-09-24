plugins {
    kotlin("jvm") version "2.1.20"
    `maven-publish`
}

group = "chess.api"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    api("net.java.dev.jna:jna:5.17.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.register("getBinary", Exec::class.java) {
    this.workingDir(projectDir)
    executable("./get-binary-source.sh")
    group = "binary"
}

tasks.register("buildBinary", Exec::class.java) {
    this.workingDir(projectDir)
    executable("./build.sh")
    group = "binary"
    dependsOn("getBinary")
}

sourceSets {
    main {
        resources {
            srcDir("binary/out")
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}