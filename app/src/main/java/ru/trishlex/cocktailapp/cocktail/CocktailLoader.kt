package ru.trishlex.cocktailapp.cocktail

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import org.openapitools.client.api.CocktailApi
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService

class CocktailLoader(
    context: Context,
    private val cocktailId: Int,
    private val selectedIngredientsService: SelectedIngredientsService,
    private val cocktailApi: CocktailApi
) : AsyncTaskLoader<Cocktail>(context) {
    constructor(
        context: Context,
        selectedIngredientsService: SelectedIngredientsService,
        cocktailId: Int
    ) : this(context, cocktailId, selectedIngredientsService, CocktailApi())

    companion object {
        val ID = LoaderType.COCKTAIL_LOADER.id
    }

    override fun onStartLoading() {
        forceLoad()
    }

    override fun loadInBackground(): Cocktail {
        Log.d("debugLog", "CocktailLoader: start loading: $cocktailId")

        val cocktail = cocktailApi.getCocktail(cocktailId)

        val c = Cocktail(cocktail)
        c.ingredients.forEach { it.isSelected = selectedIngredientsService.isSelected(it) }
        return c
    }
}