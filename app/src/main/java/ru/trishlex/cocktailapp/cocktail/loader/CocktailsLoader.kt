package ru.trishlex.cocktailapp.cocktail.loader

import android.content.Context
import android.util.Log
import org.openapitools.client.api.CocktailApi
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.cocktail.SelectedCocktailsService
import ru.trishlex.cocktailapp.cocktail.model.CocktailItem
import ru.trishlex.cocktailapp.cocktail.model.PagedCocktailItem
import ru.trishlex.cocktailapp.loader.SafeAsyncTaskLoader
import kotlin.reflect.KClass

class CocktailsLoader(
    context: Context,
    private val args: Args<*>,
    private val selectedCocktailsService: SelectedCocktailsService,
    private val cocktailApi: CocktailApi = CocktailApi(),
): SafeAsyncTaskLoader<PagedCocktailItem>(context) {
    constructor(context: Context, args: Args<*>, selectedCocktailsService: SelectedCocktailsService) : this(
        context,
        args,
        selectedCocktailsService,
        CocktailApi()
    )

    companion object {
        val ID = LoaderType.COCKTAILS_LOADER.id
        private const val START = 0
        const val LIMIT = 10
    }

    override fun load(): PagedCocktailItem {
        Log.d("debugLog", "args: $args")
        val result = when (args.argType) {
            ArgType.BY_NAME -> getByName(args.arg as String, args.start)
            ArgType.BY_INGREDIENTS -> getByIngredients(args.arg as List<Int>, args.start)
        }
        result.cocktails.forEach { it.isSelected = selectedCocktailsService.isSelected(it) }
        return result
    }

    private fun getByName(name: String, start: Int? = null, limit: Int? = null): PagedCocktailItem {
        if (name.isEmpty()) {
            return PagedCocktailItem(false, 0, emptyList())
        }
        val cocktails = cocktailApi.getCocktailsByName(name, start ?: START, limit ?: LIMIT)
        return PagedCocktailItem(
            cocktails.hasNext,
            cocktails.nextStart,
            cocktails.cocktails.map { CocktailItem(it) }
        )
    }

    private fun getByIngredients(ingredientIds: List<Int>, start: Int? = null, limit: Int? = null): PagedCocktailItem {
        if (ingredientIds.isEmpty()) {
            return PagedCocktailItem(false, 0, emptyList())
        }
        val cocktails = cocktailApi.getAllCocktailsByIngredients(ingredientIds, start ?: START, limit ?: LIMIT)
        return PagedCocktailItem(
            cocktails.hasNext,
            cocktails.nextStart,
            cocktails.cocktails.map { CocktailItem(it) }
        )
    }

    data class Args<T: Any>(val argType: ArgType<T>, val arg: T, val start: Int? = null, val limit: Int? = null)

    sealed class ArgType<T: Any>(val datatype: KClass<T>) {
        object BY_NAME : ArgType<String>(String::class)
        object BY_INGREDIENTS : ArgType<List<Int>>(List::class as KClass<List<Int>>)
    }
}