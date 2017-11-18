package alex.com.githubchecker.components.github.pullrequest;

import alex.com.githubchecker.components.app.api.model.PullRequest;
import alex.com.githubchecker.components.github.repo.GithubModel;
import alex.com.githubchecker.utils.SchedulerUtils;

/**
 * Created by Alex on 11/11/2017.
 */

public class PRDiffPresenter {

    private PRDiffView view;
    private GithubModel model;
    private Integer pullRequestId;

    public PRDiffPresenter(GithubModel model, PRDiffView view, Integer pullRequestId) {
        this.model = model;
        this.view = view;
        this.pullRequestId = pullRequestId;
    }

    public void onCreate() {

        view.showLoading(true);
        model.getPullRequest(pullRequestId)
                .observeOn(SchedulerUtils.main())
                .subscribe(pullRequest -> {
                    view.bindPR(pullRequest);
                    getDiffForPr(pullRequest);
                });
    }

    private void getDiffForPr(PullRequest pullRequest) {
        view.showLoading(true);
        model.getDiffForPr(pullRequest)
                .observeOn(SchedulerUtils.main())
                .subscribe(view::bindDiff);
    }

}
