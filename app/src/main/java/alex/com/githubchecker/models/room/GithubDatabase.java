package alex.com.githubchecker.models.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

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
        UserEntity.class}, version = 1)
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
//                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    /**
//     * Override the onOpen method to populate the database.
//     * For this sample, we clear the database every time it is created or opened.
//     *
//     * If you want to populate the database only when the database is created for the 1st time,
//     * override RoomDatabase.Callback()#onCreate
//     */
//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//            // If you want to keep the data through app restarts,
//            // comment out the following line.
//            new PopulateDbAsync(INSTANCE).execute();
//        }
//    };
//
//    /**
//     * Populate the database in the background.
//     * If you want to start with more words, just add them.
//     */
//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//
//        private final PullRequestDao mDao;
//
//        PopulateDbAsync(GithubDatabase db) {
//            mDao = db.pullRequestDao();
//        }
//
//        @Override
//        protected Void doInBackground(final Void... params) {
//            // Start the app with a clean database every time.
//            // Not needed if you only populate on creation.
//            mDao.deleteAll();
//
//            Word word = new Word("Hello");
//            mDao.insert(word);
//            word = new Word("World");
//            mDao.insert(word);
//            return null;
//        }
//    }

}
