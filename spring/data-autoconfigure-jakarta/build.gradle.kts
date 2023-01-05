apply<PublishPlugin>()

dependencies {
    compileOnly(Modules.coreJakarta)
    compileOnly(Modules.springDataCoreJakarta)
    compileOnly(libs.jakarta.persistence.api)
    compileOnly(libs.spring.boot3.autoconfigure)
}

coverage {
    exclude(project)
}
