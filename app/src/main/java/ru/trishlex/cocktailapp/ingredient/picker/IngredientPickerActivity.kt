package ru.trishlex.cocktailapp.ingredient.picker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.trishlex.cocktailapp.R

class IngredientPickerActivity : AppCompatActivity() {

    companion object {
        val TAB_NAMES = mapOf(
            0 to "Крепкие",
            1 to "Ликеры",
            2 to "Легкие",
            3 to "Биттеры",
            4 to "Сиропы",
            5 to "Безалкогольное",
            6 to "Прочее"
        )
    }

    private var ingredientPickerService = IngredientPickerService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_picker)

        val tabLayout = findViewById<TabLayout>(R.id.ingredientTabs)
        val viewPager = findViewById<ViewPager2>(R.id.ingredientTabsPager)

        val ingredientTypes = (intent.extras!!.getSerializable("ingredientTypes") as Array<PickerIngredient>).toList()
        ingredientTypes.forEach {
            if (ingredientPickerService.contains(it)) {
                it.isPicked = true
            }
        }

        viewPager.adapter = IngredientTabViewPagerAdapter(
            this,
            ingredientPickerService,
            ingredientTypes
        )

        TabLayoutMediator(
            tabLayout,
            viewPager
        ) { tab, position ->
            tab.text = TAB_NAMES[position]
        }.attach()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val returnIntent = Intent()
            returnIntent.putExtra("picked", ingredientPickerService.getPicked().toTypedArray())
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}