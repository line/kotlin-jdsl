plugins {
    id("org.springframework.boot") version Dependencies.springBootVersion
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
    implementation(Dependencies.hibernateReactive)
    implementation(Dependencies.coroutineJdk8)
    implementation(Dependencies.coroutineReactor)
    implementation(Dependencies.mutiny)

    implementation(Modules.testFixtureHibernateReactive)

    implementation(Dependencies.springBootWebflux)
    implementation(Dependencies.springBootJpa)
    implementation(Dependencies.jacksonKotlinModule)
    implementation(Dependencies.h2)
    implementation(platform(Dependencies.springBootBom))

    testImplementation(Dependencies.springBootTest)
}
