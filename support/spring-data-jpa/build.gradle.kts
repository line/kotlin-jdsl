@file:Suppress("VulnerableLibrariesLocal")

dependencies {
    compileOnly(libs.spring.boot3.starter)
    compileOnly(libs.spring.data.jpa3)
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(projects.dslJpql)
    compileOnly(projects.queryModelJpql)
    compileOnly(projects.renderJpql)

    testImplementation(libs.spring.boot3.starter)
    testImplementation(libs.spring.data.jpa3)
    testImplementation(libs.jakarta.persistence.api)
    testImplementation(projects.dslJpql)
    testImplementation(projects.queryModelJpql)
    testImplementation(projects.renderJpql)
}

kotlin {
    jvmToolchain(17)
}
