package ru.trishlex.cocktailapp.cocktail.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.CocktailIngredientDTO

class CocktailIngredientItem (
    id: Int,
    name: String,
    preview: Bitmap,
    val amount: Int,
    val unit: String
) : CocktailItemIngredient(id, name, preview) {

    constructor(cocktailIngredientDTO: CocktailIngredientDTO) : this(
        cocktailIngredientDTO.id,
        cocktailIngredientDTO.name,
        BitmapFactory.decodeByteArray(cocktailIngredientDTO.preview, 0, cocktailIngredientDTO.preview.size),
        cocktailIngredientDTO.amount,
        cocktailIngredientDTO.unit
    )
}