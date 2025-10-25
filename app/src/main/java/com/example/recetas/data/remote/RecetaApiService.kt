package com.example.recetas.data.remote

import com.example.recetas.domain.model.RecetaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecetaApiService {
    @GET("api/json/v1/1/search.php?s=")
    suspend fun getRecetas(): Response<RecetaResponse>

    @GET("api/json/v1/1/lookup.php")
    suspend fun getRecetaById(
        @Query("i") id: String  // Búsqueda por ID
    ): Response<RecetaResponse>

    @GET("api/json/v1/1/search.php")
    suspend fun searchRecetas(
        @Query("s") query: String
    ): Response<RecetaResponse>
}