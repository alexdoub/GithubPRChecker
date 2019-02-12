package alex.com.githubchecker.components.github.pullrequest

import alex.com.githubchecker.R
import alex.com.githubchecker.models.Diff
import alex.com.githubchecker.models.api.PullRequest
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import javax.inject.Inject

/**
 * Created by Alex on 11/11/2017.
 */

class PRDiffView @Inject
constructor(context: PRDiffActivity) {

    @JvmField
    @BindView(R.id.subtractions_tv)
    var subtractionsTv: TextView? = null
    @JvmField
    @BindView(R.id.additions_tv)
    var additionsTv: TextView? = null
    @JvmField
    @BindView(R.id.container)
    var containerSv: ScrollView? = null
    @JvmField
    @BindView(R.id.loading)
    var loadingView: ProgressBar? = null

    val view: View

    init {
        val parent = FrameLayout(context)
        parent.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        view = LayoutInflater.from(context).inflate(R.layout.activity_prdiff, parent, true)
        ButterKnife.bind(this, view)

        subtractionsTv!!.setHorizontallyScrolling(true)
        additionsTv!!.setHorizontallyScrolling(true)
    }

    internal fun bindPR(pullRequest: PullRequest) {
        (view.context as PRDiffActivity).supportActionBar!!.title = view.context.getString(R.string.activity_pr_diff, pullRequest.number)
    }

    internal fun bindDiff(diff: Diff) {
        showLoading(false)
        subtractionsTv!!.setText(diff.subtractionsSpan, TextView.BufferType.SPANNABLE)
        additionsTv!!.setText(diff.additionsSpan, TextView.BufferType.SPANNABLE)
    }

    internal fun showLoading(loading: Boolean) {
        containerSv!!.visibility = if (!loading) View.VISIBLE else View.GONE
        loadingView!!.visibility = if (loading) View.VISIBLE else View.GONE
    }
}
