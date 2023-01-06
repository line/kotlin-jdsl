apply<PublishPlugin>()

dependencies {
    compileOnly(libs.java.persistence.api)
    compileOnly(libs.slf4j)
    compileOnly(libs.hibernate)

    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntity)
    testImplementation(libs.java.persistence.api)
    testImplementation(libs.hibernate)
    testImplementation(libs.h2)
}

