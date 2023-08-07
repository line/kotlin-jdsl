import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-test-fixtures`

    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kover)
}

allprojects {
    group = "com.linecorp.kotlin-jdsl"
    version = "3.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-Xallow-kotlin-package",
            )
        }
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
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlinx.kover")
    apply(plugin = "java-test-fixtures")

    rootProject.dependencies {
        kover(this@subprojects)
    }

    dependencies {
        implementation(rootProject)
        implementation(rootProject.libs.kotlin)

        testImplementation(rootProject.libs.junit)
        testImplementation(rootProject.libs.mockk)

        testFixturesApi(testFixtures(rootProject))
    }

    kotlin {
        jvmToolchain(17)
    }
}

koverReport {
    filters {
        excludes {
            packages("com.linecorp.kotlinjdsl.query")
        }
    }
}

// root project
dependencies {
    testImplementation(libs.junit)

    testFixturesApi(libs.assertJ)
}
