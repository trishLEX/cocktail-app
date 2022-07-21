package ru.trishlex.cocktailapp.ingredient.recycler

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.db.ShopListDao
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService
import ru.trishlex.cocktailapp.ingredient.model.IngredientItem

class IngredientViewHolder(
    view: View,
    private var ingredient: IngredientItem,
    private val selectedIngredientsService: SelectedIngredientsService,
    private val shopListDao: ShopListDao
) : RecyclerView.ViewHolder(view) {

    private var preview: ImageView = view.findViewById(R.id.ingredientItemPreview)
    private var name: TextView = view.findViewById(R.id.ingredientItemName)
    private var checkBox: CheckBox = view.findViewById(R.id.ingredientItemCheck)
    private var toBuyCheckBox: CheckBox = view.findViewById(R.id.ingredientShopCheckBox)

    init {
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            Log.d("debugLog", "IngredientViewHolder: click")
            ingredient.isSelected = isChecked
            if (isChecked) {
                selectedIngredientsService.addItem(ingredient)
            } else {
                selectedIngredientsService.removeItem(ingredient)
            }
        }

        toBuyCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                shopListDao.save(ingredient)
            } else {
                shopListDao.remove(ingredient)
            }
        }
    }

    fun setIngredient(ingredient: IngredientItem) {
        this.ingredient = ingredient
        preview.setImageBitmap(ingredient.preview)
        name.text = ingredient.name
        checkBox.isChecked = ingredient.isSelected
        toBuyCheckBox.isChecked = shopListDao.contains(ingredient)
    }

}