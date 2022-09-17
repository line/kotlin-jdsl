apply<PublishPlugin>()

dependencies {
    compileOnly(Modules.core)
    compileOnly(Modules.springDataCore)
    compileOnly(libs.java.persistence.api)
    compileOnly(libs.spring.beans)
    compileOnly(libs.spring.batch.infrastructure)

    testImplementation(Modules.core)
    testImplementation(Modules.springDataCore)
    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntity)
    testImplementation(libs.java.persistence.api)
    testImplementation(libs.h2)
    testImplementation(libs.spring.beans)
    testImplementation(libs.spring.batch.infrastructure)
    testImplementation(libs.spring.boot.jpa)
    testImplementation(libs.spring.boot.test)
}
