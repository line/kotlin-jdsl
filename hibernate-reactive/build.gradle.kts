apply<PublishPlugin>()

dependencies {
    api(Modules.reactiveCore)

    compileOnly(libs.hibernate.reactive)
    compileOnly(libs.slf4j)
    compileOnly(libs.bundles.mitiny)

    testImplementation(libs.bundles.mitiny)
    testImplementation(libs.coroutine.jdk8)
    testImplementation(Modules.testFixtureIntegrationReactive)
    testImplementation(Modules.testFixtureHibernateReactive)
    testImplementation(libs.hibernate.reactive)
    testImplementation(libs.kotlin.reflect)
    testImplementation(libs.h2)
    testImplementation(libs.agroal.pool)
    testImplementation(libs.vertx.jdbc.client)
}
