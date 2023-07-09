dependencies {
    api(projects.sqlDsl)
    api(projects.sqlQueryModel)
    api(projects.sqlRender)

    testImplementation(projects.springJpaExecutor)
    testImplementation(projects.springJdbcExecutor)
    testImplementation(libs.jakarta.persistence.api)
    testImplementation(libs.spring.jpa3)
}
