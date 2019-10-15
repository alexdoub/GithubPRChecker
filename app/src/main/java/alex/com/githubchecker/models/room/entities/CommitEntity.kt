package alex.com.githubchecker.models.room.entities

import androidx.annotation.NonNull
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName

/**
 * Created by Alex on 11/17/2017.
 */

@Entity(tableName = "commit_table",
        foreignKeys = [ForeignKey(
                entity = PullRequestEntity::class,
                parentColumns = ["commitId"], //from ColumnInfo of User class
                childColumns = ["id"],
                onDelete = CASCADE)],
        indices = [Index(value = ["userId"])])
class CommitEntity(@PrimaryKey
                   @NonNull
                   var id: Int) {
    var userId: Int? = null
}

class NestedPullRequest {
    @Embedded
    lateinit var pullRequest: PullRequestEntity
    @Embedded
    lateinit var commit: CommitEntity
}

