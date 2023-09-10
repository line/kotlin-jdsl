@file:Suppress("VulnerableLibrariesLocal")

dependencies {
    compileOnly(libs.hibernate5.core)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)

    testImplementation(libs.hibernate5.core)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(8)
}
