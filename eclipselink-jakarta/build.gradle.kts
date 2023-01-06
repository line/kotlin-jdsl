apply<PublishPlugin>()

dependencies {
    api(Modules.coreJakarta)

    compileOnly(libs.eclipselink.v4)
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(libs.slf4j)

    testImplementation(Modules.testFixtureIntegrationJakarta)
    testImplementation(libs.eclipselink.v4)
    testImplementation(libs.jakarta.persistence.api)
    testImplementation(libs.h2)
}

coverage {
    exclude(project)
}
