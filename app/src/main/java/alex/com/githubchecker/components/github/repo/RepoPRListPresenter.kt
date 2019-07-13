package alex.com.githubchecker.components.github.repo

import alex.com.githubchecker.components.github.pullrequest.PRDiffActivity
import alex.com.githubchecker.models.dagger.GithubModel
import alex.com.githubchecker.utils.SchedulerUtils
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Alex on 11/11/2017.
 */

internal class RepoPRListPresenter(private val model: GithubModel, private val view: RepoPRListView) {
    private val disposables = CompositeDisposable()

    fun onCreate() {
        //Click listeners
        disposables.add(
                view.getRefreshClicksObservable()
                        .subscribe { refreshPullRequests() })
        disposables.add(
                view.selectedPRSubject
                        .subscribe { this.pullRequestTapped(it) })

        //Model->View listeners
        disposables.add(
                model.pullRequestsSubject
                        .observeOn(SchedulerUtils.main())
                        .subscribe { view.bindPRList(it) })

        view.bindRepo(model)
    }

    fun onDestroy() {
        disposables.clear()
    }

    fun onStart() {
        if (!model.pullRequestsSubject.hasValue()) {
            refreshPullRequests()
        }
    }

    private fun pullRequestTapped(pullrequestId: Int?) {
        PRDiffActivity.Show(view.context, pullrequestId)
    }

    private fun refreshPullRequests() {
        view.showLoading(true)
        model.getPullRequests()
    }
}
