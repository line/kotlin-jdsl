dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.spring.data.jpa2)
    compileOnly(libs.javax.persistence.api)
    compileOnly(projects.dslJpql)
    compileOnly(projects.dslSql)
    compileOnly(projects.queryModelJpql)
    compileOnly(projects.renderJpql)
    compileOnly(projects.queryModelSql)
    compileOnly(projects.renderSql)

    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    testImplementation(libs.spring.data.jpa2)
    testImplementation(libs.javax.persistence.api)
    testImplementation(projects.dslJpql)
    testImplementation(projects.dslSql)
    testImplementation(projects.queryModelJpql)
    testImplementation(projects.renderJpql)
    testImplementation(projects.queryModelSql)
    testImplementation(projects.renderSql)
}

kotlin {
    jvmToolchain(8)
}
