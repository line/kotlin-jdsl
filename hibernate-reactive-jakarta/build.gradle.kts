apply<PublishPlugin>()

coverage {
    exclude(project)
}

dependencies {
    api(Modules.reactiveCoreJakarta)

    compileOnly(libs.hibernate.reactive.jakarta)
    compileOnly(libs.slf4j)
    compileOnly(libs.bundles.mutiny)

    testImplementation(libs.bundles.mutiny)
    testImplementation(libs.coroutine.jdk8)
    testImplementation(Modules.testFixtureIntegrationReactiveJakarta)
    testImplementation(Modules.testFixtureHibernateReactiveJakarta)
    testImplementation(libs.hibernate.reactive.jakarta)
    testImplementation(libs.kotlin.reflect)
    testImplementation(libs.h2)
    testImplementation(libs.agroal.pool)
    testImplementation(libs.vertx.jdbc.client)
}
