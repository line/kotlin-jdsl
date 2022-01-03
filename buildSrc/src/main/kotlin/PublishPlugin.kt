import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.plugins.signing.SigningExtension

class PublishPlugin : AbstractPlugin() {
    override fun Project.initialize() {
        apply(plugin = "signing")
        apply(plugin = "maven-publish")

        java {
            withSourcesJar()
            withJavadocJar()
        }

        signing {
            sign(publishing.publications)
        }

        publishing {
            repositories {
                maven {
                    val releaseRepoUri = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                    val snapshotRepoUri = uri("https://oss.sonatype.org/content/repositories/snapshots/")

                    url = if (version.isSnapshot()) snapshotRepoUri else releaseRepoUri

                    val sonatypeUsername: String? by project
                    val sonatypePassword: String? by project

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
                        description.set("DSL for JPA Criteria API without generated metamodel and reflection.")
                        url.set("https://github.com/line/kotlin-jdsl")

                        licenses {
                            license {
                                name.set("The Apache License, Version 2.0")
                                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                            }
                        }

                        developers {
                            developer {
                                name.set("LINE Corporation")
                                email.set("dl_oss_dev@linecorp.com")
                                url.set("https://engineering.linecorp.com/en/")
                            }
                            developer {
                                id.set("shouwn")
                                name.set("jonghyon.s")
                                email.set("jonghyon.s@linecorp.com")
                            }
                            developer {
                                id.set("cj848")
                                name.set("hyunsik.kang")
                                email.set("kaka@linecorp.com")
                            }
                            developer {
                                id.set("pickmoment")
                                name.set("pickmoment")
                                email.set("moongeun.kim@linecorp.com")
                            }
                            developer {
                                id.set("huisam")
                                name.set("huisam")
                                email.set("huijin.hong@linecorp.com")
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
}

private val Project.publishing: PublishingExtension
    get() = (this as org.gradle.api.plugins.ExtensionAware).extensions.getByName("publishing") as PublishingExtension

private fun Project.java(configure: Action<JavaPluginExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("java", configure)

private fun Project.signing(configure: Action<SigningExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("signing", configure)

private fun Project.publishing(configure: Action<PublishingExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("publishing", configure)

private val Project.ext: org.gradle.api.plugins.ExtraPropertiesExtension
    get() = (this as org.gradle.api.plugins.ExtensionAware).extensions.getByName("ext") as org.gradle.api.plugins.ExtraPropertiesExtension
