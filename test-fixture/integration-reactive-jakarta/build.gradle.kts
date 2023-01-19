coverage {
    exclude(project)
}

dependencies {
    api(Modules.testFixtureCore)
    api(Modules.testFixtureEntityJakarta)
    api(libs.jakarta.persistence.api)
    api(libs.slf4j)
    api(Modules.reactiveCoreJakarta)

    implementation(libs.kotlin.reflect)
    implementation(libs.coroutine.jdk8)
}
