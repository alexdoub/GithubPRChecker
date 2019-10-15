package alex.com.githubchecker.models.room.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Alex on 11/17/2017.
 */

@Entity(tableName = "commit_table")
class CommitEntity(@PrimaryKey
                   @NonNull
                   var id: Int) {
    var userId: Int? = null
}

