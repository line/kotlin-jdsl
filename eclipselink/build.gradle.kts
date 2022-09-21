apply<PublishPlugin>()

dependencies {
    api(Modules.core)

    compileOnly(libs.eclipselink)
    compileOnly(libs.java.persistence.api)
    compileOnly(libs.slf4j)

    testImplementation(Modules.core)
    testImplementation(Modules.testFixtureIntegration)
    testImplementation(libs.eclipselink)
    testImplementation(libs.java.persistence.api)
    testImplementation(libs.h2)
}
