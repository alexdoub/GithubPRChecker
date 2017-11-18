package alex.com.githubchecker.models.dagger;

import java.util.List;

import alex.com.githubchecker.components.app.api.APIClient;
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
    private BehaviorSubject<List<PullRequest>> pullRequestsSubject;

    public GithubModel(APIClient apiClient) {
        this.apiClient = apiClient;
        pullRequestsSubject = BehaviorSubject.create();
    }

    public void getPullRequests() {
        apiClient.getPullRequests()
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
}
