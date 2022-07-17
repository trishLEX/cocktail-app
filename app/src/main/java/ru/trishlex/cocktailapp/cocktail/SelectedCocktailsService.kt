package ru.trishlex.cocktailapp.cocktail

import android.content.SharedPreferences
import org.openapitools.client.JsonUtil
import ru.trishlex.cocktailapp.SelectedService

class SelectedCocktailsService private constructor(
    selectedCocktails: HashMap<CocktailItem, Int>,
    sharedPreferences: SharedPreferences
) : SelectedService<CocktailItem>(selectedCocktails, sharedPreferences, "savedCocktails") {

    companion object {
        private var instance: SelectedCocktailsService? = null

        @Synchronized
        fun getInstance(sharedPreferences: SharedPreferences): SelectedCocktailsService {
            if (instance == null) {
                instance = SelectedCocktailsService(HashMap(), sharedPreferences)
                if (sharedPreferences.contains("savedCocktails")) {
                    val selected: Map<String, String> =
                        JsonUtil
                            .getGson()
                            .fromJson(
                                sharedPreferences.getString("savedCocktails", "{}"),
                                Map::class.java
                            ) as Map<String, String>
                    for (ingredient in selected) {
                        instance!!.addItem(
                            CocktailItem(
                                ingredient.key.toInt(),
                                ingredient.value,
                                true
                            )
                        )
                    }
                }
            }
            return instance as SelectedCocktailsService
        }
    }
}