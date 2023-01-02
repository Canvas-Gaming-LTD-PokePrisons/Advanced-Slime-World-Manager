plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

dependencies {
    implementation(project(":slimeworldmanager-api"))
    implementation(project(":slimeworldmanager-nms-common"))
    implementation(project(":slimeworldmanager-nms-v118-2", "reobf"))
    implementation(project(":slimeworldmanager-nms-v119", "reobf"))
    implementation(project(":slimeworldmanager-nms-v119-1", "reobf"))
    implementation(project(":slimeworldmanager-nms-v119-2", "reobf"))
    implementation(project(":slimeworldmanager-classmodifierapi"))

    implementation("com.flowpowered:flow-nbt:2.0.2")
    implementation("com.github.luben:zstd-jni:1.5.2-2")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.mongodb:mongo-java-driver:3.12.11")
    implementation("io.lettuce:lettuce-core:6.2.2.RELEASE") {
        exclude("io.netty")
    }
    implementation("org.spongepowered:configurate-yaml:4.1.2")
    implementation("commons-io:commons-io:2.11.0")
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        archiveClassifier.set("")

        fun rel(path: String) {
            relocate(path, "com.grinderwolf.swm.internal.$path")
        }

        rel("com.flowpowered.nbt")
        rel("com.zaxxer.hikari")
        rel("com.mongodb")
        rel("io.lettuce")
        rel("org.bson")
        rel("io.leangen.geantyref")
        rel("org.apache.commons.io")
        rel("org.checkerframework")
        rel("org.spongepowered.configurate")
        rel("org.slf4j")
        rel("org.yaml.snakeyaml")
        rel("org.reactivestreams")
        rel("reactor")
        rel("com.github.luben.zstd")
        rel("com.google.errorprone.annotations")
    }

    build {
        dependsOn(shadowJar)
    }

    runServer {
        minecraftVersion("1.19.2")
        jvmArgs("-javaagent:" + project(":slimeworldmanager-classmodifier").tasks.named<AbstractArchiveTask>("shadowJar").flatMap { shadow -> shadow.archiveFile }.get().asFile.toPath())
    }
}

description = "slimeworldmanager-plugin"
