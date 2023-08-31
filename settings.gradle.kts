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
module(name = ":jpql-dsl", path = "dsl/jpql")

module(name = ":query-model", path = "query-model")
module(name = ":jpql-query-model", path = "query-model/jpql")

module(name = ":render", path = "render")
module(name = ":jpql-render", path = "render/jpql")

module(name = ":support", path = "support")
module(name = ":spring-data-jpa-support", path = "support/spring-data-jpa")
module(name = ":spring-data-jpa-javax-support", path = "support/spring-data-jpa-javax")

module(name = ":example", path = "example")
module(name = ":spring-data-jpa-example", path = "example/spring-data-jpa")
module(name = ":spring-data-jpa-javax-example", path = "example/spring-data-jpa-javax")

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
