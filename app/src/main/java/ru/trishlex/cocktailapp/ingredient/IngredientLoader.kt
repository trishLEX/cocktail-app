package ru.trishlex.cocktailapp.ingredient

import android.content.Context
import android.graphics.BitmapFactory
import androidx.loader.content.AsyncTaskLoader
import org.openapitools.client.api.CocktailApi
import org.openapitools.client.api.IngredientApi
import ru.trishlex.cocktailapp.cocktail.CocktailItemView

class IngredientLoader(
    context: Context,
    private val ingredientApi: IngredientApi,
    private val cocktailApi: CocktailApi,
    private val ingredientId: Int
): AsyncTaskLoader<Ingredient>(context) {

    constructor(
        context: Context,
        ingredientId: Int
    ) : this(context, IngredientApi(), CocktailApi(), ingredientId)

    companion object {
        const val ID = 4 //TODO make enum
        private var count = 0
    }

    override fun onStartLoading() {
        forceLoad()
    }

    override fun loadInBackground(): Ingredient {
        val ingredient = ingredientApi.getIngredient(ingredientId)
        val cocktails = cocktailApi.getCocktailsByIngredient(ingredientId, 0, 100)
            .map { CocktailItemView(it) }

        return Ingredient(
            ingredient.name,
            BitmapFactory.decodeByteArray(ingredient.image, 0, ingredient.image.size),
            ingredient.tags,
            ingredient.description,
            cocktails
        )
    }

}