package com.keeghan.movieinfo.di

import com.keeghan.movieinfo.network.IMDBApi
import com.keeghan.movieinfo.repository.EpisodeRepository
import com.keeghan.movieinfo.repository.EpisodeRepositoryImpl
import com.keeghan.movieinfo.utils.Constants.Companion.BASE_URL
import com.keeghan.movieinfo.utils.Constants.Companion.RETROFIT_CONNECT_TIMEOUT
import com.keeghan.movieinfo.utils.Constants.Companion.RETROFIT_READ_TIMEOUT
import com.keeghan.movieinfo.utils.Constants.Companion.RETROFIT_WRITE_TIMEOUT
import com.keeghan.movieinfo.utils.Constants.Companion.apiKey
import com.keeghan.movieinfo.utils.Constants.Companion.apiKey2
import com.keeghan.movieinfo.utils.Constants.Companion.apiKey3
import com.keeghan.movieinfo.utils.Constants.Companion.host
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
                .addHeader("X-RapidAPI-Key", apiKey3)
                .addHeader("X-RapidAPI-Host", host)
                .build()
            chain.proceed(request)
        }

        return okHttpClient
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient.Builder): IMDBApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
            .create(IMDBApi::class.java)


    @Provides
    @Singleton
    @Named("mainRepository")
    fun provideMyRepository1(api: IMDBApi): EpisodeRepository {
        return EpisodeRepositoryImpl(api)
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