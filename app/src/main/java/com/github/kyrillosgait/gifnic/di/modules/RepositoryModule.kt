package com.github.kyrillosgait.gifnic.di.modules

import com.github.kyrillosgait.gifnic.data.GifRepository
import com.github.kyrillosgait.gifnic.data.repositories.GiphyRepository
import dagger.Binds
import dagger.Module
import kotlinx.serialization.UnstableDefault

/**
 * A [Module] which provides the implementation of each repository.
 */
@Module
interface RepositoryModule {

    @Binds @UnstableDefault
    fun bindGifRepository(repo: GiphyRepository): GifRepository
}