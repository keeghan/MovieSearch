package com.keeghan.movieinfo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.keeghan.movieinfo.network.MovieApi
import com.keeghan.movieinfo.repository.EpisodeRepository
import com.keeghan.movieinfo.repository.EpisodeRepositoryImpl
import com.keeghan.movieinfo.utils.Constants.BASE_URL
import com.keeghan.movieinfo.utils.Constants.RETROFIT_CONNECT_TIMEOUT
import com.keeghan.movieinfo.utils.Constants.RETROFIT_READ_TIMEOUT
import com.keeghan.movieinfo.utils.Constants.RETROFIT_WRITE_TIMEOUT
import com.keeghan.movieinfo.utils.Constants.APIKEY
import com.keeghan.movieinfo.utils.Constants.HOST
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

@Module
@InstallIn(SingletonComponent::class)
object AppServiceModule {

    @Provides
    @Singleton
    fun provideOkhttp(): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        okHttpClient.addInterceptor(logging)
            .connectTimeout(RETROFIT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(RETROFIT_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(RETROFIT_WRITE_TIMEOUT, TimeUnit.SECONDS)
        //application intercept for RAPIDApi
        okHttpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", APIKEY)
                .addHeader("X-RapidAPI-Host", HOST)
                .build()
            chain.proceed(request)
        }

        return okHttpClient
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient.Builder): MovieApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
            .create(MovieApi::class.java)


    @Provides
    @Singleton
    @Named("mainRepository")
    fun provideMyRepository1(api: MovieApi): EpisodeRepository {
        return EpisodeRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSettingsDataStorePreferences(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.settingsDataStore
    }


    @Provides
    @Named("mainDispatcher")
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Named("ioDispatcher")
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Named("defaultDispatcher")
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

}