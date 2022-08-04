import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.kjurkovic.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kjurkovic.android.application")
            }

            extensions.configure<BaseAppModuleExtension> {
                configureAndroidCompose(this)
            }
        }
    }
}
