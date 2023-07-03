rootProject.name = "kotlin-jdsl"

// Module
module(name = ":module", path = "module")

module(name = ":dsl", path = "dsl")
module(name = ":sql-dsl", path = "dsl/sql")

module(name = ":query", path = "query")
module(name = ":sql-query", path = "query/sql")

module(name = ":render", path = "render")
module(name = ":sql-render", path = "render/sql")

module(name = ":executor", path = "executor")

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
