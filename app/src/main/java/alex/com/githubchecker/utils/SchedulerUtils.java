package alex.com.githubchecker.utils;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Alex on 11/17/2017.
 */

public class SchedulerUtils {

    public static Scheduler io() {
        return Schedulers.io();
    }

    public static Scheduler compute() {
        return Schedulers.computation();
    }

    public static Scheduler main() {
        return AndroidSchedulers.mainThread();
    }
}
