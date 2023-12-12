plugins {
    alias(exampleLibs.plugins.kotlin.noarg)
    alias(exampleLibs.plugins.kotlin.allopen)
    alias(exampleLibs.plugins.kotlin.jpa)
}

dependencies {
    implementation(exampleLibs.eclipselink2)
    implementation(exampleLibs.logback)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.eclipselinkJavaxSupport)

    runtimeOnly(exampleLibs.h2)

    testFixturesImplementation(exampleLibs.eclipselink2)
    testFixturesImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(11)
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.eclipselink.javax.annotation.CompositeId")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.eclipselink.javax.annotation.CompositeId")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
