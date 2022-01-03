dependencies {
    implementation(Modules.eclipselink)
    implementation(Dependencies.eclipselink)
    implementation(Dependencies.javaPersistenceApi)
    implementation(Dependencies.logback)
    implementation(Dependencies.h2)

    testImplementation(Modules.testFixtureCore)
}
