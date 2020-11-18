package com.github.kyrillosgait.gifnic

import android.app.Application
import com.github.kyrillosgait.gifnic.di.ComponentProvider
import com.github.kyrillosgait.gifnic.di.DaggerApplicationComponent
import timber.log.Timber

class GifnicApplication : Application(), ComponentProvider {

    override val component by lazy {
        DaggerApplicationComponent.factory().create(applicationContext, this)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}