apply<PublishPlugin>()

dependencies {
    compileOnly(Dependencies.springJpa)
    compileOnly(Dependencies.hibernateReactive)

    api(Modules.reactiveCore)
    api(Modules.hibernateReactive)
    api(Modules.springDataReactiveCore)
    implementation(Dependencies.coroutineJdk8)
    implementation(Dependencies.javaPersistenceApi)

    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntity)
    testImplementation(Modules.testFixtureHibernateReactive)
    testImplementation(Modules.testFixtureIntegrationReactive)
    testImplementation(Dependencies.springBootTest)
    testImplementation(Dependencies.springJpa)
    testImplementation(Dependencies.hibernateReactive)
    testImplementation(Dependencies.h2)
}
