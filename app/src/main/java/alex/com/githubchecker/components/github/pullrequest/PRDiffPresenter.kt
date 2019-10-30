package alex.com.githubchecker.components.github.pullrequest

import alex.com.githubchecker.components.github.GithubModel
import alex.com.githubchecker.models.room.entities.PullRequestEntity
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
                            view.bindPR(pullRequest.pullRequest)
                            getDiffForPr(pullRequest.pullRequest)
                        })
    }

    private fun getDiffForPr(pullRequest: PullRequestEntity) {
        view.showLoading(true)
        disposables.add(
                model.getDiffForPr(pullRequest)
                        .observeOn(SchedulerUtils.main())
                        .subscribe {
                            view.bindDiff(it)
                        })
    }

    fun onDestroy() {
        disposables.clear()
    }
}
