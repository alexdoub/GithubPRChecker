package alex.com.githubchecker.components.app.api

import alex.com.githubchecker.models.api.PullRequest
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Alex on 11/17/2017.
 */

interface GithubAPI {

    @GET("repos/{owner}/{repo}/pulls")
    fun getPullRequests(@Path("owner") owner: String, @Path("repo") repo: String): Observable<List<PullRequest>>

    //    @GET("repos/{owner}/{repo}/pulls/{pr_id}")
    //    Observable<PullRequest> getPullRequest(@Path("owner") String owner, @Path("repo") String repo, @Path("pr_id") String pullrequestId);

    //    @GET("repos/{owner}/{repo}/pull/{pr_id}.diff")
    //    Observable<Diff> getDiff(@Path("owner") String owner, @Path("repo") String repo, @Path("pr_id") String pullrequestId);

}
