package alex.com.githubchecker.components.app.api;

import java.io.IOException;
import java.util.List;

import alex.com.githubchecker.components.app.api.model.Diff;
import alex.com.githubchecker.components.app.api.model.PullRequest;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by Alex on 11/17/2017.
 */

public class APIClient {

    private String owner;
    private String repo;
    private GithubAPI _githubAPI;
    private OkHttpClient _client;

    public APIClient(OkHttpClient client, GithubAPI githubAPI, String owner, String repo) {
        _githubAPI = githubAPI;
        _client = client;
        this.owner = owner;
        this.repo = repo;
    }

    public Observable<List<PullRequest>> getPullRequests() {
        return _githubAPI.getPullRequests(owner, repo);
    }

//    public Observable<PullRequest> getPullRequest(String pullrequestId) {
//        return _githubAPI.getPullRequest(owner, repo, pullrequestId);
//    }

    //For some reason we can't use the API base URL, we have to use the URL provided in the PR obj
    public Observable<Diff> getDiffForPullRequest(PullRequest pullRequest) {
        Request.Builder req = new Request.Builder();
        req.url(pullRequest.getDiffUrl());
        PublishSubject<Diff> publishSubject = PublishSubject.create();
        _client.newCall(req.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Timber.e("getDiffForPullRequest FAILURE()");
                publishSubject.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Timber.e("getDiffForPullRequest success");
                if (response.isSuccessful() && response.body() != null) {
                    publishSubject.onNext(new Diff(response.body().string()));
                }
            }
        });
        return publishSubject;
    }
}
