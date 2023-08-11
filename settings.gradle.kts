rootProject.name = "kotlin-jdsl"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.6.0"
}

// Module
module(name = ":bundle", path = "bundle")
module(name = ":bundle-jpql-jpa", path = "bundle/jpql-jpa")
module(name = ":bundle-jpql-spring-jpa", path = "bundle/jpql-spring-jpa")
module(name = ":bundle-jpql-spring-jpa-javax", path = "bundle/jpql-spring-jpa-javax")

module(name = ":dsl", path = "dsl")
module(name = ":dsl-jpql", path = "dsl/jpql")
module(name = ":dsl-sql", path = "dsl/sql")

module(name = ":query-model", path = "query-model")
module(name = ":query-model-jpql", path = "query-model/jpql")
module(name = ":query-model-sql", path = "query-model/sql")

module(name = ":render", path = "render")
module(name = ":render-jpql", path = "render/jpql")
module(name = ":render-sql", path = "render/sql")

module(name = ":executor", path = "executor")
module(name = ":executor-spring-jpa", path = "executor/spring-jpa")
module(name = ":executor-spring-jpa-javax", path = "executor/spring-jpa-javax")

module(name = ":example", path = "example")
module(name = ":example-jpql-jpa", path = "example/jpql-jpa")
module(name = ":example-jpql-spring-jpa", path = "example/jpql-spring-jpa")
module(name = ":example-jpql-spring-jpa-javax", path = "example/jpql-spring-jpa-javax")

module(name = ":benchmark", path = "benchmark")

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
