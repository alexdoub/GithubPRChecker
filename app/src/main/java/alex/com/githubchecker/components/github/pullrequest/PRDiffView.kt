package alex.com.githubchecker.components.github.pullrequest

import alex.com.githubchecker.R
import alex.com.githubchecker.models.Diff
import alex.com.githubchecker.models.api.PullRequest
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_prdiff.additions_tv
import kotlinx.android.synthetic.main.activity_prdiff.container
import kotlinx.android.synthetic.main.activity_prdiff.subtractions_tv
import kotlinx.android.synthetic.main.activity_prlist.loading
import javax.inject.Inject

/**
 * Created by Alex on 11/11/2017.
 */

class PRDiffView @Inject
constructor(private val activity: PRDiffActivity) {

    init {
        val parent = FrameLayout(activity)
        parent.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        LayoutInflater.from(activity).inflate(R.layout.activity_prdiff, parent, true)
        activity.setContentView(R.layout.activity_prdiff)

        activity.subtractions_tv.setHorizontallyScrolling(true)
        activity.additions_tv.setHorizontallyScrolling(true)
    }

    internal fun bindPR(pullRequest: PullRequest) {
        activity.supportActionBar!!.title = activity.getString(R.string.activity_pr_diff, pullRequest.number)
    }

    internal fun bindDiff(diff: Diff) {
        showLoading(false)
        activity.subtractions_tv.setText(diff.subtractionsSpan, TextView.BufferType.SPANNABLE)
        activity.additions_tv.setText(diff.additionsSpan, TextView.BufferType.SPANNABLE)
    }

    internal fun showLoading(loading: Boolean) {
        activity.container.visibility = if (!loading) View.VISIBLE else View.GONE
        activity.loading.visibility = if (loading) View.VISIBLE else View.GONE
    }
}
