package com.example.recetas.domain.model

import android.util.Log
import com.google.gson.annotations.SerializedName

data class Receta(
    val id: String,
    val nombre: String,
    val imagen: String,
    val ingredientes: List<String>,
    val instrucciones: String,
    val categoria: String
)

data class RecetaResponse(
    @SerializedName("meals")
    val meals: List<RecetaApi>?
)

data class RecetaApi(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strInstructions: String,
    val strCategory: String,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4:String?,
    val strIngredient5:String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strIngredient16: String?,
    val strIngredient17: String?,
    val strIngredient18: String?,
    val strIngredient19: String?,
    val strIngredient20: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
    val strMeasure16: String?,
    val strMeasure17: String?,
    val strMeasure18: String?,
    val strMeasure19: String?,
    val strMeasure20: String?,

)

fun RecetaApi.toReceta(): Receta {
    val ingredients = mutableListOf<String>()

    try {
        for (i in 1..20) {
            val ingredientField = RecetaApi::class.java.getDeclaredField("strIngredient$i")
            val measureField = RecetaApi::class.java.getDeclaredField("strMeasure$i")

            ingredientField.isAccessible = true
            measureField.isAccessible = true

            val ingredient = ingredientField.get(this) as? String
            val measure = measureField.get(this) as? String

            if (!ingredient.isNullOrBlank()) {
                ingredients.add("$ingredient ${measure?.takeUnless { it.isNullOrBlank() } ?: ""}".trim())
            }
        }
    } catch (e: Exception) {
        Log.e("RecetaConversion", "Error al convertir RecetaApi", e)
    }

    return Receta(
        id = idMeal,
        nombre = strMeal,
        imagen = strMealThumb,
        ingredientes = ingredients,
        instrucciones = strInstructions,
        categoria = strCategory
    )
}
