@file:Suppress("unused", "SpellCheckingInspection", "MemberVisibilityCanBePrivate")

import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    const val kotlinVersion = "1.6.10"
    const val springCoreVersion = "5.3.16"
    const val springBootVersion = "2.6.4"
    const val springDataJpaVersion = "2.6.2"
    const val coroutineVersion = "1.6.0"

    // kotlin
    const val koltin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"

    // Common
    const val javaPersistenceApi = "javax.persistence:javax.persistence-api:2.2"
    const val slf4j = "org.slf4j:slf4j-api:1.7.36"
    const val logback = "ch.qos.logback:logback-classic:1.2.10"
    const val hibernate = "org.hibernate:hibernate-core:5.6.5.Final"
    const val hibernateReactive = "org.hibernate.reactive:hibernate-reactive-core:1.1.3.Final"
    const val eclipselink = "org.eclipse.persistence:org.eclipse.persistence.jpa:2.7.10"
    const val jacksonKotlinModule = "com.fasterxml.jackson.module:jackson-module-kotlin"
    const val agroalPool = "io.agroal:agroal-pool:1.14"
    const val vertxJdbcClient = "io.vertx:vertx-jdbc-client:4.2.5"
    const val coroutineJdk8 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutineVersion"
    const val coroutineReactor = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutineVersion"

    // SpringBoot
    const val springBootStarter = "org.springframework.boot:spring-boot-starter:${springBootVersion}"
    const val springBootAutoconfigure = "org.springframework.boot:spring-boot-autoconfigure:${springBootVersion}"
    const val springBootConfigurationProcessor =
        "org.springframework.boot:spring-boot-configuration-processor:${springBootVersion}"
    const val springBootBom = "org.springframework.boot:spring-boot-dependencies:${springBootVersion}"

    const val springBootWeb = "org.springframework.boot:spring-boot-starter-web:${springBootVersion}"
    const val springBootWebflux = "org.springframework.boot:spring-boot-starter-webflux:${springBootVersion}"
    const val springBootJpa = "org.springframework.boot:spring-boot-starter-data-jpa:${springBootVersion}"
    const val springBootTest = "org.springframework.boot:spring-boot-starter-test:${springBootVersion}"

    // Spring
    const val springCore = "org.springframework:spring-core:${springCoreVersion}"
    const val springBeans = "org.springframework:spring-beans:${springCoreVersion}"
    const val springJpa = "org.springframework.data:spring-data-jpa:${springDataJpaVersion}"
    const val springBatchInfrastructure = "org.springframework.batch:spring-batch-infrastructure:4.3.5"

    // Test
    const val junit = "org.junit.jupiter:junit-jupiter:5.8.2"
    const val assertJ = "org.assertj:assertj-core:3.22.0"
    const val mockk = "io.mockk:mockk:1.12.2"

    const val h2 = "com.h2database:h2:1.4.200"

    const val mutinyVersion = "1.4.0"
    const val mutinyCore = "io.smallrye.reactive:mutiny:$mutinyVersion"
    const val mutinyKotlin = "io.smallrye.reactive:mutiny-kotlin:$mutinyVersion"
    val mutiny = listOf(mutinyCore, mutinyKotlin)
}

fun DependencyHandler.api(dependencies: List<Any>) {
    dependencies.forEach {
        add("api", it)
    }
}

fun DependencyHandler.implementation(dependencies: List<Any>) {
    dependencies.forEach {
        add("implementation", it)
    }
}

fun DependencyHandler.compileOnly(dependencies: List<Any>) {
    dependencies.forEach {
        add("compileOnly", it)
    }
}

fun DependencyHandler.testImplementation(dependencies: List<String>) {
    dependencies.forEach {
        add("testImplementation", it)
    }
}
