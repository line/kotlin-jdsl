@file:Suppress("VulnerableLibrariesLocal")

plugins {
    alias(libs.plugins.test.spring.boot3)
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(libs.test.spring.boot3.batch)
    implementation(libs.test.spring.boot3.jpa)
    implementation(libs.test.spring.boot3.p6spy)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.springBatchSupport)

    testImplementation(libs.test.spring.boot3.test)
    testImplementation(libs.test.spring.batch5.test)

    testRuntimeOnly(libs.test.h2)
}

kotlin {
    jvmToolchain(17)
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.spring.batch.jpql.annotation.CompositeId")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.spring.batch.jpql.annotation.CompositeId")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
