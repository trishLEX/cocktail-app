package ru.trishlex.cocktailapp.ingredient

import android.app.ProgressDialog
import android.content.res.Resources
import android.os.Bundle
import android.os.PersistableBundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.cocktail.CocktailsListAdapter
import kotlin.math.roundToInt

class IngredientActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Ingredient> {

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

    private lateinit var cocktails: RecyclerView
    private var cocktailsListAdapter: CocktailsListAdapter = CocktailsListAdapter()
    private lateinit var loadDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient)

        loadDialog = ProgressDialog(this)
        loadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        loadDialog.setMessage("Loading. Please wait...")
        loadDialog.setIndeterminate(true)
        loadDialog.setCanceledOnTouchOutside(false)
        loadDialog.show()

        val loaderManager = LoaderManager.getInstance(this)
        val ingredientLoader = loaderManager.getLoader<Ingredient>(IngredientLoader.ID)
        val arg = Bundle()
        arg.putInt("id", intent.getIntExtra("ID", savedInstanceState?.getInt("ID") ?: -1))
        if (ingredientLoader == null) {
            loaderManager.initLoader(IngredientLoader.ID, arg, this)
        } else {
            loaderManager.restartLoader(IngredientLoader.ID, arg, this)
        }

        cocktails = findViewById(R.id.cocktailsRecyclerView)
        cocktails.layoutManager = LinearLayoutManager(this)
        cocktails.isNestedScrollingEnabled = true

        cocktails.adapter = cocktailsListAdapter
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Ingredient> {
        return IngredientLoader(this, args!!["id"] as Int)
    }

    override fun onLoadFinished(loader: Loader<Ingredient>, data: Ingredient?) {
        if (loader.id == IngredientLoader.ID) {
            val ingredient = data!!

            val ingredientNameView = findViewById<TextView>(R.id.ingredientName)
            ingredientNameView.text = ingredient.name

            val ingredientDescriptionView = findViewById<TextView>(R.id.ingredientDescription)
            ingredientDescriptionView.text = ingredient.description

            val imageView = findViewById<ImageView>(R.id.ingredientImage)
            imageView.setImageBitmap(ingredient.image)

            val tagsLayout = findViewById<LinearLayout>(R.id.ingredientTagsLayout)
            tagsLayout.removeAllViewsInLayout()
            for (tag in ingredient.tags) {
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

            cocktailsListAdapter.cocktailItemViews = ingredient.cocktailIds
            cocktailsListAdapter.cocktailsCount = 0
            cocktails.adapter = cocktailsListAdapter

            loadDialog.dismiss()
        }
    }

    override fun onLoaderReset(loader: Loader<Ingredient>) {
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("ID", intent.getIntExtra("ID", -1))
    }
}