package ru.trishlex.cocktailapp.ingredient

import android.content.SharedPreferences
import android.content.res.Resources
import android.util.TypedValue
import org.openapitools.client.JsonUtil
import kotlin.math.roundToInt

class SelectedIngredientsService private constructor(
    private val selectedIngredients: HashMap<IngredientItem, Int>,
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private val textMargin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            5f,
            Resources.getSystem().displayMetrics
        ).roundToInt()

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
                        instance!!.addIngredient(
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

    fun isSelected(ingredientItem: IngredientItem): Boolean {
        return selectedIngredients.containsKey(ingredientItem)
    }

    fun addIngredient(ingredientItem: IngredientItem) {
        if (selectedIngredients.containsKey(ingredientItem)) {
            return
        }
        selectedIngredients[ingredientItem] = selectedIngredients.size
        val editor = sharedPreferences.edit()
        val selected = getSelectedIngredients().associate { it.id to it.name }
        editor.putString("savedIngredients", JsonUtil.getGson().toJson(selected))
        editor.apply()
    }

    fun removeIngredient(ingredientItem: IngredientItem) {
        if (!selectedIngredients.containsKey(ingredientItem)) {
            return
        }
        selectedIngredients.remove(ingredientItem)
        var currentIndex = 0
        for (item in selectedIngredients.keys) {
            selectedIngredients[item] = currentIndex++
        }

        val editor = sharedPreferences.edit()
        val selected = getSelectedIngredients().associate { it.id to it.name }
        editor.putString("savedIngredients", JsonUtil.getGson().toJson(selected))
        editor.apply()
    }

    fun getSelectedIngredientIds(): ArrayList<Int> {
        return ArrayList(selectedIngredients.keys.map { it.id })
    }

    private fun getSelectedIngredients(): List<IngredientItem> {
        return selectedIngredients.keys.toList()
    }
}