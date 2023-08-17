@file:Suppress("VulnerableLibrariesLocal")

dependencies {
    compileOnly(libs.spring.boot3.starter)
    compileOnly(libs.spring.data.jpa3)
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)

    testImplementation(libs.spring.boot3.starter)
    testImplementation(libs.spring.data.jpa3)
    testImplementation(libs.jakarta.persistence.api)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(17)
}
