package ru.trishlex.cocktailapp.ingredient

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.R

class IngredientItemAdapter(
    var ingredients: List<IngredientItem> = ArrayList(),
    var ingredientsCount: Int = 0,
    private val selectedIngredientsService: SelectedIngredientsService
) : RecyclerView.Adapter<IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val ingredient = ingredients[ingredientsCount++]
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        view.setOnClickListener {
            parent.context.startActivity(Intent(parent.context, IngredientActivity::class.java))
        }
        return IngredientViewHolder(view, ingredient, selectedIngredientsService)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.setIngredient(ingredients[position])
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    fun getSelectedIngredients(): List<IngredientItem> {
        return ingredients.filter { it.isSelected }
    }
}