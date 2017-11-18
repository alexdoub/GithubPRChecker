package alex.com.githubchecker.components.github.dagger;

import alex.com.githubchecker.components.app.api.APIClient;
import alex.com.githubchecker.components.github.repo.GithubModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Alex on 11/11/2017.
 */

@Module
public class GithubModule {

    @Provides
    @GithubScope
    GithubModel provideGithubModel(APIClient apiClient) {
        return new GithubModel(apiClient);
    }

}
