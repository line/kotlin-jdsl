coverage {
    exclude(project)
}

dependencies {
    implementation(Modules.hibernate)
    implementation(libs.hibernate)
    implementation(libs.logback)
    implementation(libs.h2)

    testImplementation(Modules.testFixtureCore)
}
