apply<PublishPlugin>()

dependencies {
    compileOnly(Dependencies.springJpa)
    implementation(Modules.reactiveCore)
    implementation(Dependencies.javaPersistenceApi)

    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntity)
    testImplementation(Dependencies.springBootTest)
    testImplementation(Dependencies.springJpa)
    testImplementation(Dependencies.h2)
    testImplementation(Dependencies.coroutineJdk8)
}
