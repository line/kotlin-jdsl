coverage {
    exclude(project)
}

dependencies {
    implementation(Modules.testFixtureEntity)
    implementation(libs.coroutine.jdk8)
    implementation(libs.hibernate.reactive)
    implementation(libs.slf4j)
    implementation(libs.kotlin.reflect)
    implementation(libs.h2)
    implementation(libs.agroal.pool)
    implementation(libs.vertx.jdbc.client)
    implementation(libs.junit)
}
