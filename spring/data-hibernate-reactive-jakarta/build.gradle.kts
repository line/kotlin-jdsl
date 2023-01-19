apply<PublishPlugin>()

coverage {
    exclude(project)
}

dependencies {
    compileOnly(libs.spring.jpa3)
    compileOnly(libs.hibernate.reactive.jakarta)
    compileOnly(libs.bundles.mutiny)

    api(Modules.reactiveCoreJakarta)
    api(Modules.hibernateReactiveJakarta)
    api(Modules.springDataReactiveCoreJakarta)
    implementation(libs.jakarta.persistence.api)

    testImplementation(Modules.testFixtureCore)
    testImplementation(Modules.testFixtureEntityJakarta)
    testImplementation(Modules.testFixtureHibernateReactiveJakarta)
    testImplementation(Modules.testFixtureIntegrationReactiveJakarta)
    testImplementation(libs.bundles.mutiny)
    testImplementation(libs.spring.boot3.test)
    testImplementation(libs.spring.jpa3)
    testImplementation(libs.hibernate.reactive.jakarta)
    testImplementation(libs.h2)
    testImplementation(libs.coroutine.jdk8)
}
