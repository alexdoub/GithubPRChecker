package alex.com.githubchecker.components.github.repo.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import alex.com.githubchecker.R;
import alex.com.githubchecker.models.api.PullRequest;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Alex on 11/13/2017.
 */

public class PRListAdapter extends RecyclerView.Adapter<PRViewHolder> {
    private ArrayList<PullRequest> pullrequestItems = new ArrayList<>();

    private PublishSubject<Integer> selectedPRSubject;

    public PRListAdapter(PublishSubject<Integer> selectedPRSubject) {
        super();
        this.selectedPRSubject = selectedPRSubject;
    }

    public void setPullrequestItems(List<PullRequest> pullrequestItems) {
        this.pullrequestItems.clear();
        this.pullrequestItems.addAll(pullrequestItems);
        notifyDataSetChanged();
    }

    @Override
    public PRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pr, parent, false);
        return new PRViewHolder(view, selectedPRSubject);
    }

    @Override
    public void onBindViewHolder(PRViewHolder holder, int position) {
        PullRequest pullRequest = pullrequestItems.get(position);
        holder.bind(pullRequest);
    }


    @Override
    public int getItemCount() {
        if (pullrequestItems != null && pullrequestItems.size() > 0) {
            return pullrequestItems.size();
        }
        return 0;
    }
}

