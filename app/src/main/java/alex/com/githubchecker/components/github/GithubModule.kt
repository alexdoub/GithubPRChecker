package alex.com.githubchecker.components.github

import alex.com.githubchecker.components.app.api.APIClient
import alex.com.githubchecker.components.app.data.SessionDataManager
import android.app.Application
import dagger.Module
import dagger.Provides

/**
 * Created by Alex on 11/11/2017.
 */

@Module
class GithubModule {

    @Provides
    @GithubScope
    fun provideGithubModel(application: Application, apiClient: APIClient, sessionDataManager: SessionDataManager): GithubModel {
        return GithubModel(application, apiClient, sessionDataManager)
    }
}
