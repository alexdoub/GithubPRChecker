package alex.com.githubchecker.models.api

/**
 * Created by Alex on 11/17/2017.
 */

class PullRequestApiResponse {
    var id: Int? = null
    var title: String? = null
    var number: Int? = null
    val head: CommitApiResponse? = null
    var created_at: String? = null
    var diff_url: String? = null
}
