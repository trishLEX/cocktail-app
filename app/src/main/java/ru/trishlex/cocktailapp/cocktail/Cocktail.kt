package ru.trishlex.cocktailapp.cocktail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.CocktailDTO
import ru.trishlex.cocktailapp.tool.ToolItem

data class Cocktail(
    val name: String,
    val image: Bitmap,
    val ingredients: List<CocktailIngredientItem>,
    val tools: List<ToolItem>,
    val instructions: List<String>,
    val tags: List<String>,
    val description: String?
) {

    constructor(cocktailDTO: CocktailDTO) : this(
        cocktailDTO.name,
        BitmapFactory.decodeByteArray(cocktailDTO.image, 0, cocktailDTO.image.size),
        cocktailDTO.ingredients.map { CocktailIngredientItem(it) },
        cocktailDTO.tools.map { ToolItem(it) },
        cocktailDTO.instructions,
        cocktailDTO.tags,
        cocktailDTO.description
    )
}