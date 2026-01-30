import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(rootLibs.plugins.kotlin2.jvm)
    alias(rootLibs.plugins.kotlin2.spring)
    alias(rootLibs.plugins.kotlin2.jpa)
    alias(rootLibs.plugins.spring.boot4)
    alias(rootLibs.plugins.spring.dependency.management)
    alias(rootLibs.plugins.ktlint5)
    alias(rootLibs.plugins.kover)
    alias(rootLibs.plugins.nexus.publish)
    `maven-publish`
    signing
}

group = "com.linecorp.kotlin-jdsl"
version = "3.7.1"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Core Dependencies (from included build)
    implementation("com.linecorp.kotlin-jdsl:kotlin-jdsl:${project.version}")
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:${project.version}")
    implementation("com.linecorp.kotlin-jdsl:jpql-query-model:${project.version}")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:${project.version}")

    // Spring Boot & JPA
    compileOnly(rootLibs.spring.boot4.starter.data.jpa)

    // Test
    testImplementation(rootLibs.spring.boot4.starter.data.jpa)
    testImplementation(rootLibs.spring.boot4.starter.test)
    testImplementation(rootLibs.junit6)
    testImplementation(rootLibs.junit6.platform.engine)
    testImplementation(rootLibs.junit6.platform.launcher)
    testImplementation(rootLibs.mockk)
    testImplementation(rootLibs.jakarta.persistence32.api)
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
        freeCompilerArgs.add("-Xallow-kotlin-package")
    }
}

tasks.test {
    useJUnitPlatform()
}

// Disable bootJar as this is a library
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    archiveClassifier.set("")
}

java {
    withSourcesJar()
    withJavadocJar()
}

signing {
    val signingKeyId: String? by project
    val signingKey: String? by project
    val signingPassword: String? by project

    // Only sign if keys are present (avoid failure in local dev)
    if (signingKeyId != null) {
        useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
        sign(publishing.publications)
    }
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

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl = uri("https://ossrh-staging-api.central.sonatype.com/service/local/")
            snapshotRepositoryUrl = uri("https://central.sonatype.com/repository/maven-snapshots/")

            val sonatypeUsername: String? by project
            val sonatypePassword: String? by project

            username = sonatypeUsername
            password = sonatypePassword
        }
    }
}
