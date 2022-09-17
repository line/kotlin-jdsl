apply<PublishPlugin>()

dependencies {
    api(Modules.core)

    compileOnly(libs.hibernate)
    compileOnly(libs.slf4j)

    testImplementation(Modules.core)
    testImplementation(Modules.testFixtureIntegration)
    testImplementation(libs.hibernate)
    testImplementation(libs.h2)
}
