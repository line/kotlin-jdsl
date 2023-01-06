@Suppress("DSL_SCOPE_VIOLATION")
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
    // implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter:x.y.z")
    implementation(Modules.springDataStarterJakarta)

    implementation(libs.spring.boot3.web)
    implementation(libs.spring.boot3.jpa)
    implementation(libs.jackson.kotlin.module)
    implementation(libs.h2)
    implementation(platform(libs.spring.boot3.bom))

    testImplementation(libs.spring.boot3.test)
}
