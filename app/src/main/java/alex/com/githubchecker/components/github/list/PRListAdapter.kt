package alex.com.githubchecker.components.github.list

import alex.com.githubchecker.R
import alex.com.githubchecker.models.room.dao.NestedPullRequest
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject
import java.util.ArrayList

/**
 * Created by Alex on 11/13/2017.
 */

class PRListAdapter(private val selectedPRSubject: PublishSubject<Int>) : RecyclerView.Adapter<PRViewHolder>() {
    private val pullrequestItems = ArrayList<NestedPullRequest>()

    fun setPullrequestItems(pullrequestItems: List<NestedPullRequest>) {
        this.pullrequestItems.clear()
        this.pullrequestItems.addAll(pullrequestItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PRViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pr, parent, false)
        return PRViewHolder(view, selectedPRSubject)
    }

    override fun onBindViewHolder(holder: PRViewHolder, position: Int) {
        val pullRequest = pullrequestItems[position]
        holder.bind(pullRequest)
    }

    override fun getItemCount(): Int {
        return pullrequestItems.size
    }
}

