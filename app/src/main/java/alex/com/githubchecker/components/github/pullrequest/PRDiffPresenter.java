package alex.com.githubchecker.components.github.pullrequest;

import alex.com.githubchecker.models.api.PullRequest;
import alex.com.githubchecker.models.dagger.GithubModel;
import alex.com.githubchecker.utils.SchedulerUtils;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Alex on 11/11/2017.
 */

class PRDiffPresenter {

    private PRDiffView view;
    private GithubModel model;
    private Integer pullRequestId;
    private CompositeDisposable disposables = new CompositeDisposable();

    PRDiffPresenter(GithubModel model, PRDiffView view, Integer pullRequestId) {
        this.model = model;
        this.view = view;
        this.pullRequestId = pullRequestId;
    }

    void onCreate() {
        view.showLoading(true);
        disposables.add(
                model.getPullRequest(pullRequestId)
                        .observeOn(SchedulerUtils.main())
                        .subscribe(pullRequest -> {
                            view.bindPR(pullRequest);
                            getDiffForPr(pullRequest);
                        }));
    }

    private void getDiffForPr(PullRequest pullRequest) {
        view.showLoading(true);
        disposables.add(
                model.getDiffForPr(pullRequest)
                        .observeOn(SchedulerUtils.main())
                        .subscribe(view::bindDiff));
    }

    void onStop() {
        disposables.clear();
    }
}
