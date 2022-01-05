coverage {
    exclude(project)
}

dependencies {
    api(Modules.testFixtureCore)
    api(Modules.testFixtureEntity)
    api(Dependencies.javaPersistenceApi)
    api(Dependencies.slf4j)

    implementation(Dependencies.kotlinReflect)

    compileOnly(Modules.springDataCore)
    compileOnly(Modules.core)
}
