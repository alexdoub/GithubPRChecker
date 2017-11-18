package alex.com.githubchecker.components.app.dagger;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import alex.com.githubchecker.components.app.api.APIClient;
import alex.com.githubchecker.components.app.api.GithubAPI;
import alex.com.githubchecker.config.Constants;
import alex.com.githubchecker.utils.SchedulerUtils;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Alex on 11/11/2017.
 */

@Module
public class NetworkModule {

    @Provides
    OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .readTimeout(10, SECONDS)
                .writeTimeout(10, SECONDS)
                .connectTimeout(10, SECONDS)
                .build();
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.GITHUB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(SchedulerUtils.io()))
                .build();
    }

    @Provides
    GithubAPI provideGithubService(Retrofit retrofit) {
        return retrofit.create(GithubAPI.class);
    }

    @Provides
    APIClient provideAPIClient(GithubAPI githubAPI, OkHttpClient client) {
        return new APIClient(client, githubAPI, Constants.GITHUB_USER, Constants.GITHUB_REPO);
    }

}

