package ru.trishlex.cocktailapp.cocktail

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import org.openapitools.client.api.CocktailApi
import kotlin.reflect.KClass

class CocktailsLoader(
    context: Context,
    private val args: Args<*>,
    private val cocktailApi: CocktailApi = CocktailApi(),
): AsyncTaskLoader<List<CocktailItemView>>(context) {
    constructor(context: Context, args: Args<*>) : this(context, args, CocktailApi())

    companion object {
        const val ID = 1
        private const val START = 0
        const val LIMIT = 10
    }

    private var res: List<CocktailItemView>? = null

    override fun onStartLoading() {
        if (res == null) {
            forceLoad()
        }
    }

    override fun loadInBackground(): List<CocktailItemView> {
        res = when (args.argType) {
            ArgType.BY_NAME -> getByName(args.arg as String, args.start)
            ArgType.BY_INGREDIENTS -> getByIngredients(args.arg as List<Int>, args.start)
        }
        return res as List<CocktailItemView>
    }

    private fun getByName(name: String, start: Int? = null, limit: Int? = null): List<CocktailItemView> {
        Log.d("debugLog", "CocktailsLoader: start loading: $name")
        if (name.isEmpty()) {
            return emptyList()
        }
        val cocktails = cocktailApi.getCocktails(name, start ?: START, limit ?: LIMIT)
        Log.d("debugLog", "CocktailsLoader: cocktails size: ${cocktails.size}")
        return cocktails.map { CocktailItemView(it) }
    }

    private fun getByIngredients(ingredientIds: List<Int>, start: Int? = null, limit: Int? = null): List<CocktailItemView> {
        if (ingredientIds.isEmpty()) {
            return emptyList()
        }
        return cocktailApi.getCocktailsByIngredients(ingredientIds, start ?: START, limit ?: LIMIT)
            .map { CocktailItemView(it) }
    }

    data class Args<T: Any>(val argType: ArgType<T>, val arg: T, val start: Int? = null, val limit: Int? = null)

    sealed class ArgType<T: Any>(val datatype: KClass<T>) {
        object BY_NAME : ArgType<String>(String::class)
        object BY_INGREDIENTS : ArgType<List<Int>>(List::class as KClass<List<Int>>)
    }
}