package com.enigmatech.hilt.di

import com.enigmatech.hilt.repository.MainRepository
import com.enigmatech.hilt.retrofit.BlogRetrofit
import com.enigmatech.hilt.retrofit.NetworkMapper
import com.enigmatech.hilt.room.BlogDao
import com.enigmatech.hilt.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        blogRetrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(
            blogDao, blogRetrofit, cacheMapper, networkMapper
        )
    }

}