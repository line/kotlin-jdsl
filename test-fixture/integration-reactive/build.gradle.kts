coverage {
    exclude(project)
}

dependencies {
    api(Modules.testFixtureCore)
    api(Modules.testFixtureEntity)
    api(Dependencies.javaPersistenceApi)
    api(Dependencies.slf4j)
    api(Modules.reactiveCore)

    implementation(Dependencies.kotlinReflect)
    implementation(Dependencies.coroutineJdk8)
}
