apply<PublishPlugin>()

dependencies {
    compileOnly(Modules.core)
    compileOnly(libs.spring.jpa)
    compileOnly(libs.hibernate)

    testImplementation(Modules.core)
    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntity)
    testImplementation(libs.spring.boot.test)
    testImplementation(libs.spring.jpa)
    testImplementation(libs.hibernate)
    testImplementation(libs.h2)
}
