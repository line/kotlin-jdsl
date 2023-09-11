plugins {
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(libs.test.eclipselink2)
    implementation(libs.logback)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.eclipselinkJavaxSupport)

    runtimeOnly(libs.test.h2)

    testFixturesImplementation(libs.test.eclipselink2)
    testFixturesImplementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(8)
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
