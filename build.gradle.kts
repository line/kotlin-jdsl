import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kover)
    `java-test-fixtures`
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlinx.kover")
    apply(plugin = "java-test-fixtures")

    group = "com.linecorp.kotlin-jdsl"
    version = "3.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(rootProject.libs.kotlin)

        testImplementation(rootProject.libs.junit)
        testImplementation(rootProject.libs.mockk)
        testImplementation(rootProject.libs.assertJ)

        testFixturesImplementation(rootProject.libs.junit)
        testFixturesImplementation(rootProject.libs.mockk)
        testFixturesImplementation(rootProject.libs.assertJ)
    }

    kotlin {
        jvmToolchain(8)
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-Xallow-kotlin-package",
            )
        }
        jvmTargetValidationMode.set(JvmTargetValidationMode.ERROR)
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
}

subprojects {
    rootProject.dependencies {
        kover(this@subprojects)
    }

    dependencies {
        implementation(rootProject)
    }
}
