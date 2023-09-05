plugins {
    alias(libs.plugins.test.spring.boot2)
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(libs.test.hibernate.reactive1.core)
    implementation(libs.test.vertx.jdbc.client)
    implementation(libs.test.agroal.pool)
    implementation(libs.logback)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.hibernateReactiveJavaxSupport)

    runtimeOnly(libs.test.h2)

    testFixturesImplementation(libs.test.hibernate.reactive1.core)
    testFixturesImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(11)
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.hibernate.reactive.javax.jpql.entity.annotation.CompositeId")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.hibernate.reactive.javax.jpql.entity.annotation.CompositeId")
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
