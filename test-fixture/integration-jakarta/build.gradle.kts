coverage {
    exclude(project)
}

dependencies {
    api(Modules.testFixtureCore)
    api(Modules.testFixtureEntityJakarta)
    api(libs.jakarta.persistence.api)
    api(libs.slf4j)

    implementation(libs.kotlin.reflect)

    compileOnly(Modules.coreJakarta)
}
