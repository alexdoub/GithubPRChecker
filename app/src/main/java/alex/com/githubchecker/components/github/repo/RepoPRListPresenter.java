package alex.com.githubchecker.components.github.repo;

import alex.com.githubchecker.components.github.pullrequest.PRDiffActivity;
import alex.com.githubchecker.models.dagger.GithubModel;
import alex.com.githubchecker.utils.SchedulerUtils;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Alex on 11/11/2017.
 */

class RepoPRListPresenter {

    private RepoPRListView view;
    private GithubModel model;
    private CompositeDisposable disposables = new CompositeDisposable();

    RepoPRListPresenter(GithubModel model, RepoPRListView view) {
        this.model = model;
        this.view = view;
    }

    void onCreate() {
        //Click listeners
        disposables.add(
                view.refreshClicks()
                        .subscribe(obj -> refreshPullRequests()));
        disposables.add(
                view.selectedPRSubject()
                        .subscribe(this::pullRequestTapped));

        //Model->View listeners
        disposables.add(
                model.getPullRequestsSubject()
                        .observeOn(SchedulerUtils.main())
                        .subscribe(view::bindPRList));


        view.bindRepo(model);
    }

    void onStop() {
        disposables.clear();
    }

    void onStart() {
        if (!model.getPullRequestsSubject().hasValue()) {
            refreshPullRequests();
        }
    }

    private void pullRequestTapped(Integer pullrequestId) {
        PRDiffActivity.Show(view.view().getContext(), pullrequestId);
    }

    private void refreshPullRequests() {
        view.showLoading(true);
        model.getPullRequests();
    }

}
