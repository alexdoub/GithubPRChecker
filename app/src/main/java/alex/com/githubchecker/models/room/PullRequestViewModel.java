package alex.com.githubchecker.models.room;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import alex.com.githubchecker.models.room.entities.PullRequestEntity;

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */
//
//public class PullRequestViewModel extends AndroidViewModel {
//
//    private GithubRepository mRepository;
//    // Using LiveData and caching what getPullRequestsSorted returns has several benefits:
//    // - We can put an observer on the data (instead of polling for changes) and only update the
//    //   the UI when the data actually changes.
//    // - Repository is completely separated from the UI through the ViewModel.
//    private LiveData<List<PullRequestEntity>> mAllPullRequests;
//
//    public PullRequestViewModel(Application application) {
//        super(application);
//        mRepository = new GithubRepository(application);
//        mAllPullRequests = mRepository.getAllPullRequests();
//    }
//
//    LiveData<List<PullRequestEntity>> getAllWords() {
//        return mAllPullRequests;
//    }
//
//    void insert(PullRequestEntity pullRequest) {
//        mRepository.insert(pullRequest);
//    }
//}
