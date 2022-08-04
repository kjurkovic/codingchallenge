import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class DatasourceConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kjurkovic.android.library")
                apply("kjurkovic.android.hilt")
            }

            dependencies {
                add("implementation", project(":common:wrappers:dispatchers"))
                add("implementation", project(":data:common"))
                add("implementation", project(":data:database"))
                add("implementation", project(":networking:core"))
            }
        }
    }
}
