apply<PublishPlugin>()

dependencies {
    api(Modules.reactiveCore)

    compileOnly(Dependencies.hibernateReactive)
    compileOnly(Dependencies.slf4j)
    compileOnly(Dependencies.mutiny)

    testImplementation(Dependencies.mutiny)
    testImplementation(Dependencies.coroutineJdk8)
    testImplementation(Modules.testFixtureIntegrationReactive)
    testImplementation(Modules.testFixtureHibernateReactive)
    testImplementation(Dependencies.hibernateReactive)
    testImplementation(Dependencies.kotlinReflect)
    testImplementation(Dependencies.h2)
    testImplementation(Dependencies.agroalPool)
    testImplementation(Dependencies.vertxJdbcClient)
}
