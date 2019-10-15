package alex.com.githubchecker.models.api

/**
 * Created by Alex on 11/17/2017.
 */

class Commit : BaseModel() {

    val user: User? = null

    val userLogin: String?
        get() = user?.login
}
