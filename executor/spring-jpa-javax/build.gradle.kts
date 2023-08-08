dependencies {
    compileOnly(libs.javax.persistence.api)
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.spring.data.jpa2)
    compileOnly(projects.queryModelJpql)
    compileOnly(projects.renderJpql)
    compileOnly(projects.queryModelSql)
    compileOnly(projects.renderSql)
}

kotlin {
    jvmToolchain(8)
}
