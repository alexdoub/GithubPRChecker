package alex.com.githubchecker.components.app.api;

import java.util.List;

import alex.com.githubchecker.models.api.PullRequest;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Alex on 11/17/2017.
 */

public interface GithubAPI {

    @GET("repos/{owner}/{repo}/pulls")
    Observable<List<PullRequest>> getPullRequests(@Path("owner") String owner, @Path("repo") String repo);

//    @GET("repos/{owner}/{repo}/pulls/{pr_id}")
//    Observable<PullRequest> getPullRequest(@Path("owner") String owner, @Path("repo") String repo, @Path("pr_id") String pullrequestId);

//    @GET("repos/{owner}/{repo}/pull/{pr_id}.diff")
//    Observable<Diff> getDiff(@Path("owner") String owner, @Path("repo") String repo, @Path("pr_id") String pullrequestId);

}
