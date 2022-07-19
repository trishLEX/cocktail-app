package ru.trishlex.cocktailapp.ingredient

import android.content.SharedPreferences
import org.openapitools.client.JsonUtil
import ru.trishlex.cocktailapp.SelectedService
import ru.trishlex.cocktailapp.ingredient.model.IngredientItem

class SelectedIngredientsService private constructor(
    selectedIngredients: HashMap<IngredientItem, Int>,
    sharedPreferences: SharedPreferences
) : SelectedService<IngredientItem>(selectedIngredients, sharedPreferences, "savedIngredients") {

    companion object {

        private var instance: SelectedIngredientsService? = null

        @Synchronized
        fun getInstance(sharedPreferences: SharedPreferences): SelectedIngredientsService {
            if (instance == null) {
                instance = SelectedIngredientsService(HashMap(), sharedPreferences)
                if (sharedPreferences.contains("savedIngredients")) {
                    val selected: Map<String, String> =
                        JsonUtil
                            .getGson()
                            .fromJson(
                                sharedPreferences.getString("savedIngredients", "{}"),
                                Map::class.java
                            ) as Map<String, String>
                    for (ingredient in selected) {
                        instance!!.addItem(
                            IngredientItem(
                                ingredient.key.toInt(),
                                ingredient.value,
                                true
                            )
                        )
                    }
                }
            }
            return instance as SelectedIngredientsService
        }
    }
}