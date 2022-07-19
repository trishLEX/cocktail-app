package ru.trishlex.cocktailapp.ingredient.menu

import android.content.Context
import org.openapitools.client.api.IngredientApi
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.ingredient.model.IngredientItem
import ru.trishlex.cocktailapp.loader.AsyncResult
import ru.trishlex.cocktailapp.loader.SafeAsyncTaskLoader

class IngredientByIdsLoader(
    context: Context,
    private val ids: List<Int>,
    private val ingredientApi: IngredientApi = IngredientApi()
) : SafeAsyncTaskLoader<List<IngredientItem>>(context) {

    companion object {
        val ID = LoaderType.INGREDIENTS_BY_IDS_LOADER.id
    }

    override fun loadInBackground(): AsyncResult<List<IngredientItem>> {
        if (ids.isEmpty()) {
            return AsyncResult.of(emptyList())
        }

        return try {
            AsyncResult.of(ingredientApi.getIngredientsByIds(ids).map { IngredientItem(it, true) })
        } catch (ex: Exception) {
            AsyncResult.of(ex)
        }
    }
}