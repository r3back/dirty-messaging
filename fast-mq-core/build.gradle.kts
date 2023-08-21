plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":fast-mq-api"))
    implementation(project(":fast-mq-redis"))
    implementation(project(":fast-mq-rabbitmq"))
    implementation("junit:junit:4.13.2")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testImplementation("org.mockito:mockito-core:5.3.1")
    testImplementation("org.mockito:mockito-junit-jupiter:5.3.1")
}



tasks {
    shadowJar {
        archiveFileName.set("fast-mq.jar")
        archiveClassifier.set("fast-mq-classifier")

        relocate("com.rabbitmq", "com.qualityplus.fastmq.base.lib.rabbitmq")
        relocate("org.msgpack", "com.qualityplus.fastmq.base.lib.org.msgpack")
        relocate("org.slf4j", "com.qualityplus.fastmq.base.lib.org.slf4j")
        relocate("io.netty", "com.qualityplus.fastmq.base.libs.io.netty")
        relocate("io.lettuce.core", "com.qualityplus.fastmq.base.libs.io.lettuce.core")
        relocate("reactor", "com.qualityplus.fastmq.base.libs.reactor")
        relocate("junit", "com.qualityplus.fastmq.base.libs.junit")
        relocate("org.hamcrest", "com.qualityplus.fastmq.base.libs.org.hamcrest")
        relocate("org.junit", "com.qualityplus.fastmq.base.libs.org.junit")
        relocate("org.reactivestreams", "com.qualityplus.fastmq.base.libs.org.reactivestreams")

        doLast {
            @Suppress("UNCHECKED_CAST")
            (rootProject.ext.get("copyJars") as? ((Provider<RegularFile>) -> File) ?: return@doLast)(archiveFile)
        }
    }

    build {
        dependsOn(shadowJar)
    }
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            groupId = project.group.toString()
            version = project.version.toString()
            artifactId = rootProject.name

            artifact(project.tasks.shadowJar.get().archiveFile)
        }
    }

    publishing {
        repositories {
            maven {
                name = "jitpack"
                url = uri("https://jitpack.io")
                credentials {
                    username = System.getenv("JITPACK_USERNAME")
                    password = System.getenv("JITPACK_PASSWORD")
                }
            }
        }
    }
}