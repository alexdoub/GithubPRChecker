package alex.com.githubchecker.models.api

/**
 * Created by Alex on 11/17/2017.
 */

class PullRequest : BaseModel() {

    //Accessors methods
    val title: String? = null
    val number: Int? = null
    private val head: Commit? = null
    val createdAt: String? = null
    val diffUrl: String? = null

    val userLogin: String?
        get() = head?.userLogin
}
