package ru.trishlex.cocktailapp.ingredient.shoplist

import android.content.Context
import org.openapitools.client.api.IngredientApi
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.db.ShopListDao
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService
import ru.trishlex.cocktailapp.ingredient.model.IngredientItem
import ru.trishlex.cocktailapp.loader.SafeAsyncTaskLoader

class IngredientsToBuyLoader(
    context: Context,
    private val selectedIngredientsService: SelectedIngredientsService,
    private val ingredientApi: IngredientApi = IngredientApi(),
    private val shopListDao: ShopListDao = ShopListDao(context),
) : SafeAsyncTaskLoader<List<IngredientItem>>(context) {

    companion object {
        val ID = LoaderType.INGREDIENTS_TO_BUY_LOADER.id
    }

    override fun load(): List<IngredientItem> {
        val ids = shopListDao.get()
        if (ids.isEmpty()) {
            return emptyList()
        }

        val result = ingredientApi.getIngredientsByIds(ids).map { IngredientItem(it) }
        result.forEach { it.isSelected = selectedIngredientsService.isSelected(it) }
        return result
    }
}