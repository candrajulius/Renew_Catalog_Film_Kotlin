package com.candra.katalog_film.core.di

import com.candra.katalog_film.core.domain.repository.MovieRepository
import com.candra.katalog_film.core.domain.repository.IRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract  class RepositoryModule {
    @Binds
    abstract fun provideRepository(repository: MovieRepository): IRepository
}