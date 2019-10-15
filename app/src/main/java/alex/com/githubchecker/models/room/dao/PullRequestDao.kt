package alex.com.githubchecker.models.room.dao

import alex.com.githubchecker.models.room.entities.PullRequestEntity
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @get:Query("SELECT * from pullrequest_table ORDER BY id ASC")
    val pullRequestsSorted: LiveData<List<PullRequestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: PullRequestEntity)

    @Query("DELETE FROM pullrequest_table")
    fun deleteAll()
}
