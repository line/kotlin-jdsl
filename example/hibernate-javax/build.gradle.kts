import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(exampleLibs.plugins.kotlin.noarg)
    alias(exampleLibs.plugins.kotlin.allopen)
    alias(exampleLibs.plugins.kotlin.jpa)
}

dependencies {
    implementation(exampleLibs.hibernate5.core)
    implementation(exampleLibs.logback)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.hibernateJavaxSupport)

    runtimeOnly(exampleLibs.h2)

    testFixturesImplementation(exampleLibs.hibernate5.core)
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
    annotation("com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.annotation.CompositeId")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.annotation.CompositeId")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
}

kover {
    excludeInstrumentation {
        packages("org.hibernate.*")
    }
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
