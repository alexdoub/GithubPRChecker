package alex.com.githubchecker.models.room.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Alex on 11/17/2017.
 */

@Entity(tableName = "commit_table",
        foreignKeys = [ForeignKey(
                entity = PullRequestEntity::class,
                parentColumns = ["commitSha"], //from ColumnInfo of User class
                childColumns = ["sha"],
                onUpdate = CASCADE,
                onDelete = CASCADE)],
        indices = [Index(value = ["sha"], unique = true)])
class CommitEntity(@NonNull
                   @PrimaryKey
                   var sha: String,
                   var userId: Int)
