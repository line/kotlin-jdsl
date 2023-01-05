apply<PublishPlugin>()

dependencies {
    compileOnly(Modules.coreJakarta)
    compileOnly(Modules.springDataCoreJakarta)
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(libs.spring6.beans)
    compileOnly(libs.spring.batch5.infrastructure)

    testImplementation(Modules.coreJakarta)
    testImplementation(Modules.springDataCoreJakarta)
    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntityJakarta)
    testImplementation(libs.jakarta.persistence.api)
    testImplementation(libs.h2)
    testImplementation(libs.spring6.beans)
    testImplementation(libs.spring.batch5.infrastructure)
    testImplementation(libs.spring.boot3.jpa)
    testImplementation(libs.spring.boot3.test)
}

coverage {
    exclude(project)
}
