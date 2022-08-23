package com.candra.katalog_film.core.di

import android.content.Context
import androidx.room.Room
import com.candra.katalog_film.core.data.source.local.room.ModelDao
import com.candra.katalog_film.core.data.source.local.room.ModelDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ModelDatabase =
        Room.databaseBuilder(
            context,
            ModelDatabase::class.java,"Model.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Provides
    fun provideModelDao(database: ModelDatabase): ModelDao = database.modelDao()
}