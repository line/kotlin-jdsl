import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    jacoco

    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
}

allprojects {
    group = "com.linecorp.kotlin-jdsl"
    version = "2.2.0.RELEASE"

    repositories {
        mavenCentral()
    }
}

rootProject {
    apply<JacocoExtensionPlugin>()
}

subprojects {
    apply(plugin = "jacoco")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-noarg")
    apply(plugin = "kotlin-allopen")

    apply<LocalPropertiesPlugin>()

    dependencies {
        implementation(rootProject.libs.kotlin)
    }

    allOpen {
        annotation("org.springframework.context.annotation.Configuration")
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.Embeddable")
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.Embeddable")
    }

    noArg {
        annotation("org.springframework.context.annotation.Configuration")
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.Embeddable")
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.Embeddable")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            showExceptions = true
            exceptionFormat = FULL
            showCauses = true
            showStackTraces = true
            events = setOf(FAILED)
        }
    }

    kotlin {
        jvmToolchain(17)
    }
}
