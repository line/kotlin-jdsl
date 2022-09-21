apply<PublishPlugin>()

dependencies {
    compileOnly(libs.spring.jpa)
    implementation(Modules.reactiveCore)
    implementation(libs.java.persistence.api)

    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntity)
    testImplementation(libs.spring.boot.test)
    testImplementation(libs.spring.jpa)
    testImplementation(libs.h2)
    testImplementation(libs.coroutine.jdk8)
}
