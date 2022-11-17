plugins {
    java
    `maven-publish`
    id ("com.github.johnrengelman.shadow") version "7.1.2"
}

group "com.qualityplus"
version "0.0.6"

val copyJars = { file: Provider<RegularFile> -> run {
        val name = file.get().asFile.name

        println ("Moving $name to new directory!")

        val path = "C:/Users/andre/OneDrive/Desktop/DirtyFolder/$name"

        file.get().asFile.copyTo(File(path), true)
    }
}


project.extra["copyJars"] = copyJars

subprojects{
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.22")
        annotationProcessor("org.projectlombok:lombok:1.18.22")
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
                artifact(tasks["shadowJar"])
            }
        }
    }
}

