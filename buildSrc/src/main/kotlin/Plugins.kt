import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class AbstractPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.initialize()
    }

    protected abstract fun Project.initialize()
}

abstract class AbstractRootPlugin(
    private val apply: Project.() -> Unit
) : Plugin<Project> {
    override fun apply(target: Project) {
        if (target == target.rootProject) {
            target.apply()
        } else {
            throw IllegalStateException("RootPlugin must be applied in root scope")
        }
    }
}

fun apply(apply: Project.() -> Unit) = apply

fun Project.rootProject(action: Project.() -> Unit) {
    if (this != rootProject) throw IllegalStateException("rootProject block must be used in root scope")

    this.action()
}
