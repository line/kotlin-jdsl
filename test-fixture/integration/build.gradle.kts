coverage {
    exclude(project)
}

dependencies {
    api(Modules.testFixtureCore)
    api(Modules.testFixtureEntity)
    api(libs.java.persistence.api)
    api(libs.slf4j)

    implementation(libs.kotlin.reflect)

    compileOnly(Modules.springDataCore)
    compileOnly(Modules.core)
}
