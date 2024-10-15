dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.hibernate5.core)
    compileOnly(libs.javax.persistence.api)
    compileOnly(libs.slf4j)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)

    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    testImplementation(libs.hibernate5.core)
    testImplementation(libs.javax.persistence.api)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(8)
}
