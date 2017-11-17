package alex.com.githubchecker.components.app.api;

import java.util.List;

import alex.com.githubchecker.components.app.api.model.PullRequest;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Alex on 11/17/2017.
 */

public interface GithubAPI {

    @GET("repos/{owner}/{repo}/pulls")
    Observable<List<PullRequest>> getPullRequests(@Path("owner") String owner, @Path("repo") String repo);

}
