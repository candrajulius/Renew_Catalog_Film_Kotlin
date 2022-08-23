package com.candra.katalog_film.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.candra.katalog_film.core.data.source.local.entity.ModelEntity

@Database(entities = [ModelEntity::class],version = 1,exportSchema = false)
abstract class ModelDatabase: RoomDatabase(){
    abstract fun modelDao(): ModelDao
}