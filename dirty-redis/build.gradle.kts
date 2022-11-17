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
    implementation ("io.lettuce:lettuce-core:6.2.1.RELEASE")
    implementation ("org.msgpack:msgpack-core:0.9.3")
    implementation ("org.slf4j:slf4j-api:2.0.3")
    implementation(project(":dirty-api"))
}


tasks.withType<ShadowJar> {
    archiveFileName.set("dirty-redis.jar")
    archiveClassifier.set("dirty-redis-classifier")

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
            artifactId = "dirty-redis"/*"$rootProject.name-$name"*/
            version = rootProject.version as String
            from(components["java"])
            artifact(tasks["shadowJar"])
        }
    }
}