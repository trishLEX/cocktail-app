package ru.trishlex.cocktailapp.ingredient.menu

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import org.openapitools.client.api.IngredientApi
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.ingredient.IngredientItem

class IngredientByIdsLoader(
    context: Context,
    private val ids: List<Int>,
    private val ingredientApi: IngredientApi = IngredientApi()
) : AsyncTaskLoader<List<IngredientItem>>(context) {

    companion object {
        val ID = LoaderType.INGREDIENTS_BY_IDS_LOADER.id
    }

    override fun onStartLoading() {
        forceLoad()
    }

    override fun loadInBackground(): List<IngredientItem> {
        if (ids.isEmpty()) {
            return emptyList()
        }

        return ingredientApi.getIngredientsByIds(ids).map { IngredientItem(it, true) }
    }
}