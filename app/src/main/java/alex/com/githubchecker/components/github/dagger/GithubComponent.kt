package alex.com.githubchecker.components.github.dagger

import alex.com.githubchecker.components.app.dagger.AppComponent
import alex.com.githubchecker.components.github.pullrequest.PRDiffActivity
import alex.com.githubchecker.components.github.list.RepoPRListActivity
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
