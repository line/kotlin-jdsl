import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

data class Module(
    val name: String
)

@Suppress("unused")
object Modules {
    val core = module(":kotlin-jdsl-core")
    val reactiveCore = module(":kotlin-jdsl-reactive-core")
    val hibernate = module(":hibernate-kotlin-jdsl")
    val hibernateReactive = module(":hibernate-reactive-kotlin-jdsl")
    val eclipselink = module(":eclipselink-kotlin-jdsl")
    val query = module(":kotlin-jdsl-query")
    val springDataCore = module(":spring-data-kotlin-jdsl-core")

    val springDataReactiveCore = module(":spring-data-kotlin-jdsl-reactive-core")

    val springDataHibernateReactive = module(":spring-data-kotlin-jdsl-hibernate-reactive")
    val springBatchInfrastructure = module(":spring-batch-kotlin-jdsl-infrastructure")
    val springDataAutoconfigure = module(":spring-data-kotlin-jdsl-autoconfigure")
    val springDataStarter = module(":spring-data-kotlin-jdsl-starter")
    val testFixtureCore = module(":test-fixture-core")

    val testFixtureEntity = module(":test-fixture-entity")
    val testFixtureIntegration = module(":test-fixture-integration")
    val testFixtureIntegrationReactive = module(":test-fixture-integration-reactive")
    val testFixtureHibernateReactive = module(":test-fixture-hibernate-reactive")

    // jakarta
    val coreJakarta = module(":kotlin-jdsl-core-jakarta")
    val queryJakarta = module(":kotlin-jdsl-query-jakarta")
    val hibernateJakarta = module(":hibernate-kotlin-jdsl-jakarta")

    // jakarta spring
    val springDataCoreJakarta = module(":spring-data-kotlin-jdsl-core-jakarta")
    val springBatchInfrastructureJakarta = module(":spring-batch-kotlin-jdsl-infrastructure-jakarta")
    val springDataAutoconfigureJakarta = module(":spring-data-kotlin-jdsl-autoconfigure-jakarta")
    val springDataStarterJakarta = module(":spring-data-kotlin-jdsl-starter-jakarta")

    val testFixtureIntegrationJakarta = module(":test-fixture-integration-jakarta")
    val testFixtureEntityJakarta = module(":test-fixture-entity-jakarta")

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
