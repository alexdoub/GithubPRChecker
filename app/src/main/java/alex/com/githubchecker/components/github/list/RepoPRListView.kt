package alex.com.githubchecker.components.github.list

import alex.com.githubchecker.R
import alex.com.githubchecker.components.github.GithubModel
import alex.com.githubchecker.models.room.dao.NestedPullRequest
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
constructor(val activity: RepoPRListActivity) {

    val selectedPRSubject = PublishSubject.create<Int>()
    private val adapter: PRListAdapter

    init {
        val parent = FrameLayout(activity)
        parent.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        LayoutInflater.from(activity).inflate(R.layout.activity_prlist, parent, true)
        activity.setContentView(R.layout.activity_prlist)

        adapter = PRListAdapter(selectedPRSubject)
        activity.pull_request_recycler_view.adapter = adapter
    }

    internal fun bindRepo(model: GithubModel) {
        activity.supportActionBar!!.title = activity.getString(R.string.activity_pull_requests, model.repo, model.owner)
    }

    internal fun bindPRList(pullRequests: List<NestedPullRequest>) {
        showLoading(false)
        adapter.setPullrequestItems(pullRequests)
    }

    internal fun showLoading(loading: Boolean) {
        activity.pull_request_recycler_view.visibility = if (loading) View.GONE else View.VISIBLE
        activity.loading.visibility = if (loading) View.VISIBLE else View.GONE
    }

    internal fun getRefreshClicksObservable(): Observable<Any> {
        return RxView.clicks(activity.refresh_prs)
    }
}
