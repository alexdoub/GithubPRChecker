package alex.com.githubchecker.models.room

import alex.com.githubchecker.models.api.PullRequestApiResponse
import alex.com.githubchecker.models.room.dao.NestedPullRequest
import alex.com.githubchecker.models.room.dao.PullRequestDao
import alex.com.githubchecker.models.room.entities.CommitEntity
import alex.com.githubchecker.models.room.entities.PullRequestEntity
import alex.com.githubchecker.models.room.entities.UserEntity
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import timber.log.Timber

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

class GithubRepository(application: Application) {

    private val PullRequestDao: PullRequestDao
    val allPullRequests: LiveData<List<NestedPullRequest>>

    init {
        val db = GithubDatabase.getDatabase(application)

        PullRequestDao = db.pullRequestDao()
        allPullRequests = PullRequestDao.pullRequestsSorted
    }

    fun save(pullRequests: List<PullRequestApiResponse>) {
        val commitEntities = ArrayList<CommitEntity>()
        val userEntities = ArrayList<UserEntity>()
        val pullRequestEntities = pullRequests.map { pullRequest ->

            val userId = pullRequest.head?.user?.id
            val commitSha = pullRequest.head?.sha
            if (userId != null) {
                userEntities.add(UserEntity(userId).apply {
                    login = pullRequest.head.user.login
                })
            } else {
                Timber.e("A pull request did not have an associated userID. Skipping user")
            }

            if (commitSha != null && userId != null) {
                commitEntities.add(CommitEntity(commitSha, userId))
            } else {
                Timber.e("A pull request did not have an associated sha or user with an ID. Skipping CommitApiResponse")
            }

            PullRequestEntity(pullRequest.id!!).apply {
                this.title = pullRequest.title
                this.number = pullRequest.number
                this.created_at = pullRequest.created_at
                this.diff_url = pullRequest.diff_url
                this.commitSha = pullRequest.head?.sha
            }
        }
        val data = PullRequestInsertionData(pullRequestEntities, commitEntities, userEntities)
        insertAsyncTask(PullRequestDao).execute(data)
    }

    inner class PullRequestInsertionData(
            val pullRequestEntities: List<PullRequestEntity>,
            val commitEntities: ArrayList<CommitEntity>,
            val userEntities: ArrayList<UserEntity>)

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: PullRequestDao) : AsyncTask<PullRequestInsertionData, Void, Void>() {

        override fun doInBackground(vararg data: PullRequestInsertionData): Void? {
            mAsyncTaskDao.insertPullRequests(data[0].pullRequestEntities)
            mAsyncTaskDao.insertCommits(data[0].commitEntities)
            mAsyncTaskDao.insertUsers(data[0].userEntities)
            return null
        }
    }
}
