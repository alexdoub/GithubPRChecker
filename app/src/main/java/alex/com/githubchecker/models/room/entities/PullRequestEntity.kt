package alex.com.githubchecker.models.room.entities

import alex.com.githubchecker.models.api.Commit
import alex.com.githubchecker.models.api.PullRequest
import androidx.annotation.NonNull
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName

/**
 * Created by Alex on 11/17/2017.
 */

@Entity(tableName = "pullrequest_table",
        indices = [Index(value = ["id"])])
class PullRequestEntity(@PrimaryKey
                        @NonNull
                        var id: Int) {
    var title: String? = null
    var number: Int? = null
    var createdAt: String? = null
    var diffUrl: String? = null
    var commitId: Int? = null
}
