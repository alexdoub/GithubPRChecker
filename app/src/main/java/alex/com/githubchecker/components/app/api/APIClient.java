package alex.com.githubchecker.components.app.api;

import android.widget.Toast;

import java.util.List;

import alex.com.githubchecker.components.app.GithubCheckerApp;
import alex.com.githubchecker.components.app.api.model.PullRequest;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import timber.log.Timber;

/**
 * Created by Alex on 11/17/2017.
 */

public class APIClient {

    private String owner;
    private String repo;
    private GithubAPI _githubAPI;

    public APIClient(GithubAPI githubAPI, String owner, String repo) {
        _githubAPI = githubAPI;
        this.owner = owner;
        this.repo = repo;
    }

    public Observable<List<PullRequest>> getPullRequest() {
        return _githubAPI.getPullRequests(owner, repo);
    }
}
