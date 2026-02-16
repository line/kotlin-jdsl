pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "spring-batch6-support"

dependencyResolutionManagement {
    versionCatalogs {
        create("rootLibs") {
            from(files("../../libs.versions.toml"))
        }
    }
}

includeBuild("../../") {
    dependencySubstitution {
        substitute(module("com.linecorp.kotlin-jdsl:kotlin-jdsl")).using(project(":"))
        substitute(module("com.linecorp.kotlin-jdsl:jpql-dsl")).using(project(":jpql-dsl"))
        substitute(module("com.linecorp.kotlin-jdsl:jpql-query-model")).using(project(":jpql-query-model"))
        substitute(module("com.linecorp.kotlin-jdsl:jpql-render")).using(project(":jpql-render"))
    }
}
