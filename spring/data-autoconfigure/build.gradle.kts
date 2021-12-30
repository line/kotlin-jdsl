apply<PublishPlugin>()

dependencies {
    compileOnly(Modules.core)
    compileOnly(Modules.springDataCore)
    compileOnly(Dependencies.javaPersistenceApi)
    compileOnly(Dependencies.springBootAutoconfigure)
}
