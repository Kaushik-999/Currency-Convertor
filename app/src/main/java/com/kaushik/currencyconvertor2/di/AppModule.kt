package com.kaushik.currencyconvertor2.di

import com.kaushik.currencyconvertor2.data.Credentials
import com.kaushik.currencyconvertor2.data.CurrencyApiService
import com.kaushik.currencyconvertor2.main.DefaultMainRepository
import com.kaushik.currencyconvertor2.main.MainRepository
import com.kaushik.currencyconvertor2.utils.DispatcherProvider
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
import javax.inject.Singleton


val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
val okHttp = OkHttpClient.Builder().addInterceptor(logger).callTimeout(10,TimeUnit.SECONDS)
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApiService(): CurrencyApiService = Retrofit.Builder()
        .baseUrl(Credentials.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
        .build()
        .create(CurrencyApiService::class.java)

    @Singleton
    @Provides
    // this fun is dependent on provideApiService() above to get apiService: CurrencyApiService
    fun provideMainRepository(apiService: CurrencyApiService): MainRepository = DefaultMainRepository(apiService)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val MAIN: CoroutineDispatcher
            get() = Dispatchers.Main
        override val IO: CoroutineDispatcher
            get() = Dispatchers.IO
        override val DEFAULT: CoroutineDispatcher
            get() = Dispatchers.Default
        override val UNCONFINED: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

}