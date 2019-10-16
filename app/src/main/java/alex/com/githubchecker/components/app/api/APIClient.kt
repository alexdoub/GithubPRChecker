package alex.com.githubchecker.components.app.api

import alex.com.githubchecker.models.api.PullRequest
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

/**
 * Created by Alex on 11/17/2017.
 */

class APIClient(private val _client: OkHttpClient, private val _githubAPI: GithubAPI) {

    fun getPullRequests(owner: String, repo: String): Observable<List<PullRequest>> {
        return _githubAPI.getPullRequests(owner, repo)
    }

    //We cant use a standard API call for this, so we have to manually get the data
    //From the URL provided
    fun getDiffForPullRequest(pullRequest: PullRequest): Observable<String> {
        val req = Request.Builder()
        req.url(pullRequest.diff_url!!)
        val publishSubject = PublishSubject.create<String>()

        _client.newCall(req.build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Timber.e("getDiffForPullRequest FAILURE()")
                publishSubject.onError(e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful && response.body != null) {
                    publishSubject.onNext(response.body!!.string())
                }
            }
        })
        return publishSubject
    }
}
