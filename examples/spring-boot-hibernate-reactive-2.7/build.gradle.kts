@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.spring.boot)
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

apply(plugin = "org.springframework.boot")
apply(plugin = "kotlin-spring")
apply(plugin = "kotlin-jpa")

coverage {
    exclude(project)
}

dependencies {
    implementation(Modules.query)
    // implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-hibernate-reactive:x.y.z")
    implementation(Modules.springDataHibernateReactive)
    implementation(libs.hibernate.reactive)
    implementation(libs.coroutine.jdk8)
    implementation(libs.coroutine.reactor)
    implementation(libs.bundles.mutiny)

    implementation(Modules.testFixtureHibernateReactive)

    implementation(libs.spring.boot.webflux)
    implementation(libs.spring.boot.jpa)
    implementation(libs.jackson.kotlin.module)
    implementation(libs.h2)
    implementation(platform(libs.spring.boot.bom))

    testImplementation(libs.spring.boot.test)
}
