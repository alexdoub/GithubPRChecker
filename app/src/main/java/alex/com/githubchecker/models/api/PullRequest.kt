package alex.com.githubchecker.models.api

import com.google.gson.annotations.SerializedName

/**
 * Created by Alex on 11/17/2017.
 */

class PullRequest : BaseModel() {

    //Accessors methods
    var title: String? = null
    var number: Int? = null
    val head: Commit? = null
    var createdAt: String? = null
    @SerializedName("diff_url")
    var diffUrl: String? = null

    val commitId: Int?
        get() = head?.id
    val userLogin: String?
        get() = head?.userLogin
}
