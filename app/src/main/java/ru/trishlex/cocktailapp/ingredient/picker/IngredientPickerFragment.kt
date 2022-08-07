package ru.trishlex.cocktailapp.ingredient.picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.ingredient.model.IngredientType

class IngredientPickerFragment(
    private val ingredientPickerService: IngredientPickerService,
    private val tabIngredients: List<PickerIngredient>
) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ingredient_picker, container, false)

        val layout = view.findViewById<LinearLayout>(R.id.ingredientsPickerLayout)

        tabIngredients
            .groupBy { it.type }
            .forEach { (type, ingredients) -> inflateGroup(layout, type, ingredients) }

        return view
    }

    private fun inflateGroup(
        linearLayout: LinearLayout,
        ingredientType: IngredientType,
        ingredients: List<PickerIngredient>
    ) {
        val inflater = LayoutInflater.from(linearLayout.context)
        val ingredientGroupView = inflater.inflate(R.layout.ingredient_group_picker, null)

        val groupNameView = ingredientGroupView.findViewById<TextView>(R.id.ingredientGroupName)
        groupNameView.text = ingredientType.name

        val ingredientsTable = ingredientGroupView.findViewById<TableLayout>(R.id.ingredientsTable)
        var currentRow: TableRow? = null
        for ((i, ingredient) in ingredients.withIndex()) {
            if (i % 2 == 0) {
                currentRow = inflater.inflate(R.layout.ingredient_picker_row, null) as TableRow
                val checkBox = currentRow.findViewById<CheckBox>(R.id.leftCheckbox)
                checkBox.isChecked = ingredient.isPicked
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    ingredient.isPicked = isChecked
                    if (ingredient.isPicked) {
                        ingredientPickerService.add(ingredient)
                    } else {
                        ingredientPickerService.remove(ingredient)
                    }
                }

                val textView = currentRow.findViewById<TextView>(R.id.leftIngredientName)
                textView.text = ingredient.name
                ingredientsTable.addView(currentRow)
            } else {
                currentRow!!.findViewById<RelativeLayout>(R.id.rightRow).visibility = View.VISIBLE
                val checkBox = currentRow.findViewById<CheckBox>(R.id.rightCheckbox)
                checkBox.isChecked = ingredient.isPicked
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    ingredient.isPicked = isChecked
                    if (ingredient.isPicked) {
                        ingredientPickerService.add(ingredient)
                    } else {
                        ingredientPickerService.remove(ingredient)
                    }
                }

                val textView = currentRow.findViewById<TextView>(R.id.rightIngredientName)
                textView.text = ingredient.name
            }
        }
        linearLayout.addView(ingredientGroupView)
    }
}