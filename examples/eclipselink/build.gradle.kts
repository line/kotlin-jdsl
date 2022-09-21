coverage {
    exclude(project)
}

dependencies {
    implementation(Modules.eclipselink)
    implementation(libs.eclipselink)
    implementation(libs.java.persistence.api)
    implementation(libs.logback)
    implementation(libs.h2)

    testImplementation(Modules.testFixtureCore)
}
