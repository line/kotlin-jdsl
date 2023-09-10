plugins {
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(libs.test.hibernate5.core)
    implementation(libs.logback)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.hibernateJavaxSupport)

    runtimeOnly(libs.test.h2)

    testFixturesImplementation(libs.test.hibernate5.core)
    testFixturesImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(8)
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
