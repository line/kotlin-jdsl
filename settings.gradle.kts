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
module(name = ":hibernate-kotlin-jdsl", path = "hibernate")

// SPRING
module(name = ":spring", path = "spring")
module(name = ":spring-data-kotlin-jdsl-core", path = "spring/data-core")
module(name = ":spring-batch-kotlin-jdsl-infrastructure", path = "spring/batch-infrastructure")
module(name = ":spring-data-kotlin-jdsl-autoconfigure", path = "spring/data-autoconfigure")
module(name = ":spring-data-kotlin-jdsl-starter", path = "spring/data-starter")

// TEST FIXTURE
module(name = ":test-fixture", path = "test-fixture")
module(name = ":test-fixture-core", path = "test-fixture/core")
module(name = ":test-fixture-entity", path = "test-fixture/entity")
module(name = ":test-fixture-integration", path = "test-fixture/integration")

// EXAMPLES
module(name = ":hibernate-example", path = "examples/hibernate")
module(name = ":spring-data-boot-2.6-example", path = "examples/spring-boot-2.6")
module(name = ":spring-data-boot-2.5-example", path = "examples/spring-boot-2.5")
module(name = ":spring-data-boot-2.4-example", path = "examples/spring-boot-2.4")
module(name = ":spring-data-boot-2.3-example", path = "examples/spring-boot-2.3")

modules.forEach {
    include(it.name)
    project(it.name).projectDir = file(it.path)
}
