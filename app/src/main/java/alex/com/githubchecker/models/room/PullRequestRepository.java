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

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import alex.com.githubchecker.models.api.PullRequest;
import alex.com.githubchecker.models.api.R_PullRequest;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

public class PullRequestRepository {

    private PullRequestDao mPullRequestDao;
    private LiveData<List<R_PullRequest>> mAllWords;

    // Note that in order to unit test the PullRequestRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public PullRequestRepository(Application application) {
        PullRequestRoomDatabase db = PullRequestRoomDatabase.getDatabase(application);
        mPullRequestDao = db.wordDao();
//        mAllWords = mPullRequestDao.getPullRequestsSorted();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<R_PullRequest>> getAllWords() {
        return mAllWords;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    void insert(R_PullRequest word) {
        new insertAsyncTask(mPullRequestDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<R_PullRequest, Void, Void> {

        private PullRequestDao mAsyncTaskDao;

        insertAsyncTask(PullRequestDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final R_PullRequest... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
