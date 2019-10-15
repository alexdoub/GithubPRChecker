package alex.com.githubchecker.models.api

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Alex on 11/17/2017.
 */

@Entity(tableName = "commit_table")
class R_Commit {
    @PrimaryKey
    @NonNull
    val id: String = ""
}

