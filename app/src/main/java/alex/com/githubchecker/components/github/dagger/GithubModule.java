package alex.com.githubchecker.components.github.dagger;

import alex.com.githubchecker.components.app.api.APIClient;
import alex.com.githubchecker.components.github.core.GithubRepoModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Alex on 11/11/2017.
 */

@Module
public class GithubModule {

    @Provides
    @GithubScope
    GithubRepoModel provideModel(APIClient apiClient) {
        return new GithubRepoModel(apiClient);
    }

}
