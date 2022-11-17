import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    "maven-publish"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group "com.qualityplus"
version "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.rabbitmq:amqp-client:5.16.0")
    implementation("org.msgpack:msgpack-core:0.9.3")
    implementation("org.slf4j:slf4j-api:2.0.3")
    implementation(project(":dirty-api"))

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
}

tasks.withType<ShadowJar> {
    archiveFileName.set("dirty-rabbitmq.jar")

    relocate ("com.rabbitmq", "com.qualityplus.dirtymessaging.base.lib.rabbitmq")
    relocate ("org.msgpack", "com.qualityplus.dirtymessaging.base.lib.org.msgpack")
    relocate ("org.slf4j", "com.qualityplus.dirtymessaging.base.lib.org.slf4j")


    doLast {
        @Suppress("UNCHECKED_CAST")
        (rootProject.ext.get("copyJars") as? ((Provider<RegularFile>) -> File) ?: return@doLast)(archiveFile)
    }
}

tasks {
    artifacts {
        archives(shadowJar)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = rootProject.group as String?
            artifactId = "$rootProject.name-$project.name"
            version = rootProject.version as String
            from(components["java"])
            artifact(tasks["shadowJar"])
        }
    }
}