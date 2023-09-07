plugins {
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(libs.test.hibernate.reactive2.core)
    implementation(libs.test.vertx.jdbc.client)
    implementation(libs.test.agroal.pool)
    implementation(libs.logback)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.hibernateReactiveSupport)

    runtimeOnly(libs.test.h2)

    testFixturesImplementation(libs.test.hibernate.reactive2.core)
    testFixturesImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(11)
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
