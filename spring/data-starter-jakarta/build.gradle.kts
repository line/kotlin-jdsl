apply<PublishPlugin>()

dependencies {
    api(Modules.hibernateJakarta)
    api(Modules.springDataCoreJakarta)
    api(Modules.springDataAutoconfigureJakarta)
    api(libs.spring.boot3.starter)
}

coverage {
    exclude(project)
}
