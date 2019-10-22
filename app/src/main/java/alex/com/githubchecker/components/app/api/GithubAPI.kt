package alex.com.githubchecker.components.app.api

import alex.com.githubchecker.models.Diff
import alex.com.githubchecker.models.api.PullRequestApiResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Alex on 11/17/2017.
 */

interface GithubAPI {

    @GET("repos/{owner}/{repo}/pulls")
    fun getPullRequests(@Path("owner") owner: String, @Path("repo") repo: String): Observable<List<PullRequestApiResponse>>

    @GET("repos/{owner}/{repo}/pulls/{pr_id}")
    fun getPullRequest(@Path("owner") owner: String, @Path("repo") repo: String, @Path("pr_id") pullrequestId: String): Observable<PullRequestApiResponse>

    @GET("repos/{owner}/{repo}/pull/{pr_id}.diff")
    fun getDiff(@Path("owner") owner: String, @Path("repo") repo: String, @Path("pr_id") pullrequestId: String): Observable<Diff>
}
