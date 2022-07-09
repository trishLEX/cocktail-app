package ru.trishlex.cocktailapp.cocktail

import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.ingredient.IngredientActivity
import kotlin.math.roundToInt

class CocktailActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cocktail> {

    companion object {
        private val tagSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            10f,
            Resources.getSystem().displayMetrics
        ).roundToInt()

        private val tagMargin = TypedValue.applyDimension(
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

    private lateinit var loadDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail)

        loadDialog = ProgressDialog(this)
        loadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        loadDialog.setMessage("Loading. Please wait...")
        loadDialog.setIndeterminate(true)
        loadDialog.setCanceledOnTouchOutside(false)
        loadDialog.show()

        val loaderManager = LoaderManager.getInstance(this)
        val cocktailLoader = loaderManager.getLoader<Cocktail>(CocktailLoader.ID)
        val arg = Bundle()
        arg.putInt("id", intent.getIntExtra("ID", savedInstanceState?.getInt("ID") ?: -1))
        if (cocktailLoader == null) {
            loaderManager.initLoader(CocktailLoader.ID, arg, this)
            Log.d("debugLog", "CocktailFragment: init loader")
        } else {
            loaderManager.restartLoader(CocktailsLoader.ID, arg, this)
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cocktail> {
        Log.d("debugLog", "CocktailActivity: loading is created: $args")
        return CocktailLoader(
            this,
            args!!["id"] as Int
        )
    }

    override fun onLoadFinished(loader: Loader<Cocktail>, data: Cocktail?) {
        Log.d("debugLog", "CocktailActivity: loading is finished in fragment")
        if (loader.id == CocktailLoader.ID) {
            val cocktail = data!!
            loadDialog.dismiss()

            val cocktailNameView = findViewById<TextView>(R.id.cocktailName)
            cocktailNameView.text = cocktail.name

            val cocktailDescriptionView = findViewById<TextView>(R.id.cocktailDescription)
            if (cocktail.description != null) {
                cocktailDescriptionView.text = cocktail.description
            } else {
                val descriptionHeader = findViewById<TextView>(R.id.descriptionHeader)
                descriptionHeader.visibility = View.INVISIBLE
                cocktailDescriptionView.visibility = View.INVISIBLE
            }

            val imageView = findViewById<ImageView>(R.id.cocktailImage)
            imageView.setImageBitmap(cocktail.image)

            val tagsLayout = findViewById<LinearLayout>(R.id.cocktailTagsLayout)
            tagsLayout.removeAllViewsInLayout()
            for (tag in cocktail.tags) {
                val cardView = CardView(this)
                cardView.radius = cornerRadius
                val cardLayoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                cardLayoutParams.leftMargin = tagMargin
                cardLayoutParams.rightMargin = tagMargin
                cardLayoutParams.topMargin = tagMargin
                cardLayoutParams.bottomMargin = tagMargin
                cardView.layoutParams = cardLayoutParams
                cardView.elevation = tagMargin.toFloat()

                val tagView = TextView(this)
                val tagLayout = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                tagLayout.leftMargin = tagMargin
                tagLayout.rightMargin = tagMargin
                tagView.text = tag
                tagView.layoutParams = tagLayout

                cardView.addView(tagView)
                tagsLayout.addView(cardView)
            }

            val instructions = findViewById<TextView>(R.id.cocktailInstructions)
            instructions.text = cocktail.instructions.joinToString("\n")

            val ingredientsView = findViewById<LinearLayout>(R.id.cocktailIngredients)
            val toolsView = findViewById<LinearLayout>(R.id.cocktailTools)

            for (cocktailIngredient in cocktail.ingredients) {

                val cardView = CardView(this)
                cardView.radius = cornerRadius
                val cardLayoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                cardLayoutParams.leftMargin = tagMargin
                cardLayoutParams.rightMargin = tagMargin
                cardLayoutParams.topMargin = tagMargin
                cardLayoutParams.bottomMargin = tagMargin
                cardView.layoutParams = cardLayoutParams
                cardView.elevation = tagMargin.toFloat()
                val ingredientView = LayoutInflater.from(ingredientsView.context).inflate(R.layout.cocktail_ingredient, ingredientsView, false)

                val ingredientPreview = ingredientView.findViewById<ImageView>(R.id.cocktailIngredientPreview)
                ingredientPreview.setImageBitmap(cocktailIngredient.preview)

                val ingredientNameView = ingredientView.findViewById<TextView>(R.id.cocktailIngredientName)
                ingredientNameView.text = cocktailIngredient.name

                val ingredientAmountView = ingredientView.findViewById<TextView>(R.id.cocktailIngredientAmount)
                ingredientAmountView.text = cocktailIngredient.amount.toString()

                val ingredientUnitView = ingredientView.findViewById<TextView>(R.id.cocktailIngredientUnit)
                ingredientUnitView.text = cocktailIngredient.unit
                cardView.addView(ingredientView)

                cardView.setOnClickListener {
                    startActivity(Intent(this, IngredientActivity::class.java)
                        .putExtra("ID", cocktailIngredient.id)
                    )
                }

                ingredientsView.addView(cardView)
            }

            for (cocktailIngredient in cocktail.tools) {

                val cardView = CardView(this)
                cardView.radius = cornerRadius
                val cardLayoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                cardLayoutParams.leftMargin = tagMargin
                cardLayoutParams.rightMargin = tagMargin
                cardLayoutParams.topMargin = tagMargin
                cardLayoutParams.bottomMargin = tagMargin
                cardView.layoutParams = cardLayoutParams
                cardView.elevation = tagMargin.toFloat()
                val toolView = LayoutInflater.from(toolsView.context).inflate(R.layout.cocktail_ingredient, toolsView, false)
                cardView.addView(toolView)
                val ingredientPreview = toolView.findViewById<ImageView>(R.id.cocktailIngredientPreview)
                ingredientPreview.setImageBitmap(cocktailIngredient.preview)

                val ingredientNameView = toolView.findViewById<TextView>(R.id.cocktailIngredientName)
                ingredientNameView.text = cocktailIngredient.name

                toolsView.addView(cardView)
            }
        }
    }

    override fun onLoaderReset(loader: Loader<Cocktail>) {
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("ID", intent.getIntExtra("ID", -1))
    }
}