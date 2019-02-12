package alex.com.githubchecker.components.app.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Created by Alex on 11/11/2017.
 */

@Module
class AppModule @Inject
constructor(internal var appContext: Context) {

    @Provides
    internal fun provideContext(): Context {
        return appContext
    }
}

