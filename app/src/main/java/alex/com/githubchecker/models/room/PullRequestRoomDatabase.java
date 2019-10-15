package alex.com.githubchecker.models.room;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import alex.com.githubchecker.models.api.PullRequest;
import alex.com.githubchecker.models.api.R_PullRequest;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {R_PullRequest.class}, version = 1)
public abstract class PullRequestRoomDatabase extends RoomDatabase {

    public abstract PullRequestDao wordDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile PullRequestRoomDatabase INSTANCE;

    static PullRequestRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PullRequestRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PullRequestRoomDatabase.class, "pullrequest_database")
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
//        PopulateDbAsync(PullRequestRoomDatabase db) {
//            mDao = db.wordDao();
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
