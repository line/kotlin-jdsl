dependencies {
    compileOnly(libs.spring.boot2.starter)
    compileOnly(libs.spring.batch4.infrastructure)
    compileOnly(libs.javax.persistence.api)
    compileOnly(projects.jpqlDsl)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)

    testImplementation(libs.spring.batch4.infrastructure)
    testImplementation(libs.spring.beans5)
    testImplementation(libs.javax.persistence.api)
    testImplementation(projects.jpqlDsl)
    testImplementation(projects.jpqlQueryModel)
    testImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(8)
}
