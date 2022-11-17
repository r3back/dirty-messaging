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
    implementation(project(":dirty-api"))
    implementation(project(":dirty-core"))
    implementation(project(":dirty-redis"))
}