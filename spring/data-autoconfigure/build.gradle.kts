apply<PublishPlugin>()

dependencies {
    compileOnly(Modules.core)
    compileOnly(Modules.springDataCore)
    compileOnly(libs.java.persistence.api)
    compileOnly(libs.spring.boot.autoconfigure)
}
