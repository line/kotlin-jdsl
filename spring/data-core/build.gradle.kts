dependencies {
    compileOnly(Modules.core)
    compileOnly(Dependencies.springJpa)
    compileOnly(Dependencies.hibernate)

    testImplementation(Modules.core)
    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntity)
    testImplementation(Dependencies.springBootTest)
    testImplementation(Dependencies.springJpa)
    testImplementation(Dependencies.hibernate)
    testImplementation(Dependencies.h2)
}