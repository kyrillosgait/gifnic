package com.github.kyrillosgait.gifnic.di

import android.app.Application
import android.content.Context
import com.github.kyrillosgait.gifnic.di.modules.NetworkModule
import com.github.kyrillosgait.gifnic.di.modules.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class
    ]
)
@Singleton
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
            @BindsInstance application: Application
        ): ApplicationComponent
    }

    val knownViewModels: KnownViewModels
}