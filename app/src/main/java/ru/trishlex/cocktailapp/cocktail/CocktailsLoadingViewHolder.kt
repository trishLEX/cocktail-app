package ru.trishlex.cocktailapp.cocktail

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.R

class CocktailsLoadingViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    private val progressBar: ProgressBar = view.findViewById(R.id.loadMoreProgress)

    fun setVisibility(visibility: Int) {
        progressBar.visibility = visibility
    }
}