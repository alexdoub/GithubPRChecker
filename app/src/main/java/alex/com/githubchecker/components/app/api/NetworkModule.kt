package alex.com.githubchecker.components.app.api

import alex.com.githubchecker.config.Constants
import alex.com.githubchecker.utils.SchedulerUtils
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

/**
 * Created by Alex on 11/11/2017.
 */

@Module
class NetworkModule {

    @Provides
    internal fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .readTimeout(10, SECONDS)
                .writeTimeout(10, SECONDS)
                .connectTimeout(10, SECONDS)
                .build()
    }

    @Provides
    internal fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.GITHUB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(SchedulerUtils.io()))
                .build()
    }

    @Provides
    internal fun provideGithubService(retrofit: Retrofit): GithubAPI {
        return retrofit.create(GithubAPI::class.java)
    }

    @Provides
    internal fun provideAPIClient(githubAPI: GithubAPI, client: OkHttpClient): APIClient {
        return APIClient(client, githubAPI)
    }

}

