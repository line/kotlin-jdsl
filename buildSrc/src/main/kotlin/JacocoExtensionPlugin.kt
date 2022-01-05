import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.*
import org.gradle.testing.jacoco.tasks.JacocoReport

@Suppress("UNUSED_VARIABLE")
class JacocoExtensionPlugin : AbstractRootPlugin(apply {
    val coverage = rootProject.extensions.create<CoverageExtension>("coverage")
    // Task to gather code coverage from multiple subprojects
    // @see https://gist.github.com/aalmiray/e6f54aa4b3803be0bcac
    val codeCoverageReport: TaskProvider<JacocoReport> by tasks.registering(JacocoReport::class) {
        group = "verification"
        dependsOn(subprojects.map { it.tasks.withType<Test>() })

        val targetProjects = subprojects.filter { it.project !in coverage.excludes }
        additionalSourceDirs.setFrom(targetProjects.map { it.the<SourceSetContainer>()["main"].allSource.srcDirs })
        sourceDirectories.setFrom(targetProjects.map { it.the<SourceSetContainer>()["main"].allSource.srcDirs })
        classDirectories.setFrom(targetProjects.map { it.the<SourceSetContainer>()["main"].output })
        executionData.setFrom(project.fileTree(".") {
            include("**/build/jacoco/test.exec")
        })

        reports {
            xml.required.set(true)
            html.required.set(false)
            csv.required.set(false)
        }
    }
})


@Suppress("MemberVisibilityCanBePrivate")
open class CoverageExtension {
    private val _excludes: MutableSet<Project> = mutableSetOf()

    val excludes: Set<Project>
        get() = _excludes

    fun exclude(project: Project) {
        _excludes.add(project)
    }
}

fun Project.coverage(customize: CoverageExtension.() -> Unit) {
    rootProject.configure(customize)
}

