package ru.trishlex.cocktailapp.ingredient.picker

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.trishlex.cocktailapp.ingredient.model.IngredientType
import ru.trishlex.cocktailapp.ingredient.model.IngredientTypeEnum

class IngredientTabViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val ingredientPickerService: IngredientPickerService,
    ingredientTypes: List<PickerIngredient>,
) : FragmentStateAdapter(fragmentActivity) {

    private val ingredientByTabs = ingredientTypes.groupBy {
        getIngredientTab(it.type)
    }

    private val fragmentTabs = ingredientByTabs.entries.associate {
        it.key to IngredientPickerFragment(ingredientPickerService, it.value)
    }

    companion object {
        private fun getIngredientTab(ingredientType: IngredientType): Int {
            return when(ingredientType.type) {
                IngredientTypeEnum.GIN -> 0
                IngredientTypeEnum.VODKA -> 0
                IngredientTypeEnum.TEQUILA -> 0
                IngredientTypeEnum.RUM -> 0
                IngredientTypeEnum.WHISKEY -> 0
                IngredientTypeEnum.BOURBON -> 0
                IngredientTypeEnum.ABSINTHE -> 0
                IngredientTypeEnum.COGNAC -> 0
                IngredientTypeEnum.PORT_WINE -> 0
                IngredientTypeEnum.SHERRY -> 0
                IngredientTypeEnum.APERITIF -> 2
                IngredientTypeEnum.LIQUOR -> 1
                IngredientTypeEnum.VERMOUTH -> 2
                IngredientTypeEnum.BITTER -> 3
                IngredientTypeEnum.WINE -> 2
                IngredientTypeEnum.BEER -> 2
                IngredientTypeEnum.SYRUP -> 4
                IngredientTypeEnum.NON_ALCOHOLIC -> 5
                IngredientTypeEnum.HONEY -> 6
                IngredientTypeEnum.DESSERT -> 6
                IngredientTypeEnum.ADDITIVE -> 6
                IngredientTypeEnum.GREENS -> 6
                IngredientTypeEnum.MISC -> 6
                IngredientTypeEnum.JAM -> 6
                IngredientTypeEnum.JUICE -> 6
                IngredientTypeEnum.SPICE -> 6
                IngredientTypeEnum.DRIED_FRUIT -> 6
                IngredientTypeEnum.DECORATION -> 6
                IngredientTypeEnum.FRUIT -> 6
                IngredientTypeEnum.VEGETABLE -> 6
                IngredientTypeEnum.TEA -> 6
                IngredientTypeEnum.NUT -> 6
                IngredientTypeEnum.BERRY -> 6
                IngredientTypeEnum.ICE -> 6
                null -> -1
            }
        }
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("debugLog", "position: $position")
        return fragmentTabs[position]!!
    }

    override fun getItemCount(): Int {
        return IngredientPickerActivity.TAB_NAMES.size
    }
}