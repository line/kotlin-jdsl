dependencies {
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(libs.spring.data.jpa)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.jpqlRender)
    compileOnly(projects.sqlQueryModel)
    compileOnly(projects.sqlRender)
}
