plugins {
    `java-library`
}

group = "dev.faceless.gnstaff"
version = "1.0"
description = "Moderation Plugin For GlitchedNetwork"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

tasks.withType(JavaCompile::class.java) {
    options.encoding = "UTF-8"
}

tasks.jar{
    destinationDirectory.set(file("C:\\Users\\Faceless\\Desktop\\Servers\\Paper 1.20.4\\plugins"))
}
