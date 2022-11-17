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
    compileOnly(project(":dirty-api"))
}

tasks.withType<ShadowJar> {
    archiveFileName.set("dirty-core.jar")
    archiveClassifier.set("dirty-core-classifier")

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
            artifactId = "dirty-core"/*"$rootProject.name-$name"*/
            version = rootProject.version as String
            from(components["java"])
            artifact(tasks["shadowJar"])
        }
    }
}