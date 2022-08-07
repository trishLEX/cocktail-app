package ru.trishlex.cocktailapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.trishlex.cocktailapp.cocktail.CocktailFragment
import ru.trishlex.cocktailapp.ingredient.IngredientFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val cocktailFragment: CocktailFragment = CocktailFragment(),
    private val ingredientFragment: IngredientFragment = IngredientFragment()
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
}