dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.spring.boot2.starter)
    compileOnly(libs.spring.data.jpa2)
    compileOnly(libs.javax.persistence.api)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)

    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    testImplementation(libs.spring.boot2.starter)
    testImplementation(libs.spring.data.jpa2)
    testImplementation(libs.javax.persistence.api)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(8)
}
