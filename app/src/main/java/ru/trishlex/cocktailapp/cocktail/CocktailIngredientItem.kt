package ru.trishlex.cocktailapp.cocktail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.CocktailIngredientDTO
import ru.trishlex.cocktailapp.ingredient.IngredientItem

class CocktailIngredientItem (
    id: Int,
    name: String,
    preview: Bitmap,
    val amount: Int,
    val unit: String
) : IngredientItem(id, name, preview) {

    constructor(cocktailIngredientDTO: CocktailIngredientDTO) : this(
        cocktailIngredientDTO.id,
        cocktailIngredientDTO.name,
        BitmapFactory.decodeByteArray(cocktailIngredientDTO.preview, 0, cocktailIngredientDTO.preview.size),
        cocktailIngredientDTO.amount,
        cocktailIngredientDTO.unit
    )
}