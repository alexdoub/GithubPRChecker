package alex.com.githubchecker.components.github.repo.list

import alex.com.githubchecker.R
import alex.com.githubchecker.models.api.PullRequest
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_pr.view.status
import kotlinx.android.synthetic.main.item_pr.view.title

/**
 * Created by Alex on 11/13/2017.
 */

class PRViewHolder(private val containerView: View, selectedPRSubject: PublishSubject<Int>) : RecyclerView.ViewHolder(containerView) {

    private var _pullRequestNumber: Int? = null

    init {
        //@@TODO: dispose
        RxView.clicks(containerView).subscribe { click -> selectedPRSubject.onNext(_pullRequestNumber!!) }
    }

    fun bind(pullRequest: PullRequest) {

        _pullRequestNumber = pullRequest.number

        val userLogin = pullRequest.userLogin
        val number = pullRequest.number
        val createdAt = pullRequest.createdAt
        val status = containerView.context.resources.getString(R.string.pr_content, number, createdAt, userLogin)

        containerView.title.text = pullRequest.title
        containerView.status.text = status
    }
}

