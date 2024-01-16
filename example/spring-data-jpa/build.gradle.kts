import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(exampleLibs.plugins.spring.boot3)
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    implementation(exampleLibs.spring.boot3.jpa)
    implementation(exampleLibs.spring.boot3.p6spy)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.springDataJpaSupport)

    runtimeOnly(exampleLibs.h2)

    testImplementation(exampleLibs.spring.boot3.test)
}

kotlin {
    jvmToolchain(17)

    compilerOptions {
        apiVersion = KotlinVersion.KOTLIN_1_9
        languageVersion = KotlinVersion.KOTLIN_1_9
    }
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.annotation.CompositeId")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.annotation.CompositeId")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
