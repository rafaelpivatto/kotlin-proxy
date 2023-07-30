plugins {
    kotlin("jvm") version "1.9.0"
    application
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "br.com.example"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-core:2.3.2")
    implementation("io.ktor:ktor-server-netty:2.3.2")
    implementation("io.ktor:ktor-serialization:2.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    implementation("ch.qos.logback:logback-classic:1.2.9")
    implementation("com.squareup.okhttp3:okhttp:4.9.2")

}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<Jar> {
        manifest {
            attributes["Main-Class"] = "MainKt"
        }
    }

    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        manifest {
            attributes["Main-Class"] = "MainKt"
        }
    }

    getByName("build").dependsOn(getByName("shadowJar"))
}