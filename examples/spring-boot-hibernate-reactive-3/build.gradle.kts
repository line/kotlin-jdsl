plugins {
    alias(libs.plugins.spring.boot3)
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
    implementation(Modules.queryJakarta)
    // implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-hibernate-reactive-jakarta:x.y.z")
    implementation(Modules.springDataHibernateReactiveJakarta)
    implementation(libs.hibernate.reactive.jakarta)
    implementation(libs.coroutine.jdk8)
    implementation(libs.coroutine.reactor)
    implementation(libs.bundles.mutiny)

    implementation(Modules.testFixtureHibernateReactiveJakarta)

    implementation(libs.spring.boot.webflux)
    implementation(libs.spring.boot3.jpa)
    implementation(libs.jackson.kotlin.module)
    implementation(libs.h2)
    implementation(platform(libs.spring.boot3.bom))

    testImplementation(libs.spring.boot3.test)
}
