package ru.trishlex.cocktailapp.cocktail

import android.content.Intent
import android.content.res.Resources
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.ingredient.IngredientActivity
import kotlin.math.roundToInt


class CocktailViewHolder(
    private val view: View,
    cocktailItemView: CocktailItemView
) : RecyclerView.ViewHolder(view) {

    private var imageView: ImageView = view.findViewById(R.id.cocktailItemPreview)
    private var textView: TextView = view.findViewById(R.id.cocktailItemName)
    private var ingredientsLayout: LinearLayout = view.findViewById(R.id.cocktailItemIngredients)
    private var cocktailId: Int = -1

    companion object {
        private val ingredientSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            90f,
            Resources.getSystem().displayMetrics
        ).roundToInt()

        private val ingredientsMargin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            5f,
            Resources.getSystem().displayMetrics
        ).roundToInt()

        private val cornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            10f,
            Resources.getSystem().displayMetrics
        )
    }

    fun setCocktail(cocktailItemView: CocktailItemView) {
        imageView.setImageBitmap(cocktailItemView.image)
        imageView.setOnClickListener {
            view.context.startActivity(
                Intent(view.context, CocktailActivity::class.java).putExtra("ID", cocktailItemView.id)
            )
        }
        textView.text = cocktailItemView.name
        ingredientsLayout.removeAllViewsInLayout()

        for (ingredient in cocktailItemView.ingredients) {
            val imageWithText = LinearLayout(view.context)
            imageWithText.gravity = Gravity.CENTER_HORIZONTAL
            imageWithText.orientation = LinearLayout.VERTICAL
            val imageWithTextLayoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            imageWithTextLayoutParams.rightMargin = ingredientsMargin
            imageWithTextLayoutParams.leftMargin = ingredientsMargin
            imageWithText.layoutParams = imageWithTextLayoutParams

            val cardView = CardView(view.context)
            cardView.radius = cornerRadius
            val cardLayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            cardView.layoutParams = cardLayoutParams
            val ingredientView = ImageView(view.context)
            ingredientView.setImageBitmap(ingredient.preview)
            val layoutParams = ViewGroup.LayoutParams(ingredientSize, ingredientSize)
            ingredientView.layoutParams = layoutParams
            cardView.addView(ingredientView)

            val labelView = TextView(view.context)
            labelView.text = ingredient.name
            labelView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            imageWithText.addView(cardView)
            imageWithText.addView(labelView)

            imageWithText.setOnClickListener {
                view.context.startActivity(Intent(view.context, IngredientActivity::class.java).putExtra("ID", ingredient.id))
            }

            ingredientsLayout.addView(imageWithText)
        }
    }
}