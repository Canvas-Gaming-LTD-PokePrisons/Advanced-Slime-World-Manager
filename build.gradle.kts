plugins {
    `java-library`
    `maven-publish`
    id("org.kordamp.gradle.profiles") version "0.47.0"
}

buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
    }
    dependencies {
        classpath("io.freefair.gradle:lombok-plugin:6.3.0")
    }
}

allprojects {

    group = "com.grinderwolf"
    version = "2.10.0-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "org.kordamp.gradle.profiles")

    repositories {
        mavenLocal()
        mavenCentral()

        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.codemc.io/repository/nms/")
        maven("https://repo.rapture.pw/repository/maven-releases/")
        maven("https://repo.glaremasters.me/repository/concuncan/")
    }

    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    tasks.withType<Javadoc> {
        options.encoding = Charsets.UTF_8.name()
        (options as StandardJavadocDocletOptions).tags("apiNote:a:API Note:", "implSec:a:Implementation Requirements:", "implNote:a:Implementation Note:")
    }

    tasks.withType<ProcessResources> {
        filteringCharset = Charsets.UTF_8.name()
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

subprojects {
    apply<MavenPublishPlugin>()

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "${project.group}"
                artifactId = project.name
                version = "${project.version}"

                from(components["java"])

                pom {
                    name.set("Advanced Slime World Manager API")
                    description.set("API for ASWM")
                    url.set("https://github.com/Paul19988/Advanced-Slime-World-Manager")
                    licenses {
                        license {
                            name.set("GNU General Public License, Version 3.0")
                            url.set("https://www.gnu.org/licenses/gpl-3.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("InfernalSuite")
                            name.set("The InfernalSuite Team")
                            url.set("https://github.com/InfernalSuite")
                            email.set("infernalsuite@gmail.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:https://github.com/Paul19988/Advanced-Slime-World-Manager.git")
                        developerConnection.set("scm:git:ssh://github.com/Paul19988/Advanced-Slime-World-Manager.git")
                        url.set("https://github.com/Paul19988/Advanced-Slime-World-Manager/")
                    }
                    issueManagement {
                        system.set("Github")
                        url.set("https://github.com/Paul19988/Advanced-Slime-World-Manager/issues")
                    }
                }

                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }
            }
        }
    }
}
