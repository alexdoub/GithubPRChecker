package alex.com.githubchecker.models.room

import alex.com.githubchecker.models.api.PullRequest
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
    //    val allPullRequests: LiveData<List<PullRequestEntity>>
    val allPullRequests2: LiveData<List<PullRequestEntity>>

    init {
        val db = GithubDatabase.getDatabase(application)

        PullRequestDao = db.pullRequestDao()
//        allPullRequests = PullRequestDao.pullRequestsSorted
        allPullRequests2 = PullRequestDao.pullRequestsSorted2
    }

    fun save(pullRequests: List<PullRequest>) {
        val commitEntities = ArrayList<CommitEntity>()
        val userEntities = ArrayList<UserEntity>()
        val pullRequestEntities = pullRequests.map { pullRequest ->

            val userId = pullRequest.head?.user?.id
            userId?.let {
                userEntities.add(UserEntity(userId))
            } ?: run {
                Timber.e("A pull request did not have a reachable userID")
            }

            val commitSha = pullRequest.head?.sha
            commitSha?.let {
                commitEntities.add(CommitEntity(commitSha).apply {
                    this.userId = userId
                })
            } ?: run {
                Timber.e("A pull request did not have a sha")
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
