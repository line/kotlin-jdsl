apply<PublishPlugin>()

dependencies {
    compileOnly(Modules.core)
    compileOnly(Modules.springDataCore)
    compileOnly(Dependencies.javaPersistenceApi)
    compileOnly(Dependencies.springBeans)
    compileOnly(Dependencies.springBatchInfrastructure)

    testImplementation(Modules.core)
    testImplementation(Modules.springDataCore)
    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntity)
    testImplementation(Dependencies.javaPersistenceApi)
    testImplementation(Dependencies.h2)
    testImplementation(Dependencies.springBeans)
    testImplementation(Dependencies.springBatchInfrastructure)
    testImplementation(Dependencies.springBootJpa)
    testImplementation(Dependencies.springBootTest)
}
