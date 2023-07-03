plugins {
    `java-test-fixtures`
}

dependencies {
    api(projects.dsl)
    api(projects.sqlQuery)

    testImplementation(libs.junit)

    testFixturesApi(libs.assertJ)
}
