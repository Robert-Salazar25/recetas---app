package com.example.recetas.di

import com.example.recetas.data.remote.RecetaApiService
import com.example.recetas.data.repository.RecetaRepositoryImpl
import com.example.recetas.domain.repository.RecetaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): RecetaApiService {
        return retrofit.create(RecetaApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: RecetaApiService): RecetaRepository {
        return RecetaRepositoryImpl(apiService)
    }
}