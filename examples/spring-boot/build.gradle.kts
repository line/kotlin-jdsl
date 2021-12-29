plugins {
    id("org.springframework.boot") version Dependencies.springBootVersion
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

apply(plugin = "org.springframework.boot")
apply(plugin = "kotlin-spring")
apply(plugin = "kotlin-jpa")

dependencies {
    implementation(Modules.springDataStarter)

    implementation(Dependencies.springBootWeb)
    implementation(Dependencies.springBootJpa)
    implementation(Dependencies.jacksonKotlinModule)
    implementation(Dependencies.h2)
    implementation(platform(Dependencies.springBootBom))

    testImplementation(Modules.testFixtureCore)
}