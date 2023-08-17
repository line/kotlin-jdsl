dependencies {
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(libs.javax.persistence.api)

    api(projects.render)
    api(projects.queryModelJpql)

    implementation(libs.kotlin.reflect)

    testImplementation(testFixtures(projects.render))

    testFixturesImplementation(libs.kotlin.reflect)
}
