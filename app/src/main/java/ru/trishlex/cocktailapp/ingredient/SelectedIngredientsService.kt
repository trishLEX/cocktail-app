package ru.trishlex.cocktailapp.ingredient

import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.size
import kotlin.math.roundToInt

class SelectedIngredientsService(
    private val selectedIngredients: HashMap<IngredientItem, Int>,
    private val selectedLayout: LinearLayout
) {

    companion object {
        private val textMargin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            5f,
            Resources.getSystem().displayMetrics
        ).roundToInt()
    }

    fun isSelected(ingredientItem: IngredientItem): Boolean {
        return selectedIngredients.containsKey(ingredientItem)
    }

    fun addIngredient(ingredientItem: IngredientItem) {
        if (selectedIngredients.containsKey(ingredientItem)) {
            return
        }
        selectedIngredients[ingredientItem] = selectedIngredients.size
        val textView = TextView(selectedLayout.context)
        val layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.leftMargin = textMargin
        textView.layoutParams = layoutParams
        textView.text = ingredientItem.name
        selectedLayout.addView(textView)
        selectedLayout.requestLayout()
        Log.d("debugLog", "SelectedIngredientsService: layoutSize ${selectedLayout.size}, $selectedIngredients")
    }

    fun removeIngredient(ingredientItem: IngredientItem) {
        if (!selectedIngredients.containsKey(ingredientItem)) {
            return
        }
        selectedLayout.removeViewAt(selectedIngredients[ingredientItem]!!)
        selectedIngredients.remove(ingredientItem)
        var currentIndex = 0
        for (item in selectedIngredients.keys) {
            selectedIngredients[item] = currentIndex++
        }
    }

    fun getSelectedIngredientIds(): ArrayList<Int> {
        return ArrayList(selectedIngredients.keys.map { it.id })
    }
}