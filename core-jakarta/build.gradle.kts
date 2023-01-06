apply<PublishPlugin>()

dependencies {
    api(Modules.queryJakarta)

    compileOnly(libs.jakarta.persistence.api)
    compileOnly(libs.slf4j)
    compileOnly(libs.hibernate6)

    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntity)
    testImplementation(libs.jakarta.persistence.api)
    testImplementation(libs.hibernate6)
    testImplementation(libs.h2)
}

coverage {
    exclude(project)
}
