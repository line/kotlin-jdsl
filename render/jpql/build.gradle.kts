dependencies {
    api(projects.render)
    api(projects.queryModelJpql)

    implementation(libs.kotlin.reflect)

    compileOnly(libs.jakarta.persistence.api)
    compileOnly(libs.javax.persistence.api)

    testImplementation(testFixtures(projects.render))
}
