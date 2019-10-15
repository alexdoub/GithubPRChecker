package alex.com.githubchecker.models.api

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Alex on 11/17/2017.
 */

@Entity(tableName = "pullrequest_table")
class R_PullRequest {

    //Accessors methods
    @PrimaryKey
    @NonNull
    var id: String = ""
}

