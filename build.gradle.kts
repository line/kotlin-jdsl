import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kover)
    alias(libs.plugins.ktlint)
    `java-test-fixtures`
    `maven-publish`
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlinx.kover")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "java-test-fixtures")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    group = "com.linecorp.kotlin-jdsl"
    version = "3.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(rootProject.libs.kotlin)

        testImplementation(rootProject.libs.test.junit)
        testImplementation(rootProject.libs.test.mockk)
        testImplementation(rootProject.libs.test.assertJ)

        testFixturesImplementation(rootProject.libs.test.junit)
        testFixturesImplementation(rootProject.libs.test.mockk)
        testFixturesImplementation(rootProject.libs.test.assertJ)
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

    java {
        withSourcesJar()
        withJavadocJar()
    }

    publishing {
        repositories {
            maven {
                val snapshotRepoUri = uri("https://oss.sonatype.org/content/repositories/snapshots/")
                val releaseRepoUri = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")

                name = "OSSRH"
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotRepoUri else releaseRepoUri

                val sonatypeUsername: String? = System.getenv("SONATYPE_USERNAME")
                val sonatypePassword: String? = System.getenv("SONATYPE_PASSWORD")

                credentials {
                    username = sonatypeUsername ?: ""
                    password = sonatypePassword ?: ""
                }
            }
        }

        publications {
            create<MavenPublication>("maven") {
                from(components["java"])

                pom {
                    name.set(artifactId)
                    description.set(
                        "Kotlin library that makes it easy to build and execute queries without generated metamodel.",
                    )
                    url.set("https://github.com/line/kotlin-jdsl")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    scm {
                        connection.set("scm:git@github.com:line/kotlin-jdsl.git")
                        developerConnection.set("scm:git:ssh://github.com:line/kotlin-jdsl.git")
                        url.set("https://github.com/line/kotlin-jdsl")
                    }
                }
            }
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

    kover {
        excludeSourceSets {
            names(sourceSets.testFixtures.name)
        }
    }
}

koverReport {
    filters {
        excludes {
            packages("com.linecorp.kotlinjdsl.example.*")
            packages("com.linecorp.kotlinjdsl.benchmark.*")
        }
    }
}
