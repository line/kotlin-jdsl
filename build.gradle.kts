import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    jacoco

    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
}

allprojects {
    group = "com.linecorp.kotlin-jdsl"
    version = "2.0.6.RELEASE"

    repositories {
        mavenCentral()
    }
}

rootProject {
    apply<JacocoExtensionPlugin>()
}

subprojects {
    apply(plugin = "jacoco")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-noarg")
    apply(plugin = "kotlin-allopen")

    apply<LocalPropertiesPlugin>()

    val jdk11Required = name.contains("hibernate-reactive")
    val javaVersion = if (jdk11Required) JavaVersion.VERSION_11 else JavaVersion.VERSION_1_8
    java.sourceCompatibility = javaVersion
    java.targetCompatibility = javaVersion

    dependencies {
        implementation(rootProject.libs.koltin)
    }

    allOpen {
        annotation("org.springframework.context.annotation.Configuration")
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.Embeddable")
    }

    noArg {
        annotation("org.springframework.context.annotation.Configuration")
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.Embeddable")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
            jvmTarget = if (jdk11Required) "11" else "1.8"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            showExceptions = true
            exceptionFormat = FULL
            showCauses = true
            showStackTraces = true
            events = setOf(FAILED)
        }
    }
}
