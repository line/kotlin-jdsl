plugins {

}

dependencies {
    api(projects.render)
    api(projects.queryModelSql)

    testImplementation(testFixtures(projects.render))
}
