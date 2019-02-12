package alex.com.githubchecker.components.github.repo.list

import alex.com.githubchecker.R
import alex.com.githubchecker.models.api.PullRequest
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject

/**
 * Created by Alex on 11/13/2017.
 */

class PRViewHolder(private val view: View, selectedPRSubject: PublishSubject<Int>) : RecyclerView.ViewHolder(view) {

    @JvmField
    @BindView(R.id.title)
    var titleTv: TextView? = null
    @JvmField
    @BindView(R.id.status)
    var statusTv: TextView? = null

    private var _pullRequestNumber: Int? = null

    init {
        ButterKnife.bind(this, view)

        RxView.clicks(view).subscribe { click -> selectedPRSubject.onNext(_pullRequestNumber!!) }
    }

    fun bind(pullRequest: PullRequest) {

        _pullRequestNumber = pullRequest.number

        val userLogin = pullRequest.userLogin
        val number = pullRequest.number
        val createdAt = pullRequest.createdAt
        val status = view.context.resources.getString(R.string.pr_content, number, createdAt, userLogin)

        titleTv!!.text = pullRequest.title
        statusTv!!.text = status
    }

}

