package alex.com.githubchecker.models.room.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Alex on 11/17/2017.
 */

@Entity(tableName = "user_table")
class UserEntity {
    @PrimaryKey
    @NonNull
    var id: String = ""
    var login: String? = null
}

