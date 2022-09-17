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
    // implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter:x.y.z")
    implementation(Modules.springDataStarter)

    implementation(libs.spring.boot.web)
    implementation(libs.spring.boot.jpa)
    implementation(libs.jackson.kotlin.module)
    implementation(libs.h2)
    implementation(platform(libs.spring.boot.bom))

    testImplementation(libs.spring.boot.test)
}
