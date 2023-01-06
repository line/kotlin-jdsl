apply<PublishPlugin>()

dependencies {
    compileOnly(Modules.coreJakarta)
    compileOnly(libs.spring.jpa3)
    compileOnly(libs.hibernate6)

    testImplementation(Modules.coreJakarta)
    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntityJakarta)
    testImplementation(libs.spring.boot3.test)
    testImplementation(libs.spring.jpa3)
    testImplementation(libs.hibernate6)
    testImplementation(libs.h2)
}

coverage {
    exclude(project)
}
