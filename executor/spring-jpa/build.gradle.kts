dependencies {
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(libs.spring.jpa3)
    compileOnly(projects.jpqlQuery)
    compileOnly(projects.sqlQuery)
    compileOnly(projects.jpqlRender)
    compileOnly(projects.sqlRender)
}
