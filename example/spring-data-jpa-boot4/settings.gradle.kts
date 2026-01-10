pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "spring-data-jpa-boot4-example"

dependencyResolutionManagement {
    versionCatalogs {
        create("rootLibs") {
            from(files("../../libs.versions.toml"))
        }
        create("exampleLibs") {
            from(files("../../libs.example.versions.toml"))
        }
    }
}

includeBuild("../../") {
    dependencySubstitution {
        substitute(module("com.linecorp.kotlin-jdsl:example")).using(project(":example"))
        substitute(module("com.linecorp.kotlin-jdsl:kotlin-jdsl")).using(project(":"))
        substitute(module("com.linecorp.kotlin-jdsl:jpql-dsl")).using(project(":jpql-dsl"))
        substitute(module("com.linecorp.kotlin-jdsl:jpql-query-model")).using(project(":jpql-query-model"))
        substitute(module("com.linecorp.kotlin-jdsl:jpql-render")).using(project(":jpql-render"))
    }
}

includeBuild("../../support/spring-data-jpa-boot4") {
    dependencySubstitution {
        substitute(module("com.linecorp.kotlin-jdsl:spring-data-jpa-boot4-support")).using(project(":"))
    }
}
