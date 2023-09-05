@file:Suppress("VulnerableLibrariesLocal")

dependencies {
    compileOnly(libs.hibernate.reactive1)
    compileOnly(libs.javax.persistence.api)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)
    compileOnly(libs.bundles.mutiny)

    testImplementation(libs.test.assertJ)
    testImplementation(libs.test.mockk)
    testImplementation(libs.hibernate.reactive1)
    testImplementation(libs.javax.persistence.api)
    testImplementation(libs.bundles.mutiny)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(17)
}
