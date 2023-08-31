dependencies {
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(libs.javax.persistence.api)
    compileOnly(libs.slf4j)

    api(projects.render)
    api(projects.jpqlQueryModel)

    implementation(libs.kotlin.reflect)

    testImplementation(libs.jakarta.persistence.api)
    testImplementation(libs.javax.persistence.api)
    testImplementation(testFixtures(projects.render))

    testFixturesImplementation(libs.kotlin.reflect)
}
