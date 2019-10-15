package alex.com.githubchecker.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Alex on 11/17/2017.
 */

object SchedulerUtils {

    fun io(): Scheduler {
        return Schedulers.io()
    }

    fun compute(): Scheduler {
        return Schedulers.computation()
    }

    fun main(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
