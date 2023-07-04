dependencies {
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(libs.spring.jpa3)
    compileOnly(projects.jpqlQueryModel)
    compileOnly(projects.sqlQueryModel)
    compileOnly(projects.jpqlRender)
    compileOnly(projects.sqlRender)
}
