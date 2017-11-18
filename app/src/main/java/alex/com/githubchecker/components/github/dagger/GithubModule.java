package alex.com.githubchecker.components.github.dagger;

import alex.com.githubchecker.components.app.api.APIClient;
import alex.com.githubchecker.components.app.data.DataManager;
import alex.com.githubchecker.models.dagger.GithubModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Alex on 11/11/2017.
 */

@Module
public class GithubModule {

    @Provides
    @GithubScope
    GithubModel provideGithubModel(APIClient apiClient, DataManager dataManager) {
        return new GithubModel(apiClient, dataManager);
    }

}
