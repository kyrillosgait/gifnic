package com.github.kyrillosgait.gifnic

import android.app.Application
import com.github.kyrillosgait.gifnic.di.networkModule
import com.github.kyrillosgait.gifnic.di.repositoriesModule
import com.github.kyrillosgait.gifnic.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

private val gifnicModules = listOf(
    networkModule,
    repositoriesModule,
    viewModelsModule
)

class GifnicApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@GifnicApplication)
            modules(gifnicModules)
        }
    }
}