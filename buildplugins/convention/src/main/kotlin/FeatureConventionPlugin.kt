import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kjurkovic.android.library.compose")
                apply("kjurkovic.android.hilt")
            }

            val libs = extensions.getByType(VersionCatalogsExtension::class).named("libs")

            dependencies {
                add("implementation", libs.findDependency("androidx.activity.activityCompose").get())
                add("implementation", libs.findDependency("coil.compose").get())
                add("implementation", libs.findDependency("hilt.navigationCompose").get())
                add("implementation", project(":common:framework"))
                add("implementation", project(":common:ui"))
                add("implementation", project(":common:wrappers:dispatchers"))
                add("implementation", project(":data:common"))
            }
        }
    }
}
