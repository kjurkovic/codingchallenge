import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class RepoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kjurkovic.android.library")
            }

            dependencies {
                add("implementation", project(":data:common"))
            }
        }
    }
}
