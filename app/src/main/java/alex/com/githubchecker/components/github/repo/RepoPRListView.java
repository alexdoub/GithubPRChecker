package alex.com.githubchecker.components.github.repo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;

import javax.inject.Inject;

import alex.com.githubchecker.R;
import alex.com.githubchecker.components.github.repo.list.PRListAdapter;
import alex.com.githubchecker.models.api.PullRequest;
import alex.com.githubchecker.models.dagger.GithubModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Alex on 11/11/2017.
 */

public class RepoPRListView {

    @BindView(R.id.loading) ProgressBar loadingView;

    @BindView(R.id.pull_request_recycler_view) RecyclerView pullRequestRV;
    @BindView(R.id.refresh_prs) Button refreshBtn;

    private View view;

    private PRListAdapter adapter;
    private PublishSubject<Integer> selectedPRSubject = PublishSubject.create();

    @Inject
    public RepoPRListView(RepoPRListActivity context) {
        FrameLayout parent = new FrameLayout(context);
        parent.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view = LayoutInflater.from(context).inflate(R.layout.activity_prlist, parent, true);
        ButterKnife.bind(this, view);

        adapter = new PRListAdapter(selectedPRSubject);
        pullRequestRV.setAdapter(adapter);
    }

    void bindRepo(GithubModel model) {
        ((RepoPRListActivity) view.getContext()).getSupportActionBar().setTitle(view.getContext().getString(R.string.activity_pull_requests, model.getRepo(), model.getOwner()));
    }

    void bindPRList(List<PullRequest> pullRequests) {
        showLoading(false);
        adapter.setPullrequestItems(pullRequests);
    }


    void showLoading(boolean loading) {
        pullRequestRV.setVisibility(loading ? View.GONE : View.VISIBLE);
        loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    public View view() {
        return view;
    }

    Observable<Object> refreshClicks() {
        return RxView.clicks(refreshBtn);
    }

    PublishSubject<Integer> selectedPRSubject() {
        return selectedPRSubject;
    }


    //Helpers

}
