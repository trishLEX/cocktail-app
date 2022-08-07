package ru.trishlex.cocktailapp.ingredient.model

import android.graphics.Bitmap
import ru.trishlex.cocktailapp.cocktail.model.CocktailItem

class Ingredient(
    id: Int,
    name: String,
    ingredientType: IngredientType,
    val image: Bitmap,
    val tags: List<String>,
    val description: String?,
    val cocktails: List<CocktailItem>,
    isSelected: Boolean = false
) : IngredientItem(id, name, image, ingredientType, isSelected)