package ru.trishlex.cocktailapp.ingredient.recycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.db.ShopListDao
import ru.trishlex.cocktailapp.ingredient.IngredientActivity
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService
import ru.trishlex.cocktailapp.ingredient.model.IngredientItem

class IngredientsListAdapter(
    private var ingredients: ArrayList<IngredientItem> = ArrayList(),
    private val selectedIngredientsService: SelectedIngredientsService,
    private val shopListDao: ShopListDao
) : RecyclerView.Adapter<IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        return IngredientViewHolder(view, selectedIngredientsService, shopListDao)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity(
                Intent(holder.itemView.context, IngredientActivity::class.java).putExtra("ID", ingredient.id)
            )
        }
        holder.setIngredient(ingredient)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    fun add(ingredientItem: IngredientItem) {
        ingredients.add(ingredientItem)
        notifyItemInserted(ingredients.size - 1)
    }

    fun addAll(ingredientItems: List<IngredientItem>) {
        ingredientItems.forEach { add(it) }
    }

    fun removeAll() {
        ingredients.clear()
        notifyItemRangeRemoved(0, ingredients.size)
    }

    fun getSelectedIngredients(): List<IngredientItem> {
        return ingredients.filter { it.isSelected }
    }
}