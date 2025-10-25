package com.example.recetas.domain.repository

import com.example.recetas.domain.model.Receta
import kotlinx.coroutines.flow.Flow

interface RecetaRepository {
    fun getRecetas(): Flow<List<Receta>>
    suspend fun getRecetaById(id: String): Receta?
   suspend fun searchRecetas(query: String): List<Receta>
}