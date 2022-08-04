import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.kapt")
            }

            val libs = extensions.getByType(VersionCatalogsExtension::class).named("libs")

            dependencies {
                add("implementation", libs.findDependency("hilt.android").get())
                add("kapt", libs.findDependency("hilt.androidCompiler").get())
            }
        }
    }
}
