package ru.trishlex.cocktailapp.ingredient.loader

import android.content.Context
import android.util.Log
import org.openapitools.client.api.IngredientApi
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService
import ru.trishlex.cocktailapp.ingredient.model.IngredientItem
import ru.trishlex.cocktailapp.loader.AsyncResult
import ru.trishlex.cocktailapp.loader.SafeAsyncTaskLoader

class IngredientsLoader(
    context: Context,
    private val name: String,
    private val selectedIngredientsService: SelectedIngredientsService,
    private val ingredientApi: IngredientApi = IngredientApi()
): SafeAsyncTaskLoader<List<IngredientItem>>(context) {

    companion object {
        val ID = LoaderType.INGREDIENTS_LOADER.id
        const val LIMIT = 1000
    }

    override fun loadInBackground(): AsyncResult<List<IngredientItem>> {
        Log.d("debugLog", "IngredientsLoader: start loading: $name")
        if (name.isEmpty()) {
            return AsyncResult.of(emptyList())
        }

        return try {
            res = ingredientApi.getIngredients(name, 0, LIMIT)
                .map { IngredientItem(it) }
            res!!.forEach { it.isSelected = selectedIngredientsService.isSelected(it) }
            AsyncResult.of(res!!)
        } catch (ex: Exception) {
            AsyncResult.of(ex)
        }
    }
}