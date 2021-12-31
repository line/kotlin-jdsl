apply<PublishPlugin>()

dependencies {
    api(Modules.core)

    compileOnly(Dependencies.eclipselink)
    compileOnly(Dependencies.javaPersistenceApi)
    compileOnly(Dependencies.slf4j)

    testImplementation(Modules.core)
    testImplementation(Modules.testFixtureIntegration)
    testImplementation(Dependencies.eclipselink)
    testImplementation(Dependencies.javaPersistenceApi)
    testImplementation(Dependencies.h2)
}
