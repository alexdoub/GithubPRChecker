package alex.com.githubchecker.components.app.dagger;

import android.content.Context;

import alex.com.githubchecker.components.app.api.APIClient;
import alex.com.githubchecker.components.app.data.DataManager;
import dagger.Component;

/**
 * Created by Alex on 11/12/2017.
 */
@AppScope
@Component(modules = {AppModule.class, NetworkModule.class, DataModule.class})
public interface AppComponent {
    APIClient provideAPIClient();

    DataManager provideDataManager();

    Context provideContext();
}
