package ru.trishlex.cocktailapp.cocktail

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.ingredient.IngredientActivity
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService
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

    private lateinit var progressBar: ProgressBar
    private lateinit var selectedIngredientsService: SelectedIngredientsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail)

        selectedIngredientsService = SelectedIngredientsService.getInstance(getSharedPreferences("preferences", MODE_PRIVATE))

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

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
            SelectedIngredientsService.getInstance(getSharedPreferences("preferences", MODE_PRIVATE)),
            args!!["id"] as Int
        )
    }

    override fun onLoadFinished(loader: Loader<Cocktail>, data: Cocktail?) {
        Log.d("debugLog", "CocktailActivity: loading is finished in fragment")
        val descriptionHeader = findViewById<TextView>(R.id.descriptionHeader)
        if (loader.id == CocktailLoader.ID) {
            val cocktail = data!!

            val cocktailNameView = findViewById<TextView>(R.id.cocktailName)
            cocktailNameView.text = cocktail.name

            val cocktailDescriptionView = findViewById<TextView>(R.id.cocktailDescription)
            if (cocktail.description != null) {
                cocktailDescriptionView.text = cocktail.description
            } else {
                descriptionHeader.visibility = View.GONE
                cocktailDescriptionView.visibility = View.GONE
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

                val checkBox = ingredientView.findViewById<CheckBox>(R.id.cocktailIngredientCheck)
                checkBox.isChecked = cocktailIngredient.isSelected

                checkBox.setOnCheckedChangeListener {_ , isChecked ->
                    cocktailIngredient.isSelected = isChecked
                    if (isChecked) {
                        selectedIngredientsService.addItem(cocktailIngredient)
                    } else {
                        selectedIngredientsService.removeItem(cocktailIngredient)
                    }
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

                toolView.findViewById<CheckBox>(R.id.cocktailIngredientCheck).visibility = View.GONE

                toolsView.addView(cardView)
            }

            findViewById<TextView>(R.id.cocktailReceiptTextView).visibility = View.VISIBLE
            findViewById<CardView>(R.id.cocktailInstructionsCardView).visibility = View.VISIBLE
            findViewById<TextView>(R.id.ingredientsTextView).visibility = View.VISIBLE
            findViewById<TextView>(R.id.toolsTextView).visibility = View.VISIBLE
            findViewById<CardView>(R.id.cocktailDescriptionCardView).visibility = View.VISIBLE
            descriptionHeader.visibility = View.VISIBLE

            progressBar.visibility = View.GONE
        }
    }

    override fun onLoaderReset(loader: Loader<Cocktail>) {
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("ID", intent.getIntExtra("ID", -1))
    }
}