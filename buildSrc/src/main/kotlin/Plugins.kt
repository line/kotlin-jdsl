import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class AbstractPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.initialize()
    }

    protected abstract fun Project.initialize()
}
