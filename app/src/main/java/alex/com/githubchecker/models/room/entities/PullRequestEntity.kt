package alex.com.githubchecker.models.room.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Alex on 11/17/2017.
 */

@Entity(tableName = "pullrequest_table")
class PullRequestEntity(@PrimaryKey
                        @NonNull
                        var id: Int) {
    var title: String? = null
    var number: Int? = null
    var createdAt: String? = null
    var diffUrl: String? = null
    var commitId: Int? = null
}

