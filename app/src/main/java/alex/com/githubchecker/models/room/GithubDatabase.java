package alex.com.githubchecker.models.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;

import alex.com.githubchecker.models.room.dao.PullRequestDao;
import alex.com.githubchecker.models.room.entities.CommitEntity;
import alex.com.githubchecker.models.room.entities.PullRequestEntity;
import alex.com.githubchecker.models.room.entities.UserEntity;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {
        PullRequestEntity.class,
        CommitEntity.class,
        UserEntity.class}, version = 3)
public abstract class GithubDatabase extends RoomDatabase {

    public abstract PullRequestDao pullRequestDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile GithubDatabase INSTANCE;

    static GithubDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GithubDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GithubDatabase.class, "github_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
