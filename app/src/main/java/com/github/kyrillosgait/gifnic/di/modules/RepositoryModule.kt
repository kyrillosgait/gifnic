package com.github.kyrillosgait.gifnic.di.modules

import com.github.kyrillosgait.gifnic.data.GifRepository
import com.github.kyrillosgait.gifnic.data.repositories.GiphyRepository
import dagger.Binds
import dagger.Module
import kotlinx.serialization.UnstableDefault

/**
 * A [Module] that provides repository implementation.
 */
@Module @UnstableDefault
interface RepositoryModule {

    @Binds
    fun bindGifRepository(repo: GiphyRepository): GifRepository
}