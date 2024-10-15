dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.spring.boot3.starter)
    compileOnly(libs.spring.batch5.infrastructure)
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(libs.slf4j)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)

    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    testImplementation(libs.spring.boot3.starter)
    testImplementation(libs.spring.batch5.infrastructure)
    testImplementation(libs.jakarta.persistence.api)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(17)
}
