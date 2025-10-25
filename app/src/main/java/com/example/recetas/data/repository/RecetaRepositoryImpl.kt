package com.example.recetas.data.repository

import android.util.Log
import com.example.recetas.domain.model.Receta
import com.example.recetas.domain.repository.RecetaRepository
import com.example.recetas.domain.model.RecetaResponse
import com.example.recetas.data.remote.RecetaApiService
import com.example.recetas.domain.model.toReceta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RecetaRepositoryImpl @Inject constructor(
    private val apiService: RecetaApiService
) : RecetaRepository {
    override fun getRecetas(): Flow<List<Receta>> = flow {
        try {
            val response = apiService.getRecetas()
            if (response.isSuccessful) {
                response.body()?.meals?.map { it.toReceta() }?.let { emit(it) }
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            Log.e("Repository", "API Error", e)
            emit(emptyList())
        }
    }

    override suspend fun getRecetaById(id: String): Receta? {
        return try {
            val response: Response<RecetaResponse> = apiService.getRecetaById(id)

            if (response.isSuccessful) {
                response.body()?.meals?.firstOrNull()?.let { recetaApi ->
                    recetaApi.toReceta() // Conversión a tu modelo de dominio
                }
            } else {
                Log.e("API", "Error: ${response.code()} - ${response.errorBody()}")
                null
            }
        } catch (e: Exception) {
            Log.e("API", "Network error", e)
            null
        }
    }

    override suspend fun searchRecetas(query: String): List<Receta> {
        return try {
            val response = apiService.searchRecetas(query)
            if (response.isSuccessful){
                response.body()?.meals?.map { it.toReceta() } ?: emptyList()
            } else{
                emptyList()
            }
        } catch (e: Exception){
            emptyList()
        }
    }
}