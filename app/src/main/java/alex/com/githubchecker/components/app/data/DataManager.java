package alex.com.githubchecker.components.app.data;

import alex.com.githubchecker.config.Constants;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Alex on 11/12/2017.
 */

public class DataManager {

    private BehaviorSubject<String> currentRepoSubject = BehaviorSubject.createDefault(Constants.GITHUB_REPO);
    private BehaviorSubject<String> currentUserSubject = BehaviorSubject.createDefault(Constants.GITHUB_USER);

    public DataManager() {
    }

    public BehaviorSubject<String> getCurrentRepoSubject() {
        return currentRepoSubject;
    }

    public BehaviorSubject<String> getCurrentUserSubject() {
        return currentUserSubject;
    }
}


