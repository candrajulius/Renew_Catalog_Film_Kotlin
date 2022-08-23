package com.candra.katalog_film.core.di

import com.candra.katalog_film.core.domain.usecase.MovieInteract
import com.candra.katalog_film.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideMovieTourismCase(movieInteract: MovieInteract): MovieUseCase
}