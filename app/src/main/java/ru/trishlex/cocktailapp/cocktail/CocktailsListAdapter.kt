package ru.trishlex.cocktailapp.cocktail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.R

class CocktailsListAdapter(
    private val selectedCocktailsService: SelectedCocktailsService,
    var cocktailItems: ArrayList<CocktailItem> = ArrayList(),
    var isLoadingAdded: Boolean = false,
    var isLoading: Boolean = false,
    var isLastPage: Boolean = false,
    var currentId: Int = 0
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val LOADING = 0
        private const val ITEM = 1
    }

    lateinit var type: Type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ITEM -> {
                val view = inflater.inflate(R.layout.cocktail_item, parent, false)
                CocktailViewHolder(view, selectedCocktailsService)
            }
            LOADING -> {
                val view = inflater.inflate(R.layout.item_progress, parent, false)
                CocktailsLoadingViewHolder(view)
            }
            else -> throw UnsupportedOperationException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cocktail = cocktailItems[position]
        when (getItemViewType(position)) {
            ITEM -> {
                val cocktailHolder = holder as CocktailViewHolder
                cocktailHolder.setCocktail(cocktail)
            }
            LOADING -> {
                val loadingViewHolder = holder as CocktailsLoadingViewHolder
                loadingViewHolder.setVisibility(View.VISIBLE)
            }
        }
    }

    override fun getItemCount(): Int {
        return cocktailItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == cocktailItems.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(CocktailItem())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = cocktailItems.size - 1
        if (position >= 0 && position < cocktailItems.size) {
            cocktailItems.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun add(cocktailItem: CocktailItem) {
        cocktailItems.add(cocktailItem)
        notifyItemInserted(cocktailItems.size - 1)
    }

    fun addAll(cocktails: List<CocktailItem>) {
        cocktails.forEach { add(it) }
        currentId = if (cocktails.isEmpty()) 0 else cocktails.last().id
    }

    fun removeAll() {
        val cur = cocktailItems.size
        cocktailItems.clear()
        notifyItemRangeRemoved(0, cur)
        currentId = 0
        isLastPage = false
        isLoading = false
    }

    enum class Type {
        BY_NAME,
        BY_INGREDIENTS
    }
}