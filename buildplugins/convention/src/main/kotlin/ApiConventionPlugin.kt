import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class ApiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kjurkovic.android.library")
                apply("kjurkovic.android.hilt")
            }

            val libs = extensions.getByType(VersionCatalogsExtension::class).named("libs")

            dependencies {
                add("implementation", libs.findDependency("moshi.core").get())
                add("implementation", libs.findDependency("retrofit.converterMoshi").get())
                add("implementation", libs.findDependency("retrofit.core").get())
                add("implementation", project(":data:common"))
                add("implementation", project(":networking:core"))
                add("kapt", libs.findDependency("moshi.kotlinCodegen").get())
            }
        }
    }
}
