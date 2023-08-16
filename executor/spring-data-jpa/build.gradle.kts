dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.spring.data.jpa3)
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(projects.dslJpql)
    compileOnly(projects.dslSql)
    compileOnly(projects.queryModelJpql)
    compileOnly(projects.queryModelSql)
    compileOnly(projects.renderJpql)
    compileOnly(projects.renderSql)

    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    testImplementation(libs.spring.data.jpa3)
    testImplementation(libs.jakarta.persistence.api)
    testImplementation(projects.dslJpql)
    testImplementation(projects.dslSql)
    testImplementation(projects.queryModelJpql)
    testImplementation(projects.queryModelSql)
    testImplementation(projects.renderJpql)
    testImplementation(projects.renderSql)
}

kotlin {
    jvmToolchain(17)
}
