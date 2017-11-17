package alex.com.githubchecker.components.app;

import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;

import alex.com.githubchecker.BuildConfig;

import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;
import static android.os.PowerManager.FULL_WAKE_LOCK;
import static android.os.PowerManager.ON_AFTER_RELEASE;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

/**
 * Created by Alex on 11/17/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        if (BuildConfig.DEBUG) {
            turnOnScreen();
        }
    }

    public void turnOnScreen() {
        getWindow().addFlags(FLAG_SHOW_WHEN_LOCKED);

        PowerManager power = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock lock = power.newWakeLock(FULL_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP | ON_AFTER_RELEASE, "wakeup!");
        lock.acquire();
        lock.release();
    }

}
