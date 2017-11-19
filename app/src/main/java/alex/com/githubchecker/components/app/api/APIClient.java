package alex.com.githubchecker.components.app.api;

import java.io.IOException;
import java.util.List;

import alex.com.githubchecker.models.Diff;
import alex.com.githubchecker.models.api.PullRequest;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by Alex on 11/17/2017.
 */

public class APIClient {

    private GithubAPI _githubAPI;
    private OkHttpClient _client;

    public APIClient(OkHttpClient client, GithubAPI githubAPI) {
        _githubAPI = githubAPI;
        _client = client;
    }

    public Observable<List<PullRequest>> getPullRequests(String owner, String repo) {
        return _githubAPI.getPullRequests(owner, repo);
    }

    //We cant use a standard API call for this, so we have to manually get the data
    //From the URL provided
    public Observable<String> getDiffForPullRequest(PullRequest pullRequest) {
        Request.Builder req = new Request.Builder();
        req.url(pullRequest.getDiffUrl());
        PublishSubject<String> publishSubject = PublishSubject.create();

        _client.newCall(req.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Timber.e("getDiffForPullRequest FAILURE()");
                publishSubject.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    publishSubject.onNext(response.body().string());
                }
            }
        });
        return publishSubject;
    }
}
