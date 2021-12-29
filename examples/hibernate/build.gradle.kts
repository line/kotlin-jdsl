dependencies {
    implementation(Modules.hibernate)
    implementation(Dependencies.hibernate)
    implementation(Dependencies.logback)
    implementation(Dependencies.h2)

    testImplementation(Modules.testFixtureCore)
}