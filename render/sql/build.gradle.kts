dependencies {
    api(projects.render)
    api(projects.sqlQueryModel)

    testImplementation(testFixtures(projects.render))
}
