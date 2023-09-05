plugins {
    alias(libs.plugins.test.spring.boot2)
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    implementation(libs.test.h2)
    implementation(libs.hibernate.reactive1)
    implementation(libs.slf4j)
    implementation(libs.logback)
    implementation(libs.vertx.jdbc.client)
    implementation(libs.agroal.pool)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.hibernateReactiveJavaxSupport)
}

kotlin {
    jvmToolchain(17)
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax.entity.annotation.CompositeId")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax.entity.annotation.CompositeId")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
