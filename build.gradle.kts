import java.nio.file.Files
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kover)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.nexus.publish)
    `java-test-fixtures`
    `maven-publish`
    signing
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlinx.kover")
    apply(plugin = "org.jmailen.kotlinter")
    apply(plugin = "java-test-fixtures")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    group = "com.linecorp.kotlin-jdsl"
    version = "3.5.3"

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

        compilerOptions {
            apiVersion = KotlinVersion.KOTLIN_1_7
            languageVersion = KotlinVersion.KOTLIN_1_7
        }
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

    signing {
        val signingKeyId: String? by project
        val signingKey: String? by project
        val signingPassword: String? by project

        useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)

        sign(publishing.publications)
    }

    publishing {
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

                    developers {
                        developer {
                            name.set("LY Corporation")
                            email.set("dl_oss_dev@linecorp.com")
                            url.set("https://engineering.linecorp.com/en/")
                        }
                        developer {
                            id.set("shouwn")
                            name.set("jonghyon.s")
                            email.set("jonghyon.s@linecorp.com")
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

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl = uri("https://oss.sonatype.org/service/local/")
            snapshotRepositoryUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")

            val sonatypeUsername: String? by project
            val sonatypePassword: String? by project

            username = sonatypeUsername
            password = sonatypePassword
        }
    }
}

// Git Hooks
File("$projectDir/.githook").let { projectGitHookDir ->
    val os = OperatingSystem.current()

    val suffix = when {
        os.isMacOsX -> "macos"
        os.isWindows -> "windows"
        else -> "default"
    }

    val gitHookDir = File("$projectDir/.git/hooks")

    gitHookDir
        .listFiles()
        ?.forEach {
            it.deleteRecursively()
        }

    projectGitHookDir
        .listFiles()
        ?.filter {
            it.nameWithoutExtension.contains(suffix)
        }?.forEach {
            val gitHook = File(gitHookDir, it.nameWithoutExtension.removeSuffix("-$suffix"))

            Files.copy(it.toPath(), gitHook.toPath())

            gitHook.setExecutable(true)
        }
}
