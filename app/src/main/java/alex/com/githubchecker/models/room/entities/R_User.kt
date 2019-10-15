package alex.com.githubchecker.models.api

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Alex on 11/17/2017.
 */

@Entity(tableName = "user_table")
class R_User {
    @PrimaryKey
    @NonNull
    val id: String = ""
}

