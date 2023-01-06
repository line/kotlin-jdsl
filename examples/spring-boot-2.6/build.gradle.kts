plugins {
    id("org.springframework.boot") version "2.6.9"
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

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(libs.jackson.kotlin.module)
    implementation(libs.h2)
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.6.9"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

