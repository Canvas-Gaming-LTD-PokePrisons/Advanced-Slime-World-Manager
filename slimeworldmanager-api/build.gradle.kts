plugins {
    `java-library`
}

dependencies {
    implementation("com.flowpowered:flow-nbt:2.0.2")
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}

java {
    withSourcesJar()
    withJavadocJar()
}

description = "slimeworldmanager-api"
