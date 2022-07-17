package ru.trishlex.cocktailapp.cocktail.menu

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import org.openapitools.client.api.CocktailApi
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.cocktail.CocktailItem

class CocktailByIdsLoader(
    context: Context,
    private val ids: List<Int>,
    private val start: Int?,
    private val cocktailApi: CocktailApi = CocktailApi(),
) : AsyncTaskLoader<List<CocktailItem>>(context) {

    companion object {
        val ID = LoaderType.COCKTAILS_BY_IDS_LOADER.id
        private const val START = 0
        const val LIMIT = 10
    }

    private var res: List<CocktailItem>? = null

    override fun onStartLoading() {
        if (res == null) {
            forceLoad()
        }
    }

    override fun loadInBackground(): List<CocktailItem> {
        if (ids.isEmpty()) {
            return emptyList()
        }

        res = cocktailApi.getCocktailsByIds(ids, start ?: START, LIMIT).map { CocktailItem(it, true) }
        return res!!
    }
}