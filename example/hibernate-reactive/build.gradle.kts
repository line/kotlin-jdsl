import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(exampleLibs.plugins.kotlin.noarg)
    alias(exampleLibs.plugins.kotlin.allopen)
    alias(exampleLibs.plugins.kotlin.jpa)
}

dependencies {
    implementation(exampleLibs.hibernate.reactive2.core)
    implementation(exampleLibs.vertx.jdbc.client)
    implementation(exampleLibs.agroal.pool)
    implementation(exampleLibs.logback)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.hibernateReactiveSupport)

    runtimeOnly(exampleLibs.h2)

    testFixturesImplementation(exampleLibs.hibernate.reactive2.core)
    testFixturesImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(11)

    compilerOptions {
        apiVersion = KotlinVersion.KOTLIN_1_9
        languageVersion = KotlinVersion.KOTLIN_1_9
    }
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.hibernate.reactive.jakarta.jpql.entity.annotation.CompositeId")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.hibernate.reactive.jakarta.jpql.entity.annotation.CompositeId")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
}

kover {
    excludeInstrumentation {
        packages("org.hibernate.*")
    }
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
