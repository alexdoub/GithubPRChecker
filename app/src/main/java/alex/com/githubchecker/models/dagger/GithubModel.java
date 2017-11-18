package alex.com.githubchecker.models.dagger;

import java.util.List;

import alex.com.githubchecker.components.app.api.APIClient;
import alex.com.githubchecker.components.app.data.DataManager;
import alex.com.githubchecker.models.Diff;
import alex.com.githubchecker.models.api.PullRequest;
import alex.com.githubchecker.utils.SchedulerUtils;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Alex on 11/17/2017.
 */

public class GithubModel {

    private APIClient apiClient;
    private DataManager dataManager;
    private BehaviorSubject<List<PullRequest>> pullRequestsSubject;

    public GithubModel(APIClient apiClient, DataManager dataManager) {
        this.apiClient = apiClient;
        this.dataManager = dataManager;
        pullRequestsSubject = BehaviorSubject.create();
    }

    public void getPullRequests() {
        apiClient.getPullRequests(dataManager.getCurrentUserSubject().getValue(), dataManager.getCurrentRepoSubject().getValue())
                .observeOn(SchedulerUtils.main())
                .subscribe(pullRequestsSubject::onNext);
    }


    public BehaviorSubject<List<PullRequest>> getPullRequestsSubject() {
        return pullRequestsSubject;
    }

    public Observable<PullRequest> getPullRequest(Integer number) {
        return pullRequestsSubject
                .flatMapIterable(items -> items)
                .filter(item -> item.getNumber().intValue() == number.intValue());
    }

    public Observable<Diff> getDiffForPr(PullRequest pullRequest) {
        return apiClient.getDiffForPullRequest(pullRequest);
    }

    public String getOwner() {
        return dataManager.getCurrentUserSubject().getValue();
    }

    public String getRepo() {
        return dataManager.getCurrentRepoSubject().getValue();
    }
}
