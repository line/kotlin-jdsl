plugins {
    id("org.springframework.boot") version "2.4.13"
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
    // implementation("com.linecorp.kotlin-jdsl:hibernate-kotlin-jdsl:x.y.z")
    implementation(Modules.hibernate)
    // implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-core:x.y.z")
    implementation(Modules.springDataCore)
    // implementation("spring-data-kotlin-jdsl-autoconfigure:x.y.z")
    implementation(Modules.springDataAutoconfigure)

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(libs.jackson.kotlin.module)
    implementation(libs.h2)
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${"2.4.13"}"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
