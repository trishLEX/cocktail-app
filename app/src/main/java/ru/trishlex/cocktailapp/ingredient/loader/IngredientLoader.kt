package ru.trishlex.cocktailapp.ingredient.loader

import android.content.Context
import android.graphics.BitmapFactory
import org.openapitools.client.api.CocktailApi
import org.openapitools.client.api.IngredientApi
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.cocktail.CocktailItem
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService
import ru.trishlex.cocktailapp.ingredient.model.Ingredient
import ru.trishlex.cocktailapp.loader.AsyncResult
import ru.trishlex.cocktailapp.loader.SafeAsyncTaskLoader

class IngredientLoader(
    context: Context,
    private val ingredientApi: IngredientApi,
    private val cocktailApi: CocktailApi,
    private val selectedIngredientsService: SelectedIngredientsService,
    private val ingredientId: Int,
    private val start: Int = 0
): SafeAsyncTaskLoader<Ingredient>(context) {

    constructor(
        context: Context,
        selectedIngredientsService: SelectedIngredientsService,
        ingredientId: Int,
        start: Int?
    ) : this(context, IngredientApi(), CocktailApi(), selectedIngredientsService, ingredientId, start ?: 0)

    companion object {
        val ID = LoaderType.INGREDIENT_LOADER.id
        const val LIMIT = 10
    }

    override fun loadInBackground(): AsyncResult<Ingredient> {
        return try {
            val ingredient = ingredientApi.getIngredient(ingredientId)
            val cocktails = cocktailApi.getCocktailsByIngredient(ingredientId, start, LIMIT)
                .map { CocktailItem(it) }

            res = Ingredient(
                ingredient.id,
                ingredient.name,
                BitmapFactory.decodeByteArray(ingredient.image, 0, ingredient.image.size),
                ingredient.tags,
                ingredient.description,
                cocktails
            )
            res!!.isSelected = selectedIngredientsService.isSelected(res!!)
            AsyncResult.of(res!!)
        } catch (ex: Exception) {
            AsyncResult.of(ex)
        }
    }

}