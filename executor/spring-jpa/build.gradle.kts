dependencies {
    compileOnly(libs.jakarta.persistence.api)
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.spring.data.jpa3)
    compileOnly(projects.queryModelJpql)
    compileOnly(projects.renderJpql)
    compileOnly(projects.queryModelSql)
    compileOnly(projects.renderSql)
}

kotlin {
    jvmToolchain(17)
}
