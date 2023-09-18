plugins {
    alias(exampleLibs.plugins.kotlin.benchmark)
    alias(exampleLibs.plugins.kotlin.allopen)
    alias(exampleLibs.plugins.kotlin.spring)
    alias(exampleLibs.plugins.kotlin.jpa)
}

dependencies {
    implementation(exampleLibs.kotlin.benchmark)
    implementation(exampleLibs.jakarta.persistence.api)
    implementation(exampleLibs.logback)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
}

benchmark {
    configurations {
        named("main") {
            warmups = 10
            iterations = 10
            iterationTime = 1
            iterationTimeUnit = "sec"
        }
    }
    targets {
        register("main")
    }
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
