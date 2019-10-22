package alex.com.githubchecker.models.room.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Alex on 11/17/2017.
 */

@Entity(tableName = "user_table",
        indices = [Index(value = ["user_id"])])
class UserEntity(@PrimaryKey
                 @NonNull
                 @ColumnInfo(name = "user_id")
                 var id: Int) {
    var login: String? = null
}

