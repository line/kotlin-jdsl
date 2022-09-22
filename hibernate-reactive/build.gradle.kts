apply<PublishPlugin>()

dependencies {
    api(Modules.reactiveCore)

    compileOnly(libs.hibernate.reactive)
    compileOnly(libs.slf4j)
    compileOnly(libs.bundles.mutiny)

    testImplementation(libs.bundles.mutiny)
    testImplementation(libs.coroutine.jdk8)
    testImplementation(Modules.testFixtureIntegrationReactive)
    testImplementation(Modules.testFixtureHibernateReactive)
    testImplementation(libs.hibernate.reactive)
    testImplementation(libs.kotlin.reflect)
    testImplementation(libs.h2)
    testImplementation(libs.agroal.pool)
    testImplementation(libs.vertx.jdbc.client)
}
