@file:Suppress("VulnerableLibrariesLocal")

dependencies {
    compileOnly(libs.eclipselink.v4)
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)

    testImplementation(libs.eclipselink.v4)
    testImplementation(libs.jakarta.persistence.api)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(17)
}
