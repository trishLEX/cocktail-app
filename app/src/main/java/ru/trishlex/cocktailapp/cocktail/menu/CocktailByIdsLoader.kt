package ru.trishlex.cocktailapp.cocktail.menu

import android.content.Context
import org.openapitools.client.api.CocktailApi
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.cocktail.CocktailItem
import ru.trishlex.cocktailapp.loader.SafeAsyncTaskLoader

class CocktailByIdsLoader(
    context: Context,
    private val ids: List<Int>,
    private val start: Int?,
    private val cocktailApi: CocktailApi = CocktailApi(),
) : SafeAsyncTaskLoader<List<CocktailItem>>(context) {

    companion object {
        val ID = LoaderType.COCKTAILS_BY_IDS_LOADER.id
        private const val START = 0
        const val LIMIT = 10
    }

    override fun load(): List<CocktailItem> {
        if (ids.isEmpty()) {
            return emptyList()
        }

        return cocktailApi.getCocktailsByIds(ids, start ?: START, LIMIT)
            .map { CocktailItem(it, true) }
    }
}