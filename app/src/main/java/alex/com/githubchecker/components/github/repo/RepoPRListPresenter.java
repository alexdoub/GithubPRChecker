package alex.com.githubchecker.components.github.repo;

import alex.com.githubchecker.models.dagger.GithubModel;
import alex.com.githubchecker.components.github.pullrequest.PRDiffPresenter;
import alex.com.githubchecker.utils.SchedulerUtils;

/**
 * Created by Alex on 11/11/2017.
 */

public class RepoPRListPresenter {

    private RepoPRListView view;
    private GithubModel model;

    public RepoPRListPresenter(GithubModel model, RepoPRListView view) {
        this.model = model;
        this.view = view;
    }

    public void onCreate() {

        //Click listeners
        view.refreshClicks().subscribe(obj -> {
            refreshPullRequests();
        });
        view.selectedPRSubject().subscribe(this::pullRequestTapped);

        //Model->View listeners
        model.getPullRequestsSubject()
                .observeOn(SchedulerUtils.main())
                .subscribe(view::bindPRList);
    }

    public void onStart() {
        if (!model.getPullRequestsSubject().hasValue()) {
            refreshPullRequests();
        }
    }

    private void pullRequestTapped(Integer pullrequestId) {
        PRDiffPresenter.PRDiffActivity.Show(view.view().getContext(), pullrequestId);
    }

    private void refreshPullRequests() {
        view.showLoading(true);
        model.getPullRequests();
    }

}
