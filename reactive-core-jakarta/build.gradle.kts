apply<PublishPlugin>()

coverage {
    exclude(project)
}

dependencies {
    api(Modules.queryJakarta)

    compileOnly(libs.jakarta.persistence.api)
    compileOnly(libs.slf4j)

    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntityJakarta)
    testImplementation(libs.jakarta.persistence.api)
    testImplementation(libs.h2)
    testImplementation(libs.coroutine.jdk8)
}
