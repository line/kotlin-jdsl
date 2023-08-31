plugins {
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(libs.jakarta.persistence.api)
    implementation(libs.eclipselink.v4)
    implementation(libs.test.h2)
    implementation(projects.eclipselinkSupport)

    testRuntimeOnly(libs.test.h2)
}

kotlin {
    jvmToolchain(17)
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.eclipselink.annotation.CompositeId")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.eclipselink.annotation.CompositeId")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
