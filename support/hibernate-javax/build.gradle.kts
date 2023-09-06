@file:Suppress("VulnerableLibrariesLocal")

dependencies {
    compileOnly(libs.spring.boot2.starter)
    compileOnly(libs.javax.hibernate.core)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)

    testImplementation(libs.spring.boot2.starter)
    testImplementation(libs.javax.hibernate.core)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(8)
}
