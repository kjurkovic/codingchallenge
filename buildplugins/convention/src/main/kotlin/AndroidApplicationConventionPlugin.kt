import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.kjurkovic.app
import com.kjurkovic.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("io.realm.kotlin")
            }

            extensions.configure<BaseAppModuleExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = app.targetSdk
            }
        }
    }
}
