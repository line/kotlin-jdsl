plugins {
    alias(libs.plugins.spring.boot3)

    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)

    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(projects.example)
    implementation(projects.bundleJpqlSpringJpa)
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    implementation(libs.spring.boot3.jpa)
    implementation(libs.spring.boot3.p6spy)

    testImplementation(libs.spring.boot3.test)

    testRuntimeOnly(libs.h2)
}

kotlin {
    jvmToolchain(17)
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.jpql.spring.jpa.annotation.CompositeId")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.jpql.spring.jpa.annotation.CompositeId")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
}
