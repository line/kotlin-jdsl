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
module(name = ":dsl", path = "dsl")
module(name = ":dsl-jpql", path = "dsl/jpql")

module(name = ":query-model", path = "query-model")
module(name = ":query-model-jpql", path = "query-model/jpql")

module(name = ":render", path = "render")
module(name = ":render-jpql", path = "render/jpql")

module(name = ":support", path = "support")
module(name = ":support-spring-data-jpa", path = "support/spring-data-jpa")
module(name = ":support-spring-data-jpa-javax", path = "support/spring-data-jpa-javax")

module(name = ":example", path = "example")
module(name = ":example-jpql-jpa", path = "example/jpql-jpa")
module(name = ":example-jpql-spring-data-jpa", path = "example/jpql-spring-data-jpa")
module(name = ":example-jpql-spring-data-jpa-javax", path = "example/jpql-spring-data-jpa-javax")

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
