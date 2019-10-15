package alex.com.githubchecker.components.app.data

import alex.com.githubchecker.config.Constants
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Alex on 11/12/2017.
 */

class SessionDatamanager {
    val currentRepoSubject = BehaviorSubject.createDefault(Constants.GITHUB_REPO)
    val currentUserSubject = BehaviorSubject.createDefault(Constants.GITHUB_USER)
}
