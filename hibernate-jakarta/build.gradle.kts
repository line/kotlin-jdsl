apply<PublishPlugin>()

dependencies {
    api(Modules.coreJakarta)

    compileOnly(libs.hibernate6)
    compileOnly(libs.slf4j)

    testImplementation(Modules.coreJakarta)
    testImplementation(Modules.testFixtureIntegrationJakarta)
    testImplementation(libs.hibernate6)
    testImplementation(libs.h2)
}

coverage {
    exclude(project)
}
