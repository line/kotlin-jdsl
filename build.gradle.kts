import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    jacoco

    id("org.jetbrains.kotlin.plugin.noarg") version Dependencies.kotlinVersion
    id("org.jetbrains.kotlin.plugin.allopen") version Dependencies.kotlinVersion

    kotlin("jvm") version Dependencies.kotlinVersion
}

allprojects {
    group = "com.linecorp.kotlin-jdsl"
    version = "1.0.1.RELEASE"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "jacoco")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-noarg")
    apply(plugin = "kotlin-allopen")

    apply<LocalPropertiesPlugin>()

    java.sourceCompatibility = JavaVersion.VERSION_1_8
    java.targetCompatibility = JavaVersion.VERSION_1_8

    dependencies {
        implementation(Dependencies.koltin)
    }

    allOpen {
        annotation("org.springframework.context.annotation.Configuration")
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.Embeddable")
    }

    noArg {
        annotation("org.springframework.context.annotation.Configuration")
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.Embeddable")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
            jvmTarget = "1.8"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
