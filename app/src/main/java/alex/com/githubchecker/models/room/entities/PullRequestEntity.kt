package alex.com.githubchecker.models.room.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

/**
 * Created by Alex on 11/17/2017.
 */

@Entity(tableName = "pullrequest_table",
        primaryKeys = ["pull_request_id"],
        indices = [Index(value = ["pull_request_id"], unique = true),
            Index(value = ["commitSha"], unique = true)])
class PullRequestEntity(@NonNull
                        @ColumnInfo(name = "pull_request_id")
                        var id: Int) {
    var title: String? = null
    var number: Int? = null
    var created_at: String? = null
    var diff_url: String? = null
    var commitSha: String? = null
}
