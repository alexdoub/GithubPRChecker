package alex.com.githubchecker.components.github.pullrequest

import alex.com.githubchecker.models.api.PullRequest
import alex.com.githubchecker.models.dagger.GithubModel
import alex.com.githubchecker.utils.SchedulerUtils
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Alex on 11/11/2017.
 */

internal class PRDiffPresenter(private val model: GithubModel, private val view: PRDiffView, private val pullRequestId: Int?) {
    private val disposables = CompositeDisposable()

    fun onCreate() {
        view.showLoading(true)
        disposables.add(
                model.getPullRequest(pullRequestId)
                        .observeOn(SchedulerUtils.main())
                        .subscribe { pullRequest ->
                            view.bindPR(pullRequest)
                            getDiffForPr(pullRequest)
                        })
    }

    private fun getDiffForPr(pullRequest: PullRequest) {
        view.showLoading(true)
        disposables.add(
                model.getDiffForPr(pullRequest)
                        .observeOn(SchedulerUtils.main())
                        .subscribe {
                            view.bindDiff(it)
                        })
    }

    fun onStop() {
        disposables.clear()
    }
}
