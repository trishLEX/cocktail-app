package ru.trishlex.cocktailapp.ingredient.menu

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.openapitools.client.JsonUtil
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.ingredient.*

class MyIngredientsActivity(
) : AppCompatActivity(),
    LoaderManager.LoaderCallbacks<List<IngredientItem>>
{

    private lateinit var ingredients: RecyclerView
    private lateinit var ingredientItemAdapter: IngredientItemAdapter
    private lateinit var loadDialog: ProgressDialog
    private lateinit var progressBar: ProgressBar
    private lateinit var selectedIngredientsService: SelectedIngredientsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ingredients)

        progressBar = findViewById(R.id.myIngredientsProgressBar)

        val preferences = getPreferences(Context.MODE_PRIVATE)
        selectedIngredientsService = SelectedIngredientsService.getInstance(preferences)
        ingredientItemAdapter = IngredientItemAdapter(ArrayList(), 0, selectedIngredientsService)

        val loaderManager = LoaderManager.getInstance(this)
        val ingredientsLoader = loaderManager.getLoader<List<IngredientItem>>(IngredientsLoader.ID)
        if (ingredientsLoader == null) {
            loaderManager.initLoader(IngredientByIdsLoader.ID, null, this)
        } else {
            loaderManager.restartLoader(IngredientByIdsLoader.ID, null, this)
        }

        val searchIngredientView = findViewById<AutoCompleteTextView>(R.id.searchIngredientByName)
        searchIngredientView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchIngredientView.dismissDropDown()
                    val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    var focus = currentFocus
                    if (focus == null) {
                        focus = View(this@MyIngredientsActivity)
                    }
                    imm.hideSoftInputFromWindow(focus.windowToken, 0)

                    progressBar.visibility = View.VISIBLE
                    val ingredientsByNameLoader = loaderManager.getLoader<List<IngredientItem>>(IngredientsLoader.ID)
                    if (ingredientsByNameLoader == null) {
                        loaderManager.initLoader(IngredientsLoader.ID, null, this@MyIngredientsActivity)
                    } else {
                        loaderManager.restartLoader(IngredientsLoader.ID, null, this@MyIngredientsActivity)
                    }
                    Log.d("debugLog", "IngredientFragment: selected ${ingredientItemAdapter.getSelectedIngredients()}")
                    return true
                }
                return false
            }
        })
        searchIngredientView.threshold = 1
        searchIngredientView.setAdapter(IngredientSearchAdapter(this))

        ingredients = findViewById(R.id.ingredientsRecyclerView)
        ingredients.layoutManager = LinearLayoutManager(this)
        ingredients.adapter = ingredientItemAdapter
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<IngredientItem>> {
        if (id == IngredientByIdsLoader.ID) {
            val preferences = getPreferences(Context.MODE_PRIVATE)
            val ids = ArrayList<Int>()
            if (preferences.contains("savedIngredients")) {
                val selected: Map<String, String> =
                    JsonUtil
                        .getGson()
                        .fromJson(
                            preferences.getString("savedIngredients", "{}"),
                            Map::class.java
                        ) as Map<String, String>
                ids.addAll(selected.keys.map { it.toInt() })
            }
            return IngredientByIdsLoader(this, ids)
        } else if (id == IngredientsLoader.ID) {
            val text = findViewById<TextView>(R.id.searchIngredientByName).text.toString()
            return IngredientsLoader(this, text, selectedIngredientsService)
        } else {
            throw UnsupportedOperationException()
        }
    }

    override fun onLoadFinished(loader: Loader<List<IngredientItem>>, data: List<IngredientItem>?) {
        if (loader.id == IngredientByIdsLoader.ID) {
            ingredientItemAdapter.ingredients = data!!
            ingredientItemAdapter.ingredientsCount = 0
            ingredients.adapter = ingredientItemAdapter

            progressBar.visibility = View.GONE
        } else if (loader.id == IngredientsLoader.ID) {
            ingredientItemAdapter.ingredients = data!!
            ingredientItemAdapter.ingredientsCount = 0
            ingredients.adapter = ingredientItemAdapter
            progressBar.visibility = View.GONE
        }
    }

    override fun onLoaderReset(loader: Loader<List<IngredientItem>>) {
    }
}