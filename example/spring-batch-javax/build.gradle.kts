import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(exampleLegacyLibs.plugins.spring.boot2)
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    implementation(exampleLegacyLibs.spring.boot2.batch)
    implementation(exampleLegacyLibs.spring.boot2.jpa)
    implementation(exampleLegacyLibs.spring.boot2.p6spy)
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
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.annotation.CompositeId")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
