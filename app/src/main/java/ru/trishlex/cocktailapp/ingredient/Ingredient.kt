package ru.trishlex.cocktailapp.ingredient

import android.graphics.Bitmap
import ru.trishlex.cocktailapp.cocktail.CocktailItem

data class Ingredient(
    val name: String,
    val image: Bitmap,
    val tags: List<String>,
    val description: String?,
    val cocktails: List<CocktailItem>
)