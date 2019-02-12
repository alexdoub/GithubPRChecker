package alex.com.githubchecker.components.github.repo

import alex.com.githubchecker.R
import alex.com.githubchecker.components.github.repo.list.PRListAdapter
import alex.com.githubchecker.models.api.PullRequest
import alex.com.githubchecker.models.dagger.GithubModel
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Alex on 11/11/2017.
 */

class RepoPRListView @Inject
constructor(context: RepoPRListActivity) {

    @JvmField
    @BindView(R.id.loading)
    var loadingView: ProgressBar? = null

    @JvmField
    @BindView(R.id.pull_request_recycler_view)
    var pullRequestRV: RecyclerView? = null
    @JvmField
    @BindView(R.id.refresh_prs)
    var refreshBtn: Button? = null

    val selectedPRSubject = PublishSubject.create<Int>()
    val view: View
    private val adapter: PRListAdapter

    init {
        val parent = FrameLayout(context)
        parent.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        view = LayoutInflater.from(context).inflate(R.layout.activity_prlist, parent, true)
        ButterKnife.bind(this, view)

        adapter = PRListAdapter(selectedPRSubject)
        pullRequestRV!!.adapter = adapter
    }

    internal fun bindRepo(model: GithubModel) {
        (view.context as RepoPRListActivity).supportActionBar!!.title = view.context.getString(R.string.activity_pull_requests, model.repo, model.owner)
    }

    internal fun bindPRList(pullRequests: List<PullRequest>) {
        showLoading(false)
        adapter.setPullrequestItems(pullRequests)
    }

    internal fun showLoading(loading: Boolean) {
        pullRequestRV!!.visibility = if (loading) View.GONE else View.VISIBLE
        loadingView!!.visibility = if (loading) View.VISIBLE else View.GONE
    }

    internal fun getRefreshClicksObservable(): Observable<Any> {
        return RxView.clicks(refreshBtn!!)
    }
}
