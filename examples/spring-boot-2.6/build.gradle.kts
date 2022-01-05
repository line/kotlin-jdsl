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
    // implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter:x.y.z")
    implementation(Modules.springDataStarter)

    implementation(Dependencies.springBootWeb)
    implementation(Dependencies.springBootJpa)
    implementation(Dependencies.jacksonKotlinModule)
    implementation(Dependencies.h2)
    implementation(platform(Dependencies.springBootBom))

    testImplementation(Dependencies.springBootTest)
}
