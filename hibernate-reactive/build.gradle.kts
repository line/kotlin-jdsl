apply<PublishPlugin>()

dependencies {
    api(Modules.reactiveCore)
    implementation(Dependencies.coroutineJdk8)

    compileOnly(Dependencies.hibernateReactive)
    compileOnly(Dependencies.slf4j)
    compileOnly(Dependencies.mutiny)

    testImplementation(Dependencies.mutiny)
    testImplementation(Modules.testFixtureIntegrationReactive)
    testImplementation(Modules.testFixtureHibernateReactive)
    testImplementation(Dependencies.hibernateReactive)
    testImplementation(Dependencies.kotlinReflect)
    testImplementation(Dependencies.h2)
    testImplementation(Dependencies.agroalPool)
    testImplementation(Dependencies.vertxJdbcClient)
}
