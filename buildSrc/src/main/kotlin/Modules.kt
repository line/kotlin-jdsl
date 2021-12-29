import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

data class Module(
    val name: String
)

@Suppress("unused")
object Modules {
    val core = module(":kotlin-jdsl-core")
    val hibernate = module(":hibernate-kotlin-jdsl")

    val springDataCore = module(":spring-data-kotlin-jdsl-core")
    val springBatchInfrastructure = module(":spring-batch-kotlin-jdsl-infrastructure")
    val springDataAutoconfigure = module(":spring-data-kotlin-jdsl-autoconfigure")
    val springDataStarter = module(":spring-data-kotlin-jdsl-starter")

    val testFixtureCore = module(":test-fixture-core")
    val testFixtureEntity = module(":test-fixture-entity")
    val testFixtureIntegration = module(":test-fixture-integration")

    private fun module(name: String): Module = Module(name)
}

fun DependencyHandler.api(module: Module): Dependency? =
    add("api", this.project(module.name))

fun DependencyHandler.implementation(module: Module): Dependency? =
    add("implementation", this.project(module.name))

fun DependencyHandler.compileOnly(module: Module): Dependency? =
    add("compileOnly", this.project(module.name))

fun DependencyHandler.testImplementation(module: Module): Dependency? =
    add("testImplementation", this.project(module.name))