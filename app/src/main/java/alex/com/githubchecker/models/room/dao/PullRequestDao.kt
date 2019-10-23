package alex.com.githubchecker.models.room.dao

import alex.com.githubchecker.models.room.entities.CommitEntity
import alex.com.githubchecker.models.room.entities.PullRequestEntity
import alex.com.githubchecker.models.room.entities.UserEntity
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * The Room Magic is in this file, where you map a Java method call to an SQL query.
 *
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
interface PullRequestDao {

//    @get:Query("SELECT * from pullrequest_table ORDER BY id ASC")
//    val pullRequestsSorted1: LiveData<List<NestedPullRequest1>>

    @get:Query(
            "SELECT * from pullrequest_table " +
                    "INNER JOIN commit_table ON commit_table.sha = pullrequest_table.commitSha " +
                    "INNER JOIN user_table ON user_table.user_id = commit_table.userId " +
                    "ORDER BY pullrequest_table.pull_request_id ASC"
    )
    val pullRequestsSorted2: LiveData<List<NestedPullRequest2>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPullRequests(entity: List<PullRequestEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommits(entity: List<CommitEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(entity: List<UserEntity>)

    @Query("DELETE FROM pullrequest_table")
    fun deleteAll()
}

//This approach fails because @Relation cannot easily double jump
//class NestedPullRequest1(
//        @Embedded val pullRequest: PullRequestEntity,
//        @Relation(parentColumn = "commitSha", entityColumn = "sha") val commit: CommitEntity,
//        @Relation(parentColumn = "commit.user_id", entityColumn = "id") val user: UserEntity)

class NestedPullRequest2(
        @Embedded val pullRequest: PullRequestEntity,
        @Embedded val commit: CommitEntity,
        @Embedded val user: UserEntity)

