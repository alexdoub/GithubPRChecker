package alex.com.githubchecker.components.app

import alex.com.githubchecker.BuildConfig
import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP
import android.os.PowerManager.FULL_WAKE_LOCK
import android.os.PowerManager.ON_AFTER_RELEASE
import android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Alex on 11/17/2017.
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)

        if (BuildConfig.DEBUG) {
            turnOnScreen()
        }
    }

    fun turnOnScreen() {
        window.addFlags(FLAG_SHOW_WHEN_LOCKED)

        val power = getSystemService(Context.POWER_SERVICE) as PowerManager
        val lock = power.newWakeLock(FULL_WAKE_LOCK or ACQUIRE_CAUSES_WAKEUP or ON_AFTER_RELEASE, "wakeup!")
        lock.acquire()
        lock.release()
    }

}
