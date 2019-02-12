package alex.com.githubchecker.components.github.repo

import alex.com.githubchecker.R
import alex.com.githubchecker.components.github.repo.list.PRListAdapter
import alex.com.githubchecker.models.api.PullRequest
import alex.com.githubchecker.models.dagger.GithubModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_prdiff.loading
import kotlinx.android.synthetic.main.activity_prlist.pull_request_recycler_view
import kotlinx.android.synthetic.main.activity_prlist.refresh_prs
import javax.inject.Inject

/**
 * Created by Alex on 11/11/2017.
 */

class RepoPRListView @Inject
constructor(val context: RepoPRListActivity) {

    val selectedPRSubject = PublishSubject.create<Int>()
    private val adapter: PRListAdapter

    init {
        val parent = FrameLayout(context)
        parent.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        LayoutInflater.from(context).inflate(R.layout.activity_prlist, parent, true)
        context.setContentView(R.layout.activity_prlist)

        adapter = PRListAdapter(selectedPRSubject)
        context.pull_request_recycler_view.adapter = adapter
    }

    internal fun bindRepo(model: GithubModel) {
        context.supportActionBar!!.title = context.getString(R.string.activity_pull_requests, model.repo, model.owner)
    }

    internal fun bindPRList(pullRequests: List<PullRequest>) {
        showLoading(false)
        adapter.setPullrequestItems(pullRequests)
    }

    internal fun showLoading(loading: Boolean) {
        context.pull_request_recycler_view.visibility = if (loading) View.GONE else View.VISIBLE
        context.loading.visibility = if (loading) View.VISIBLE else View.GONE
    }

    internal fun getRefreshClicksObservable(): Observable<Any> {
        return RxView.clicks(context.refresh_prs)
    }
}
