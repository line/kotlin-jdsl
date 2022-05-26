rootProject.name = "kotlin-jdsl"

val modules: MutableList<Module> = mutableListOf()

fun module(name: String, path: String) {
    modules.add(Module(name, "${rootDir}/${path}"))
}

data class Module(
    val name: String,
    val path: String,
)

// CORE
module(name = ":kotlin-jdsl-core", path = "core")
module(name = ":kotlin-jdsl-reactive-core", path = "reactive-core")
module(name = ":hibernate-kotlin-jdsl", path = "hibernate")
module(name = ":hibernate-reactive-kotlin-jdsl", path = "hibernate-reactive")
module(name = ":eclipselink-kotlin-jdsl", path = "eclipselink")
module(name = ":kotlin-jdsl-query", path = "query")

// SPRING
module(name = ":spring", path = "spring")
module(name = ":spring-data-kotlin-jdsl-core", path = "spring/data-core")
module(name = ":spring-data-kotlin-jdsl-reactive-core", path = "spring/data-reactive-core")
module(name = ":spring-data-kotlin-jdsl-hibernate-reactive", path = "spring/data-hibernate-reactive")
module(name = ":spring-batch-kotlin-jdsl-infrastructure", path = "spring/batch-infrastructure")
module(name = ":spring-data-kotlin-jdsl-autoconfigure", path = "spring/data-autoconfigure")
module(name = ":spring-data-kotlin-jdsl-starter", path = "spring/data-starter")

// TEST FIXTURE
module(name = ":test-fixture-core", path = "test-fixture/core")
module(name = ":test-fixture-entity", path = "test-fixture/entity")
module(name = ":test-fixture-integration", path = "test-fixture/integration")
module(name = ":test-fixture-integration-reactive", path = "test-fixture/integration-reactive")
module(name = ":test-fixture-hibernate-reactive", path = "test-fixture/hibernate-reactive")

// EXAMPLES
module(name = ":hibernate-example", path = "examples/hibernate")
module(name = ":eclipselink-example", path = "examples/eclipselink")
module(name = ":spring-data-boot-2.6-example", path = "examples/spring-boot-2.6")
module(name = ":spring-data-boot-2.7-example", path = "examples/spring-boot-2.7")
module(name = ":spring-data-boot-hibernate-reactive-2.6-example", path = "examples/spring-boot-hibernate-reactive-2.6")
module(name = ":spring-data-boot-hibernate-reactive-2.7-example", path = "examples/spring-boot-hibernate-reactive-2.7")
module(name = ":spring-data-boot-2.5-example", path = "examples/spring-boot-2.5")
module(name = ":spring-data-boot-2.4-example", path = "examples/spring-boot-2.4")
module(name = ":spring-data-boot-2.3-example", path = "examples/spring-boot-2.3")

modules.forEach {
    include(it.name)
    project(it.name).projectDir = file(it.path)
}
