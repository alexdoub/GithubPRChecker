package alex.com.githubchecker.components.app.dagger;

import android.content.Context;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alex on 11/11/2017.
 */

@Module
public class AppModule {

    Context appContext;

    @Inject
    public AppModule(Context context) {
        appContext = context;
    }

    @Provides
    Context provideContext() {
        return appContext;
    }

}

