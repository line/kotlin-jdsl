apply<PublishPlugin>()

dependencies {
    api(Modules.query)

    compileOnly(libs.java.persistence.api)
    compileOnly(libs.slf4j)

    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntity)
    testImplementation(libs.java.persistence.api)
    testImplementation(libs.h2)
    testImplementation(libs.coroutine.jdk8)
}
