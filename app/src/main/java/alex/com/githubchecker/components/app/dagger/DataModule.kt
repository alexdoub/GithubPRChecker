package alex.com.githubchecker.components.app.dagger

import alex.com.githubchecker.components.app.data.DataManager
import alex.com.githubchecker.config.Constants
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Created by Alex on 11/11/2017.
 */

@Module
class DataModule @Inject
constructor() {

    @Provides
    internal fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Provides
    internal fun providesDatamanager(): DataManager {
        return DataManager()
    }
}

