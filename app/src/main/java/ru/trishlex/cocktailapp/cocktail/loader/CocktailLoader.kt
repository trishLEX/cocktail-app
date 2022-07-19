package ru.trishlex.cocktailapp.cocktail.loader

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import org.openapitools.client.api.CocktailApi
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.cocktail.SelectedCocktailsService
import ru.trishlex.cocktailapp.cocktail.model.Cocktail
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService

class CocktailLoader(
    context: Context,
    private val cocktailId: Int,
    private val selectedIngredientsService: SelectedIngredientsService,
    private val selectedCocktailsService: SelectedCocktailsService,
    private val cocktailApi: CocktailApi
) : AsyncTaskLoader<Cocktail>(context) {
    constructor(
        context: Context,
        selectedIngredientsService: SelectedIngredientsService,
        selectedCocktailsService: SelectedCocktailsService,
        cocktailId: Int
    ) : this(context, cocktailId, selectedIngredientsService, selectedCocktailsService, CocktailApi())

    companion object {
        val ID = LoaderType.COCKTAIL_LOADER.id
    }

    private var res: Cocktail? = null

    override fun onStartLoading() {
        if (res == null) {
            forceLoad()
        }
    }

    override fun loadInBackground(): Cocktail {
        Log.d("debugLog", "CocktailLoader: start loading: $cocktailId")

        val cocktail = cocktailApi.getCocktail(cocktailId)

        res = Cocktail(cocktail)
        res!!.ingredients.forEach { it.isSelected = selectedIngredientsService.isSelected(it) }
        res!!.isSelected = selectedCocktailsService.isSelected(res!!)
        return res!!
    }
}