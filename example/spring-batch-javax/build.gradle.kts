import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    `java-library`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    implementation(platform(exampleLegacyLibs.spring.boot2.bom))

    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    implementation(exampleLegacyLibs.spring.boot2.batch)
    implementation(exampleLegacyLibs.spring.boot2.jpa)
    implementation(exampleLegacyLibs.spring.boot2.p6spy)
    implementation(exampleLegacyLibs.javax.persistence.api)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.springBatchJavaxSupport)

    runtimeOnly(exampleLibs.h2)

    testImplementation(exampleLegacyLibs.spring.boot2.test)
    testImplementation(exampleLegacyLibs.spring.batch4.test)
}

kotlin {
    jvmToolchain(11)

    compilerOptions {
        apiVersion = KotlinVersion.KOTLIN_1_9
        languageVersion = KotlinVersion.KOTLIN_1_9
    }
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.annotation.CompositeId")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.annotation.CompositeId")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
