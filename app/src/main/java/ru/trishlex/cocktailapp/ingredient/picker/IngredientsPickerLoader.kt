package ru.trishlex.cocktailapp.ingredient.picker

import android.content.Context
import org.openapitools.client.api.IngredientApi
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.loader.SafeAsyncTaskLoader

class IngredientsPickerLoader(
    context: Context,
    private val ingredientApi: IngredientApi = IngredientApi()
) : SafeAsyncTaskLoader<List<PickerIngredient>>(context) {

    companion object {
        val ID = LoaderType.INGREDIENTS_PICKER_LOADER.id
    }

    override fun load(): List<PickerIngredient> {
        return ingredientApi.ingredientTypes.map { PickerIngredient(it) }
    }
}