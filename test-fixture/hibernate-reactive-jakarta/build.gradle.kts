coverage {
    exclude(project)
}

dependencies {
    implementation(Modules.testFixtureEntityJakarta)
    implementation(libs.coroutine.jdk8)
    implementation(libs.hibernate.reactive.jakarta)
    implementation(libs.slf4j)
    implementation(libs.kotlin.reflect)
    implementation(libs.h2)
    implementation(libs.agroal.pool)
    implementation(libs.vertx.jdbc.client)
    implementation(libs.junit)
}
