package ru.trishlex.cocktailapp.ingredient

import android.content.Context
import android.graphics.BitmapFactory
import androidx.loader.content.AsyncTaskLoader
import org.openapitools.client.api.CocktailApi
import org.openapitools.client.api.IngredientApi
import ru.trishlex.cocktailapp.cocktail.CocktailItem

class IngredientLoader(
    context: Context,
    private val ingredientApi: IngredientApi,
    private val cocktailApi: CocktailApi,
    private val ingredientId: Int,
    private val start: Int = 0
): AsyncTaskLoader<Ingredient>(context) {

    constructor(
        context: Context,
        ingredientId: Int,
        start: Int?
    ) : this(context, IngredientApi(), CocktailApi(), ingredientId, start ?: 0)

    companion object {
        const val ID = 4 //TODO make enum
        const val LIMIT = 10
    }

    override fun onStartLoading() {
        forceLoad()
    }

    override fun loadInBackground(): Ingredient {
        val ingredient = ingredientApi.getIngredient(ingredientId)
        val cocktails = cocktailApi.getCocktailsByIngredient(ingredientId, start, LIMIT)
            .map { CocktailItem(it) }

        return Ingredient(
            ingredient.name,
            BitmapFactory.decodeByteArray(ingredient.image, 0, ingredient.image.size),
            ingredient.tags,
            ingredient.description,
            cocktails
        )
    }

}