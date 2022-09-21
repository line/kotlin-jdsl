apply<PublishPlugin>()

dependencies {
    compileOnly(libs.spring.jpa)
    compileOnly(libs.hibernate.reactive)
    compileOnly(libs.bundles.mutiny)

    api(Modules.reactiveCore)
    api(Modules.hibernateReactive)
    api(Modules.springDataReactiveCore)
    implementation(libs.java.persistence.api)

    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntity)
    testImplementation(Modules.testFixtureHibernateReactive)
    testImplementation(Modules.testFixtureIntegrationReactive)
    testImplementation(libs.bundles.mutiny)
    testImplementation(libs.spring.boot.test)
    testImplementation(libs.spring.jpa)
    testImplementation(libs.hibernate.reactive)
    testImplementation(libs.h2)
    testImplementation(libs.coroutine.jdk8)
}
