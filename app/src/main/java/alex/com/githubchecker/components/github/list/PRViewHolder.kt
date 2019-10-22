package alex.com.githubchecker.components.github.list

import alex.com.githubchecker.R
import alex.com.githubchecker.models.room.dao.NestedPullRequest2
import android.view.View
import androidx.recyclerview.widget.RecyclerView
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

    fun bind(nestedPullRequest: NestedPullRequest2) {

        _pullRequestNumber = nestedPullRequest.pullRequestEntity.number

        val userLogin = nestedPullRequest.user.login
        val number = nestedPullRequest.pullRequestEntity.number
        val createdAt = nestedPullRequest.pullRequestEntity.created_at
        val status = containerView.context.resources.getString(R.string.pr_content, number, createdAt, userLogin)

        containerView.title.text = nestedPullRequest.pullRequestEntity.title
        containerView.status.text = status
    }
}

