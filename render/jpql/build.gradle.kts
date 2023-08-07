dependencies {
    api(projects.render)
    api(projects.jpqlQueryModel)

    compileOnly(libs.jakarta.persistence.api)
    compileOnly(libs.javax.persistence.api)

    implementation(libs.kotlin.reflect)

    testImplementation(testFixtures(projects.render))

    testFixturesImplementation(testFixtures(projects.render))
}
