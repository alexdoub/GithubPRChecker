package alex.com.githubchecker.components.github.dagger;

import alex.com.githubchecker.components.app.dagger.AppComponent;
import alex.com.githubchecker.components.github.activities.PRDiffActivity;
import alex.com.githubchecker.components.github.activities.PRListActivity;
import dagger.Component;

/**
 * Created by Alex on 11/17/2017.
 */

@GithubScope
@Component(dependencies = {AppComponent.class}, modules = {GithubModule.class})
public interface GithubComponent {

    void inject(PRListActivity activity);

    void inject(PRDiffActivity activity);
}
