apply<PublishPlugin>()

dependencies {
    api(Modules.core)

    compileOnly(Dependencies.hibernate)
    compileOnly(Dependencies.slf4j)

    testImplementation(Modules.core)
    testImplementation(Modules.testFixtureIntegration)
    testImplementation(Dependencies.hibernate)
    testImplementation(Dependencies.h2)
}
