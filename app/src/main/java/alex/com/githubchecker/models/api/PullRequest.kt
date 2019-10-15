package alex.com.githubchecker.models.api

import com.google.gson.annotations.SerializedName

/**
 * Created by Alex on 11/17/2017.
 */

class PullRequest : BaseModel() {

    //Accessors methods
    val title: String? = null
    val number: Int? = null
    private val head: Commit? = null
    val createdAt: String? = null
    @SerializedName("diff_url")
    val diffUrl: String? = null

    val commitId: Int?
        get() = head?.id
    val userLogin: String?
        get() = head?.userLogin
}
