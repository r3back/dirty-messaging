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
    implementation("org.slf4j:slf4j-api:2.0.3")
    implementation("org.msgpack:msgpack-core:0.9.3")
}

tasks.withType<ShadowJar> {
    archiveFileName.set("dirty-api.jar")

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