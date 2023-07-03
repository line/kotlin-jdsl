plugins {
    `java-test-fixtures`
}

dependencies {
    api(projects.sqlDsl)
    api(projects.sqlQuery)
    api(projects.sqlRender)

    testImplementation(libs.junit)

    testFixturesApi(libs.assertJ)
}
