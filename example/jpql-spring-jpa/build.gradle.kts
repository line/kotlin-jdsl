plugins {
    alias(libs.plugins.spring.boot)

    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)

    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(projects.example)
    implementation(projects.jpqlSpringJpaBundle)
    implementation(libs.spring.boot.jpa)

    implementation(libs.spring.boot.p6spy)

    testImplementation(libs.spring.boot.test)

    testRuntimeOnly(libs.h2)
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.jpql.springjpa.annotation.CompositeId")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.jpql.springjpa.annotation.CompositeId")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
}
