package ru.trishlex.cocktailapp.ingredient

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import org.openapitools.client.api.IngredientApi

class IngredientsLoader(
    context: Context,
    private val name: String,
    private val selectedIngredientsService: SelectedIngredientsService,
    private val ingredientApi: IngredientApi = IngredientApi()
): AsyncTaskLoader<List<IngredientItem>>(context) {

    companion object {
        const val ID = 3
        const val LIMIT = 200
        private var count = 0
    }

    override fun onStartLoading() {
        forceLoad()
    }

    override fun loadInBackground(): List<IngredientItem> {
        Log.d("debugLog", "IngredientsLoader: start loading: $name")
        if (name.isEmpty()) {
            return emptyList()
        }

        val res = ingredientApi.getIngredients(name, 0, LIMIT)
            .map { IngredientItem(it) }
        res.forEach { it.isSelected = selectedIngredientsService.isSelected(it) }
        return res
    }
}