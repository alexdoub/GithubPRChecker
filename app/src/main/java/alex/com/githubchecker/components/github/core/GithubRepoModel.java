package alex.com.githubchecker.components.github.core;

import android.location.Location;

import java.util.List;

import alex.com.githubchecker.components.app.api.APIClient;
import alex.com.githubchecker.components.app.api.model.PullRequest;
import alex.com.githubchecker.components.app.api.model.Repo;
import alex.com.githubchecker.utils.SchedulerUtils;
import io.reactivex.Observable;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Alex on 11/17/2017.
 */

public class GithubRepoModel {

    private APIClient apiClient;
    private BehaviorSubject<List<PullRequest>> pullRequestsSubject;

    public GithubRepoModel(APIClient apiClient) {
        this.apiClient = apiClient;
        pullRequestsSubject = BehaviorSubject.create();
    }

    public void getPullRequests() {
        apiClient.getPullRequest()
                .observeOn(SchedulerUtils.main())
                .subscribe(pullRequestsSubject::onNext);
    }


    BehaviorSubject<List<PullRequest>> getPullRequestsSubject() {
        return pullRequestsSubject;
    }

    //TODO: If null, do API call fetch
    public Observable<PullRequest> getPullRequest(Integer id) {
        pullRequestsSubject.flatMapIterable(items -> items)
                .filter(item -> {return item.getId().intValue() == id.intValue();});
    }
}
