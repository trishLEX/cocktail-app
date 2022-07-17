package ru.trishlex.cocktailapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.trishlex.cocktailapp.cocktail.CocktailFragment
import ru.trishlex.cocktailapp.cocktail.CocktailsListAdapter
import ru.trishlex.cocktailapp.cocktail.SelectedCocktailsService
import ru.trishlex.cocktailapp.ingredient.IngredientFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val selectedCocktailsService: SelectedCocktailsService,
    private val cocktailsListAdapter: CocktailsListAdapter = CocktailsListAdapter(selectedCocktailsService),
    private val cocktailFragment: CocktailFragment = CocktailFragment(cocktailsListAdapter),
    private val ingredientFragment: IngredientFragment = IngredientFragment(cocktailsListAdapter)
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> cocktailFragment
            1 -> ingredientFragment
            else -> throw IllegalStateException("Wrong position: $position")
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    fun onTabSelected(position: Int) {
        if (position == 0) {
            cocktailFragment.updateCocktails()
        }
    }
}