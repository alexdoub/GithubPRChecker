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
                parentColumns = ["commitSha"], //from ColumnInfo of User class
                childColumns = ["sha"],
                onUpdate = CASCADE,
                onDelete = CASCADE)],
        indices = [Index(value = ["sha"], unique = true)])
class CommitEntity(@NonNull
                   @PrimaryKey
                   var sha: String) {
    var userId: Int? = null
}
