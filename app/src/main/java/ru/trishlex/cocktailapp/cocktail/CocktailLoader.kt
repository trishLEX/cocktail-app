package ru.trishlex.cocktailapp.cocktail

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import org.openapitools.client.api.CocktailApi

class CocktailLoader(
    context: Context,
    val cocktailId: Int,
    private val cocktailApi: CocktailApi
) : AsyncTaskLoader<Cocktail>(context) {
    constructor(context: Context, cocktailId: Int) : this(context, cocktailId, CocktailApi())

    companion object {
        const val ID = 2 //TODO make enum
    }

    override fun onStartLoading() {
        forceLoad()
    }

    override fun loadInBackground(): Cocktail {
        Log.d("debugLog", "CocktailLoader: start loading: $cocktailId")

        val cocktail = cocktailApi.getCocktail(cocktailId)

        return Cocktail(cocktail)
    }
}