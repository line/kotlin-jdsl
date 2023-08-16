plugins {
    alias(libs.plugins.spring.boot2)
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    implementation(libs.spring.boot2.jpa)
    implementation(libs.spring.boot2.p6spy)
    implementation(projects.example)
    implementation(projects.bundleJpqlSpringDataJpaJavax)

    testImplementation(libs.spring.boot2.test)

    testRuntimeOnly(libs.h2)
}

kotlin {
    jvmToolchain(8)
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.annotation.CompositeId")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.annotation.CompositeId")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
}
