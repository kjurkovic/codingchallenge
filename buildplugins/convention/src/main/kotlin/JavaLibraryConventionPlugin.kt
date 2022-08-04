import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class JavaLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("java-library")
                apply("org.jetbrains.kotlin.jvm")
            }
        }
    }
}
