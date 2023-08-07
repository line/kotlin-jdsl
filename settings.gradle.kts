rootProject.name = "kotlin-jdsl"

// Module
module(name = ":bundle", path = "bundle")
module(name = ":jpql-jpa-bundle", path = "bundle/jpql-jpa")
module(name = ":jpql-spring-jpa-bundle", path = "bundle/jpql-spring-jpa")

module(name = ":dsl", path = "dsl")
module(name = ":jpql-dsl", path = "dsl/jpql")
module(name = ":sql-dsl", path = "dsl/sql")

module(name = ":query-model", path = "query-model")
module(name = ":jpql-query-model", path = "query-model/jpql")
module(name = ":sql-query-model", path = "query-model/sql")

module(name = ":render", path = "render")
module(name = ":jpql-render", path = "render/jpql")
module(name = ":sql-render", path = "render/sql")

module(name = ":executor", path = "executor")
module(name = ":jdbc-executor", path = "executor/jdbc")
module(name = ":spring-jdbc-executor", path = "executor/spring-jdbc")
module(name = ":spring-jpa-executor", path = "executor/spring-jpa")

module(name = ":example", path = "example")
module(name = ":jpql-jpa-example", path = "example/jpql-jpa")
module(name = ":jpql-spring-jpa-example", path = "example/jpql-spring-jpa")

// Version Catalog
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")






// Util
fun module(name: String, path: String) {
    include(name)
    project(name).projectDir = file(path)
}
