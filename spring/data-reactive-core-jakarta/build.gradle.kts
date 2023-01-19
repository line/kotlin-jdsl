apply<PublishPlugin>()

coverage {
    exclude(project)
}

dependencies {
    compileOnly(libs.spring.jpa3)
    implementation(Modules.reactiveCoreJakarta)
    implementation(libs.jakarta.persistence.api)

    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntityJakarta)
    testImplementation(libs.spring.boot3.test)
    testImplementation(libs.spring.jpa3)
    testImplementation(libs.h2)
    testImplementation(libs.coroutine.jdk8)
}
