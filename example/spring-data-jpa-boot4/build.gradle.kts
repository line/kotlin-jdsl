plugins {
    alias(rootLibs.plugins.kotlin2.jvm)
    alias(exampleLibs.plugins.spring.boot4)
    alias(rootLibs.plugins.kotlin2.noarg)
    alias(rootLibs.plugins.kotlin2.allopen)
    alias(rootLibs.plugins.kotlin2.spring)
    alias(rootLibs.plugins.kotlin2.jpa)
    alias(rootLibs.plugins.ktlint5)
    alias(rootLibs.plugins.kover)
}

group = "com.linecorp.kotlin-jdsl"
version = "3.8.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(exampleLibs.spring.boot4.jpa)
    implementation(exampleLibs.spring.boot4.p6spy)
    implementation("com.linecorp.kotlin-jdsl:example")
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl")
    implementation("com.linecorp.kotlin-jdsl:jpql-render")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-boot4-support")

    runtimeOnly(exampleLibs.h2)

    testImplementation(exampleLibs.spring.boot4.test)
    testImplementation(rootLibs.mockk)
}

kotlin {
    jvmToolchain(17)
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.annotation.CompositeId")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.annotation.CompositeId")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
}

// Disable bootJar as we usually run tests or use it as a library reference
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    archiveClassifier.set("")
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
