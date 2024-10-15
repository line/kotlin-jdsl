dependencies {
    compileOnly(libs.hibernate6.core)
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(libs.slf4j)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)

    testImplementation(libs.hibernate6.core)
    testImplementation(libs.jakarta.persistence.api)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(11)
}
