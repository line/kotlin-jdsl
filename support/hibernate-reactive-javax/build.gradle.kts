dependencies {
    compileOnly(libs.hibernate.reactive1.core)
    compileOnly(libs.javax.persistence.api)
    compileOnly(libs.slf4j)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)

    testImplementation(libs.hibernate.reactive1.core)
    testImplementation(libs.javax.persistence.api)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(11)
}
