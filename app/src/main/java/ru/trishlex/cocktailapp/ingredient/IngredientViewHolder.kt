package ru.trishlex.cocktailapp.ingredient

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.R

class IngredientViewHolder(
    view: View,
    private var ingredient: IngredientItem,
    private var selectedIngredientsService: SelectedIngredientsService
) : RecyclerView.ViewHolder(view) {

    private var preview: ImageView = view.findViewById(R.id.ingredientItemPreview)
    private var name: TextView = view.findViewById(R.id.ingredientItemName)
    private var checkBox: CheckBox = view.findViewById(R.id.ingredientItemCheck)

    init {
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            Log.d("debugLog", "IngredientViewHolder: click")
            ingredient.isSelected = isChecked
            if (isChecked) {
                selectedIngredientsService.addIngredient(ingredient)
                Toast.makeText(view.context, "Ингредиенты сохранены", Toast.LENGTH_SHORT).show()
            } else {
                selectedIngredientsService.removeIngredient(ingredient)
                Toast.makeText(view.context, "Ингредиенты сохранены", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setIngredient(ingredient: IngredientItem) {
        this.ingredient = ingredient
        preview.setImageBitmap(ingredient.preview)
        name.text = ingredient.name
        checkBox.isChecked = ingredient.isSelected
    }

}