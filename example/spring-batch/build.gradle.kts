plugins {
    alias(libs.plugins.test.spring.boot3)
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(libs.spring.boot3.batch)
    implementation(libs.test.spring.boot3.jpa)
    implementation(libs.test.spring.boot3.p6spy)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.springBatchSupport)

    testImplementation(libs.test.spring.boot3.test)
    testImplementation(libs.test.spring.batch)

    testRuntimeOnly(libs.test.h2)
}

kotlin {
    jvmToolchain(17)
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
