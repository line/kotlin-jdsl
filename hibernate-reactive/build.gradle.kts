apply<PublishPlugin>()

dependencies {
    api(Modules.reactiveCore)
    implementation(Dependencies.coroutineJdk8)
    implementation(Dependencies.mutiny)

    compileOnly(Dependencies.hibernateReactive)
    compileOnly(Dependencies.slf4j)

    testImplementation(Modules.testFixtureIntegrationReactive)
    testImplementation(Modules.testFixtureHibernateReactive)
    testImplementation(Dependencies.hibernateReactive)
    testImplementation(Dependencies.kotlinReflect)
    testImplementation(Dependencies.h2)
    testImplementation(Dependencies.agroalPool)
    testImplementation(Dependencies.vertxJdbcClient)
}
