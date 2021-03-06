plugins {
    id("org.springframework.boot") version "2.6.8"
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

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:")
    implementation(Dependencies.jacksonKotlinModule)
    implementation(Dependencies.h2)
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.6.8"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
