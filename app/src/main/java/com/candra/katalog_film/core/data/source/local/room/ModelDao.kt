package com.candra.katalog_film.core.data.source.local.room

import androidx.room.*
import com.candra.katalog_film.core.data.source.local.entity.ModelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ModelDao {

    @Query("SELECT * FROM `database`")
    fun getAllFavoriteModelEntity(): Flow<List<ModelEntity>>

    @Delete
    suspend fun deleteFromFavorite(modelEntity: ModelEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavorite(modelEntity: ModelEntity)

    @Query("SELECT EXISTS(SELECT * FROM `database` WHERE title = :title )")
    fun isFavoriteEntity(title: String): Flow<Boolean>
}