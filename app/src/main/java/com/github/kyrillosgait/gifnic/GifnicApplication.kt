package com.github.kyrillosgait.gifnic

import android.app.Application
import com.github.kyrillosgait.gifnic.di.ComponentProvider
import com.github.kyrillosgait.gifnic.di.DaggerApplicationComponent
import kotlinx.serialization.UnstableDefault
import timber.log.Timber

@UnstableDefault
class GifnicApplication : Application(), ComponentProvider {

    override val component by lazy {
        DaggerApplicationComponent.factory().create(applicationContext, this)
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}