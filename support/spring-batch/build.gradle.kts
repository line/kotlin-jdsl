dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.spring.boot3.starter)
    compileOnly(libs.spring.batch5.infrastructure)
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)

    testImplementation(libs.spring.batch5.infrastructure)
    testImplementation(libs.spring.beans6)
    testImplementation(libs.jakarta.persistence.api)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(17)
}
