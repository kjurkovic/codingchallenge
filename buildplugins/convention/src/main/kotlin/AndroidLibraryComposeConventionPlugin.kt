import com.android.build.gradle.LibraryExtension
import com.kjurkovic.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kjurkovic.android.library")
            }

            extensions.configure<LibraryExtension> {
                configureAndroidCompose(this)
            }

            val libs = extensions.getByType(VersionCatalogsExtension::class).named("libs")

            dependencies {
                add("implementation", libs.findBundle("compose").get())
            }
        }
    }
}
