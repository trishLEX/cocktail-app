package ru.trishlex.cocktailapp.cocktail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.CocktailIngredientDTO

data class CocktailIngredientItem (
    val id: Int,
    val name: String,
    val preview: Bitmap,
    val amount: Int,
    val unit: String
) {

    constructor(cocktailIngredientDTO: CocktailIngredientDTO) : this(
        cocktailIngredientDTO.id,
        cocktailIngredientDTO.name,
        BitmapFactory.decodeByteArray(cocktailIngredientDTO.preview, 0, cocktailIngredientDTO.preview.size),
        cocktailIngredientDTO.amount,
        cocktailIngredientDTO.unit
    )
}