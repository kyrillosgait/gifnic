package com.github.kyrillosgait.gifnic.di.modules

import android.app.Application
import com.github.kyrillosgait.gifnic.data.remote.Api
import com.github.kyrillosgait.gifnic.data.remote.BASE_URl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

/**
 * A [Module] that provides the [Api] dependencies.
 */
@Module
object NetworkModule {

    @Named("BASE_URL")
    @Provides @JvmStatic @Singleton
    internal fun provideBaseUrl(): String = BASE_URl

    @Provides @JvmStatic @Singleton
    internal fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides @JvmStatic @Singleton
    internal fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @JvmStatic
    @Singleton
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("BASE_URL") baseUrl: String
    ): Retrofit {
        val contentType = "application/json; charset=utf-8".toMediaType()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .build()
    }

    @Provides @JvmStatic @Singleton
    internal fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}