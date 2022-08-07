package ru.trishlex.cocktailapp.cocktail.construct

import android.content.Context
import org.openapitools.client.api.CocktailApi
import org.openapitools.client.model.SaveCocktailRequestDTO
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.loader.SafeAsyncTaskLoader

class CocktailSaveLoader(
    context: Context,
    private val saveCocktailRequestDTO: SaveCocktailRequestDTO,
    private val cocktailApi: CocktailApi = CocktailApi()
) : SafeAsyncTaskLoader<Boolean>(context) {

    companion object {
        val ID = LoaderType.SAVE_COCKTAIL_LOADER.id
    }

    override fun load(): Boolean {
        cocktailApi.saveCocktail(saveCocktailRequestDTO)
        return true
    }
}