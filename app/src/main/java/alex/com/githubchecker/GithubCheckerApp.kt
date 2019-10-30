package alex.com.githubchecker

import alex.com.githubchecker.components.app.AppComponent
import alex.com.githubchecker.components.app.AppModule
import alex.com.githubchecker.components.app.api.NetworkModule
import alex.com.githubchecker.components.app.dagger.DaggerAppComponent
import alex.com.githubchecker.components.app.data.DataModule
import alex.com.githubchecker.components.github.GithubComponent
import alex.com.githubchecker.components.github.GithubModule
import alex.com.githubchecker.components.github.dagger.DaggerGithubComponent
import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber

/**
 * Created by Alex on 11/17/2017.
 */

class GithubCheckerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
        Timber.plant(Timber.DebugTree())

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule())
                .dataModule(DataModule()).build()
    }

    companion object {

        lateinit var appComponent: AppComponent

        val githubComponent: GithubComponent by lazy {
            DaggerGithubComponent.builder()
                    .appComponent(appComponent)
                    .githubModule(GithubModule()).build()
        }
    }
}
