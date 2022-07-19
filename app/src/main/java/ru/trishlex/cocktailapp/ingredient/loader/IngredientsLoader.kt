package ru.trishlex.cocktailapp.ingredient.loader

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import org.openapitools.client.api.IngredientApi
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService
import ru.trishlex.cocktailapp.ingredient.model.IngredientItem

class IngredientsLoader(
    context: Context,
    private val name: String,
    private val selectedIngredientsService: SelectedIngredientsService,
    private val ingredientApi: IngredientApi = IngredientApi()
): AsyncTaskLoader<List<IngredientItem>>(context) {

    companion object {
        val ID = LoaderType.INGREDIENTS_LOADER.id
        const val LIMIT = 1000
    }

    private var res: List<IngredientItem>? = null

    override fun onStartLoading() {
        if (res == null) {
            forceLoad()
        }
    }

    override fun loadInBackground(): List<IngredientItem> {
        Log.d("debugLog", "IngredientsLoader: start loading: $name")
        if (name.isEmpty()) {
            return emptyList()
        }

        res = ingredientApi.getIngredients(name, 0, LIMIT)
            .map { IngredientItem(it) }
        res!!.forEach { it.isSelected = selectedIngredientsService.isSelected(it) }
        return res!!
    }
}