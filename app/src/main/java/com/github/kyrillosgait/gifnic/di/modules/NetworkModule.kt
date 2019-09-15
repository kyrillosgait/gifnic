package com.github.kyrillosgait.gifnic.di.modules

import android.app.Application
import com.github.kyrillosgait.gifnic.data.remote.Api
import com.github.kyrillosgait.gifnic.data.remote.BASE_URl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import tech.linjiang.pandora.Pandora
import javax.inject.Named
import javax.inject.Singleton

/**
 * A [Module] that provides the [Api].
 */
@Module
object NetworkModule {

    @Named("BASE_URL")
    @Provides @JvmStatic @Singleton
    internal fun providesBaseUrl(): String = BASE_URl

    @Provides @JvmStatic @Singleton
    internal fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides @JvmStatic @Singleton
    internal fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val pandoraInterceptor = Pandora.get().interceptor

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(pandoraInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides @JvmStatic @Singleton @UnstableDefault
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("BASE_URL") baseUrl: String
    ): Retrofit {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(Json.nonstrict.asConverterFactory(mediaType))
            .build()
    }

    @Provides @JvmStatic @Singleton
    internal fun providesApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}