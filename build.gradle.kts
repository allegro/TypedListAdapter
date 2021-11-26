plugins {
    `java-library`
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("pl.allegro.tech.build.axion-release") version "1.13.2"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

group = "pl.allegro.android.typedlistadapter"
version = scmVersion.version

nexusPublishing {
    repositories {
        sonatype {
            username.set(System.getenv("SONATYPE_USERNAME"))
            password.set(System.getenv("SONATYPE_PASSWORD"))
        }
    }
}
