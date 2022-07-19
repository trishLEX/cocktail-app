package ru.trishlex.cocktailapp.cocktail.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.CocktailDTO
import ru.trishlex.cocktailapp.cocktail.CocktailItem
import ru.trishlex.cocktailapp.tool.ToolItem

class Cocktail(
    id: Int,
    name: String,
    val image: Bitmap,
    override val ingredients: List<CocktailIngredientItem>,
    val tools: List<ToolItem>,
    val instructions: List<String>,
    val tags: List<String>,
    val description: String?,
    isSelected: Boolean = false
) : CocktailItem(id, name, image, ingredients, isSelected) {

    constructor(cocktailDTO: CocktailDTO) : this(
        cocktailDTO.id,
        cocktailDTO.name,
        BitmapFactory.decodeByteArray(cocktailDTO.image, 0, cocktailDTO.image.size),
        cocktailDTO.ingredients.map { CocktailIngredientItem(it) },
        cocktailDTO.tools.map { ToolItem(it) },
        cocktailDTO.instructions,
        cocktailDTO.tags,
        cocktailDTO.description
    )
}