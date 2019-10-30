package alex.com.githubchecker.components.github

import alex.com.githubchecker.components.app.AppComponent
import alex.com.githubchecker.components.github.list.RepoPRListActivity
import alex.com.githubchecker.components.github.pullrequest.PRDiffActivity
import dagger.Component

/**
 * Created by Alex on 11/17/2017.
 */

@GithubScope
@Component(dependencies = [AppComponent::class], modules = [GithubModule::class])
interface GithubComponent {

    fun inject(activity: RepoPRListActivity)

    fun inject(activity: PRDiffActivity)
}
