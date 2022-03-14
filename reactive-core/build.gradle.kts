apply<PublishPlugin>()

dependencies {
    api(Modules.query)

    compileOnly(Dependencies.javaPersistenceApi)
    compileOnly(Dependencies.slf4j)

    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntity)
    testImplementation(Dependencies.javaPersistenceApi)
    testImplementation(Dependencies.h2)
    testImplementation(Dependencies.coroutineJdk8)
}
