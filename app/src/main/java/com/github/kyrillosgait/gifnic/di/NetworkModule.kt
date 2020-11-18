package com.github.kyrillosgait.gifnic.di

import android.content.Context
import com.github.kyrillosgait.gifnic.data.remote.Api
import com.github.kyrillosgait.gifnic.data.remote.BASE_URl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit

private const val CACHE_SIZE = 10 * 1024 * 1024

private enum class Interceptors { LOGGING }

val networkModule = module {

    single { provideOkHttpCache(get()) }

    single(named(Interceptors.LOGGING.name)) { provideHttpLoggingInterceptor() }

    single { provideOkHttpClient(get(named(Interceptors.LOGGING.name))) }

    single { provideConverterFactory() }

    single { provideRetrofit(BASE_URl, get(), get()) }

    single { provideService(get()) }
}

private fun provideOkHttpCache(context: Context): Cache {
    return Cache(context.cacheDir, CACHE_SIZE.toLong())
}

private fun provideHttpLoggingInterceptor(): Interceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }
}

private fun provideOkHttpClient(httpLoggingInterceptor: Interceptor): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)

    return okHttpClientBuilder.build()
}

private fun provideConverterFactory(): Converter.Factory {
    val contentType = "application/json; charset=utf-8".toMediaType()

    return Json { ignoreUnknownKeys = true }.asConverterFactory(contentType)
}

private fun provideRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    converterFactory: Converter.Factory
): Retrofit {
    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)

    return retrofitBuilder.build()
}

private fun provideService(retrofit: Retrofit): Api {
    return retrofit.create(Api::class.java)
}