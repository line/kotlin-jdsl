apply<PublishPlugin>()

dependencies {
    api(Modules.hibernate)
    api(Modules.springDataCore)
    api(Modules.springDataAutoconfigure)
    api(Dependencies.springBootStarter)
}
