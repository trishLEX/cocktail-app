package ru.trishlex.cocktailapp.ingredient

import android.content.res.Resources
import android.os.Bundle
import android.os.PersistableBundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.widget.NestedScrollView
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.PaginationScrollListener
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.cocktail.CocktailsListAdapter
import kotlin.math.roundToInt
import kotlin.properties.Delegates

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
    private lateinit var progressBar: ProgressBar
    private lateinit var ingredientLoaderManager: LoaderManager
    private var ingredientId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient)
        ingredientId = intent.getIntExtra("ID", savedInstanceState?.getInt("ID") ?: -1)

        ingredientLoaderManager = LoaderManager.getInstance(this)

        cocktailsListAdapter.type = CocktailsListAdapter.Type.BY_INGREDIENTS
        progressBar = findViewById(R.id.ingredientProgressBar)

        val ingredientLoader = ingredientLoaderManager.getLoader<Ingredient>(IngredientLoader.ID)
        val arg = Bundle()
        arg.putInt("id", ingredientId)
        if (ingredientLoader == null) {
            ingredientLoaderManager.initLoader(IngredientLoader.ID, arg, this)
        } else {
            ingredientLoaderManager.restartLoader(IngredientLoader.ID, arg, this)
        }

        val layoutManager = LinearLayoutManager(this)
        cocktails = findViewById(R.id.cocktailsRecyclerView)
        cocktails.layoutManager = layoutManager
        cocktails.isNestedScrollingEnabled = true
        cocktails.adapter = cocktailsListAdapter
        cocktails.isNestedScrollingEnabled = false

        val ingredientScrollView = findViewById<NestedScrollView>(R.id.ingredientScrollView)
        ingredientScrollView.setOnScrollChangeListener(object : PaginationScrollListener(cocktails.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                cocktailsListAdapter.isLoading = true
                loadNextPage()
            }

            override fun isLastPage(): Boolean {
                return cocktailsListAdapter.isLastPage
            }

            override fun isLoading(): Boolean {
                return cocktailsListAdapter.isLoading
            }

        })
    }

    fun loadNextPage() {
        val ingredientLoader = ingredientLoaderManager.getLoader<Ingredient>(IngredientLoader.ID)
        val arg = Bundle()
        arg.putInt("id", ingredientId)
        arg.putInt("start", cocktailsListAdapter.currentId)
        if (ingredientLoader == null) {
            ingredientLoaderManager.initLoader(IngredientLoader.ID, arg, this)
        } else {
            ingredientLoaderManager.restartLoader(IngredientLoader.ID, arg, this)
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Ingredient> {
        val start = args?.getInt("start")
        return IngredientLoader(this, args!!["id"] as Int, start)
    }

    override fun onLoadFinished(loader: Loader<Ingredient>, data: Ingredient?) {
        if (loader.id == IngredientLoader.ID) {
            val ingredient = data!!

            if (cocktailsListAdapter.currentId == 0) {
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
                    val cardLayoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    cardLayoutParams.leftMargin = tagMargin
                    cardLayoutParams.rightMargin = tagMargin
                    cardLayoutParams.topMargin = tagMargin
                    cardLayoutParams.bottomMargin = tagMargin
                    cardView.layoutParams = cardLayoutParams
                    cardView.elevation = tagMargin.toFloat()

                    val tagView = TextView(this)
                    val tagLayout = ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    tagLayout.leftMargin = tagMargin
                    tagLayout.rightMargin = tagMargin
                    tagView.text = tag
                    tagView.layoutParams = tagLayout

                    cardView.addView(tagView)
                    tagsLayout.addView(cardView)
                }

                val cardView = findViewById<CardView>(R.id.ingredientCard)
                cardView.visibility = View.VISIBLE
            } else {
                cocktailsListAdapter.removeLoadingFooter()
            }

            cocktailsListAdapter.isLoading = false
            cocktailsListAdapter.addAll(ingredient.cocktails)

            if (ingredient.cocktails.size == IngredientLoader.LIMIT) {
                cocktailsListAdapter.addLoadingFooter()
            } else {
                cocktailsListAdapter.isLastPage = true
            }

            progressBar.visibility = View.GONE
        }
    }

    override fun onLoaderReset(loader: Loader<Ingredient>) {
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("ID", intent.getIntExtra("ID", -1))
    }
}