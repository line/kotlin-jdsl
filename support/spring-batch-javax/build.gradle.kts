dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.spring.boot2.starter)
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.spring.batch4.infrastructure)
    compileOnly(libs.javax.persistence.api)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)

    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    testImplementation(libs.spring.boot2.starter)
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    testImplementation(libs.spring.batch4.infrastructure)
    testImplementation(libs.javax.persistence.api)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(8)
}
