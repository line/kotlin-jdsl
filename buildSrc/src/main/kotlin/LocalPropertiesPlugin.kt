import org.gradle.api.Project
import java.util.*

class LocalPropertiesPlugin : AbstractPlugin() {
    override fun Project.initialize() {
        val localProperties = rootProject.file("local.properties").takeIf { it.exists() } ?: return

        localProperties.reader().use {
            Properties().apply { load(it) }
        }.forEach { (name, value) ->
            ext[name.toString()] = value
        }
    }
}

private val Project.ext: org.gradle.api.plugins.ExtraPropertiesExtension
    get() = (this as org.gradle.api.plugins.ExtensionAware).extensions.getByName("ext") as org.gradle.api.plugins.ExtraPropertiesExtension
