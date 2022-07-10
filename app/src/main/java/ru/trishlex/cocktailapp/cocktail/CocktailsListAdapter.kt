package ru.trishlex.cocktailapp.cocktail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.R

class CocktailsListAdapter(
    var cocktailItemViews: List<CocktailItemView> = ArrayList(),
    var cocktailsCount: Int = 0
) : RecyclerView.Adapter<CocktailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val cocktail = cocktailItemViews[cocktailsCount++]
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cocktail_item, parent, false)

        return CocktailViewHolder(
            view,
            cocktail
        )
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.setCocktail(cocktailItemViews[position])
    }

    override fun getItemCount(): Int {
        return cocktailItemViews.size
    }
}