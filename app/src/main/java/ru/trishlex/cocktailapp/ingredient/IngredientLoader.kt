package ru.trishlex.cocktailapp.ingredient

import android.content.Context
import android.graphics.BitmapFactory
import androidx.loader.content.AsyncTaskLoader
import org.openapitools.client.api.CocktailApi
import org.openapitools.client.api.IngredientApi
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.cocktail.CocktailItem

class IngredientLoader(
    context: Context,
    private val ingredientApi: IngredientApi,
    private val cocktailApi: CocktailApi,
    private val selectedIngredientsService: SelectedIngredientsService,
    private val ingredientId: Int,
    private val start: Int = 0
): AsyncTaskLoader<Ingredient>(context) {

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

    private var res: Ingredient? = null

    override fun onStartLoading() {
        if (res == null) {
            forceLoad()
        }
    }

    override fun loadInBackground(): Ingredient {
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
        return res!!
    }

}