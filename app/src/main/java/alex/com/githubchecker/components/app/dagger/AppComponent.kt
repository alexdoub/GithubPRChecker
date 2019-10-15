package alex.com.githubchecker.components.app.dagger

import alex.com.githubchecker.components.app.api.APIClient
import alex.com.githubchecker.components.app.data.SessionDatamanager
import android.app.Application
import android.content.Context
import dagger.Component

/**
 * Created by Alex on 11/12/2017.
 */

@AppScope
@Component(modules = [AppModule::class, NetworkModule::class, DataModule::class])
interface AppComponent {
    fun provideAPIClient(): APIClient

    fun provideDataManager(): SessionDatamanager

    fun provideContext(): Context

    fun provideApplication(): Application
}
