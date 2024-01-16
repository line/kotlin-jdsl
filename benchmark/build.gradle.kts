import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.kotlin.benchmark)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(libs.kotlin.benchmark)
    implementation(exampleLibs.jakarta.persistence.api)
    implementation(exampleLibs.logback)
    implementation(projects.jpqlDsl)
    implementation(projects.jpqlRender)
}

kotlin {
    jvmToolchain(17)

    compilerOptions {
        apiVersion = KotlinVersion.KOTLIN_1_9
        languageVersion = KotlinVersion.KOTLIN_1_9
    }
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
