package com.candra.katalog_film.core.data.source.local

import com.candra.katalog_film.core.data.source.local.entity.ModelEntity
import com.candra.katalog_film.core.data.source.local.room.ModelDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val movieDao: ModelDao
){
    fun getAllFavoriteAny(): Flow<List<ModelEntity>> = movieDao.getAllFavoriteModelEntity()
    suspend fun insertToFavoriteEntity(modelEntity: ModelEntity) = movieDao.insertToFavorite(modelEntity)
    suspend fun deleteFromFavorite(modelEntity: ModelEntity) = movieDao.deleteFromFavorite(modelEntity)
    fun isFavorite(title: String) = movieDao.isFavoriteEntity(title)
}