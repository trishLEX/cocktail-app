package ru.trishlex.cocktailapp.ingredient

import android.graphics.Bitmap
import ru.trishlex.cocktailapp.cocktail.CocktailItemView

data class Ingredient(
    val name: String,
    val image: Bitmap,
    val tags: List<String>,
    val description: String?,
    val cocktails: List<CocktailItemView>
)