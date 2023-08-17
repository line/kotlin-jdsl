@file:Suppress("VulnerableLibrariesLocal")

dependencies {
    compileOnly(libs.spring.boot2.starter)
    compileOnly(libs.spring.data.jpa2)
    compileOnly(libs.javax.persistence.api)
    compileOnly(projects.dslJpql)
    compileOnly(projects.queryModelJpql)
    compileOnly(projects.renderJpql)

    testImplementation(libs.spring.boot2.starter)
    testImplementation(libs.spring.data.jpa2)
    testImplementation(libs.javax.persistence.api)
    testImplementation(projects.dslJpql)
    testImplementation(projects.queryModelJpql)
    testImplementation(projects.renderJpql)
}

kotlin {
    jvmToolchain(8)
}
