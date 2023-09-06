@file:Suppress("VulnerableLibrariesLocal")

dependencies {
    compileOnly(libs.spring.boot3.starter)
    compileOnly(libs.jakarta.hibernate.core)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)

    testImplementation(libs.spring.boot3.starter)
    testImplementation(libs.jakarta.hibernate.core)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(17)
}
