package com.enigmatech.hilt.repository

import com.enigmatech.hilt.retrofit.BlogRetrofit
import com.enigmatech.hilt.retrofit.NetworkMapper
import com.enigmatech.hilt.room.BlogDao
import com.enigmatech.hilt.room.CacheMapper
import com.enigmatech.hilt.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
    var blogDao: BlogDao,
    var blogRetrofit: BlogRetrofit,
    var cacheMapper: CacheMapper,
    var networkMapper: NetworkMapper
) {

    suspend fun getBlog() = flow {
        emit(DataState.Loading)
        delay(1000)

        try {

            val networkBlogs = blogRetrofit.getBlogs()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)

            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }

            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))


        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }

}