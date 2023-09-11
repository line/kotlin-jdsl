plugins {
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(libs.test.hibernate6.core)
    implementation(libs.logback)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.hibernateSupport)

    runtimeOnly(libs.test.h2)

    testFixturesImplementation(libs.test.hibernate6.core)
    testFixturesImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(17)
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.jpql.hibernate.annotation.CompositeId")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.jpql.hibernate.annotation.CompositeId")
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
