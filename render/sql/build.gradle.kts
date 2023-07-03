dependencies {
    api(projects.render)
    api(projects.sqlQuery)

    testImplementation(testFixtures(projects.render))
}
