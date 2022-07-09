package ru.trishlex.cocktailapp.cocktail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.CocktailIngredientDTO
import org.openapitools.client.model.CocktailLightDTO

data class CocktailItemView(
    val id: Int,
    val name: String,
    val image: Bitmap,
    val ingredients: List<CocktailItemIngredient>
) {
    constructor(cocktailLightDTO: CocktailLightDTO) : this(
        cocktailLightDTO.id,
        cocktailLightDTO.name,
        BitmapFactory.decodeByteArray(cocktailLightDTO.preview, 0, cocktailLightDTO.preview.size),
        cocktailLightDTO.ingredients.map { CocktailItemIngredient(it) }
    )
}

data class CocktailItemIngredient(val name: String, val preview: Bitmap) {

    constructor(cocktailIngredientDTO: CocktailIngredientDTO) : this(
        cocktailIngredientDTO.name,
        BitmapFactory.decodeByteArray(cocktailIngredientDTO.preview, 0, cocktailIngredientDTO.preview.size)
    )
}