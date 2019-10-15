package alex.com.githubchecker.models.room

import alex.com.githubchecker.models.api.PullRequest
import alex.com.githubchecker.models.room.dao.PullRequestDao
import android.app.Application
import android.os.AsyncTask

import androidx.lifecycle.LiveData

import alex.com.githubchecker.models.room.entities.PullRequestEntity

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

class GithubRepository(application: Application) {

    private val PullRequestDao: PullRequestDao
    val allPullRequests: LiveData<List<PullRequestEntity>>

    init {
        val db = GithubDatabase.getDatabase(application)

        PullRequestDao = db.pullRequestDao()
        allPullRequests = PullRequestDao.pullRequestsSorted
    }

    fun insert(vararg pullRequest: PullRequest) {
        val pullRequestEntity = pullRequest.map {
            PullRequestEntity().apply {
                id = it.id!!
                title = it.title
                number = it.number
                commitId = it.commitId
                createdAt = it.createdAt
                diffUrl = it.diffUrl
            }
        }
        insertAsyncTask(PullRequestDao).execute(*pullRequestEntity.toTypedArray())
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: PullRequestDao) : AsyncTask<PullRequestEntity, Void, Void>() {

        override fun doInBackground(vararg params: PullRequestEntity): Void? {
            mAsyncTaskDao.insert(*params)
            return null
        }
    }
}
