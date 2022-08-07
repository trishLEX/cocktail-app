package ru.trishlex.cocktailapp.cocktail.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.CocktailIngredientDTO
import ru.trishlex.cocktailapp.ingredient.model.IngredientType

class CocktailIngredientItem (
    id: Int,
    name: String,
    preview: Bitmap,
    ingredientType: IngredientType,
    val amount: Int,
    val unit: String
) : CocktailItemIngredient(id, name, preview, ingredientType) {

    constructor(cocktailIngredientDTO: CocktailIngredientDTO) : this(
        cocktailIngredientDTO.id,
        cocktailIngredientDTO.name,
        BitmapFactory.decodeByteArray(cocktailIngredientDTO.preview, 0, cocktailIngredientDTO.preview.size),
        IngredientType(cocktailIngredientDTO.type),
        cocktailIngredientDTO.amount,
        cocktailIngredientDTO.unit
    )
}