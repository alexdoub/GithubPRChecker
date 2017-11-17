package alex.com.githubchecker.components.app;

import android.app.Application;

import com.facebook.stetho.Stetho;

import alex.com.githubchecker.components.app.dagger.AppComponent;
import alex.com.githubchecker.components.app.dagger.AppModule;
import alex.com.githubchecker.components.app.dagger.DaggerAppComponent;
import alex.com.githubchecker.components.app.dagger.DataModule;
import alex.com.githubchecker.components.app.dagger.NetworkModule;
import alex.com.githubchecker.components.github.dagger.DaggerGithubComponent;
import alex.com.githubchecker.components.github.dagger.GithubComponent;
import alex.com.githubchecker.components.github.dagger.GithubModule;
import timber.log.Timber;

/**
 * Created by Alex on 11/17/2017.
 */

public class GithubCheckerApp extends Application {

    private static AppComponent _AppComponent;
    private static GithubComponent _GithubComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        Timber.plant(new Timber.DebugTree());

        _AppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .dataModule(new DataModule()).build();
    }

    public static AppComponent getAppComponent() {
        return _AppComponent;
    }

    public static GithubComponent getGithubComponent() {
        if (_GithubComponent == null) {
            _GithubComponent = DaggerGithubComponent.builder()
                    .appComponent(getAppComponent())
                    .githubModule(new GithubModule()).build();
        }
        return _GithubComponent;
    }
}
