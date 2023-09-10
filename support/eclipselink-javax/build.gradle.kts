@file:Suppress("VulnerableLibrariesLocal")

dependencies {
    compileOnly(libs.eclipselink2)
    compileOnly(libs.javax.persistence.api)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)

    testImplementation(libs.eclipselink2)
    testImplementation(libs.javax.persistence.api)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(8)
}
