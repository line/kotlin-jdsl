plugins {
    alias(exampleLibs.plugins.test.spring.boot2)
    alias(exampleLibs.plugins.kotlin.noarg)
    alias(exampleLibs.plugins.kotlin.allopen)
    alias(exampleLibs.plugins.kotlin.spring)
    alias(exampleLibs.plugins.kotlin.jpa)
}

dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    implementation(exampleLibs.test.spring.boot2.batch)
    implementation(exampleLibs.test.spring.boot2.jpa)
    implementation(exampleLibs.test.spring.boot2.p6spy)
    implementation(projects.example)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
    implementation(projects.springBatchJavaxSupport)

    runtimeOnly(exampleLibs.test.h2)

    testImplementation(exampleLibs.test.spring.boot2.test)
    testImplementation(exampleLibs.test.spring.batch4.test)
}

kotlin {
    jvmToolchain(8)
}

noArg {
    annotation("com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.annotation.CompositeId")
}

allOpen {
    annotation("com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.annotation.CompositeId")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
