plugins {
    alias(libs.plugins.kotlin.benchmark)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(libs.kotlin.benchmark)
    implementation(libs.jakarta.persistence.api)
    implementation(projects.dslJpql)
    implementation(projects.renderJpql)
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
            param("size", 1000)
        }
    }
    targets {
        register("main")
    }
}
