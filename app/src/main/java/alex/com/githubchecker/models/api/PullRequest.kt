package alex.com.githubchecker.models.api

import com.google.gson.annotations.SerializedName

/**
 * Created by Alex on 11/17/2017.
 */

class PullRequest {
    var id: Int? = null
    var title: String? = null
    var number: Int? = null
    val head: Commit? = null
    var created_at: String? = null
    var diff_url: String? = null
}
