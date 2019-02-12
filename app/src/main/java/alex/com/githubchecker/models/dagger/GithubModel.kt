package alex.com.githubchecker.models.dagger

import alex.com.githubchecker.components.app.GithubCheckerApp
import alex.com.githubchecker.components.app.api.APIClient
import alex.com.githubchecker.components.app.data.DataManager
import alex.com.githubchecker.models.Diff
import alex.com.githubchecker.models.api.PullRequest
import alex.com.githubchecker.utils.SchedulerUtils
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 * Created by Alex on 11/17/2017.
 */

class GithubModel(private val apiClient: APIClient, private val dataManager: DataManager) {
    val pullRequestsSubject: BehaviorSubject<List<PullRequest>>
    private val disposables = CompositeDisposable()

    val owner: String
        get() = dataManager.currentUserSubject.value!!

    val repo: String
        get() = dataManager.currentRepoSubject.value!!

    init {
        pullRequestsSubject = BehaviorSubject.create()
    }

    fun getPullRequests() {
        disposables.add(apiClient.getPullRequests(dataManager.currentUserSubject.value!!, dataManager.currentRepoSubject.value!!)
                .observeOn(SchedulerUtils.main())
                .subscribe { pullRequestsSubject.onNext(it) })
    }

    fun getPullRequest(number: Int?): Observable<PullRequest> {
        return pullRequestsSubject
                .flatMapIterable { items -> items }
                .filter { item -> item.number!!.toInt() == number!!.toInt() }
    }

    fun getDiffForPr(pullRequest: PullRequest): Observable<Diff> {
        val diffPublishSubject = PublishSubject.create<Diff>()
        disposables.add(apiClient.getDiffForPullRequest(pullRequest)
                .observeOn(SchedulerUtils.main())   //Must be main to make the toast
                .subscribe { rawDiff ->

                    //Notify user of expensive operation
                    if (rawDiff.length > 15000) {
                        Toast.makeText(GithubCheckerApp.appComponent!!.provideContext(), "Processing large diff, this could take some time.", Toast.LENGTH_SHORT).show()
                    }

                    //Do computation on computation thread
                    Observable.just(rawDiff)
                            .observeOn(SchedulerUtils.compute())
                            .subscribe { input -> diffPublishSubject.onNext(Diff.Parse(input)) }
                })

        return diffPublishSubject
    }
}
