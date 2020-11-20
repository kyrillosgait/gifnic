package com.github.kyrillosgait.gifnic.di

import com.github.kyrillosgait.gifnic.data.GifRepository
import com.github.kyrillosgait.gifnic.data.repositories.GiphyRepository
import org.koin.dsl.module

val repositoriesModule = module {

    single<GifRepository> { GiphyRepository(get()) }
}
