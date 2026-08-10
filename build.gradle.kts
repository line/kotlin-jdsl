import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Files
import java.nio.file.StandardCopyOption.REPLACE_EXISTING

fun resolveGitDirectory(gitMetadata: File): File? =
    when {
        gitMetadata.isDirectory -> gitMetadata
        gitMetadata.isFile -> {
            val worktreeGitDirectory = resolvePath(gitMetadata.readText().trim().removePrefix("gitdir: "), gitMetadata.parentFile)
            val commonDirectory = File(worktreeGitDirectory, "commondir")

            if (commonDirectory.isFile) {
                resolvePath(commonDirectory.readText().trim(), worktreeGitDirectory)
            } else {
                worktreeGitDirectory
            }
        }
        else -> null
    }

fun resolvePath(path: String, parent: File): File =
    File(path).let {
        if (it.isAbsolute) {
            it
        } else {
            File(parent, path)
        }
    }

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
    version = "3.10.0-SNAPSHOT"

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

        testRuntimeOnly(rootProject.libs.junit.platform.launcher)
    }

    kotlin {
        jvmToolchain(8)
    }

    tasks.withType<KotlinCompile> {
        compilerOptions {
            freeCompilerArgs.add("-Xallow-kotlin-package")
            freeCompilerArgs.add("-Xconsistent-data-class-copy-visibility")
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

    val signingKeyId = providers.gradleProperty("signingKeyId").orNull
    val signingKey = providers.gradleProperty("signingKey").orNull
    val signingPassword = providers.gradleProperty("signingPassword").orNull

    signing {
        if (signingKey != null) {
            useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
            sign(publishing.publications)
        }
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
                suppressPomMetadataWarningsFor("testFixturesApiElements")
                suppressPomMetadataWarningsFor("testFixturesRuntimeElements")

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
    // Kover 0.9.8: subproject aggregation via dependencies block
    // The kover(project) dependency pattern is no longer valid;
    // aggregate tasks are created automatically.
    dependencies {
        implementation(rootProject)
    }

    kover {
        currentProject {
            sources {
                excludedSourceSets.addAll(sourceSets.testFixtures.name)
            }
        }
    }
}

kover {
    reports {
        filters {
            excludes {
                packages("com.linecorp.kotlinjdsl.example.*")
                packages("com.linecorp.kotlinjdsl.benchmark.*")
            }
        }
    }
}

val sonatypeUsername = providers.gradleProperty("sonatypeUsername").orNull
val sonatypePassword = providers.gradleProperty("sonatypePassword").orNull

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl = uri("https://ossrh-staging-api.central.sonatype.com/service/local/")
            snapshotRepositoryUrl = uri("https://central.sonatype.com/repository/maven-snapshots/")

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

    resolveGitDirectory(File(projectDir, ".git"))
        ?.resolve("hooks")
        ?.takeIf(File::isDirectory)
        ?.let { gitHookDir ->
            projectGitHookDir
                .listFiles()
                ?.filter {
                    it.nameWithoutExtension.contains(suffix)
                }?.forEach {
                    val gitHook = File(gitHookDir, it.nameWithoutExtension.removeSuffix("-$suffix"))

                    Files.copy(it.toPath(), gitHook.toPath(), REPLACE_EXISTING)

                    gitHook.setExecutable(true)
                }
        }
}
