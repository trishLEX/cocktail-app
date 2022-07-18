package ru.trishlex.cocktailapp.ingredient.menu

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.ingredient.IngredientItem
import ru.trishlex.cocktailapp.ingredient.IngredientsListAdapter
import ru.trishlex.cocktailapp.ingredient.IngredientsLoader
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService
import java.util.concurrent.atomic.AtomicBoolean

class MyIngredientsActivity(
) : AppCompatActivity(),
    LoaderManager.LoaderCallbacks<List<IngredientItem>>
{

    private lateinit var ingredients: RecyclerView
    private lateinit var ingredientsListAdapter: IngredientsListAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var selectedIngredientsService: SelectedIngredientsService

    @Volatile
    private var showKeyBoard = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ingredients)

        progressBar = findViewById(R.id.myIngredientsProgressBar)

        val preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        selectedIngredientsService = SelectedIngredientsService.getInstance(preferences)
        ingredientsListAdapter = IngredientsListAdapter(ArrayList(), 0, selectedIngredientsService)

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
                    Log.d("debugLog", "IngredientFragment: selected ${ingredientsListAdapter.getSelectedIngredients()}")
                    return true
                }
                return false
            }
        })
        searchIngredientView.threshold = 1
//        searchIngredientView.setAdapter(IngredientSearchAdapter(this))
        searchIngredientView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                showKeyBoard.set(false)
                progressBar.visibility = View.VISIBLE

                val ingredientsByNameLoader = loaderManager.getLoader<List<IngredientItem>>(IngredientsLoader.ID)
                if (ingredientsByNameLoader == null) {
                    loaderManager.initLoader(IngredientsLoader.ID, null, this@MyIngredientsActivity)
                } else {
                    loaderManager.restartLoader(IngredientsLoader.ID, null, this@MyIngredientsActivity)
                }
            }
        })
        searchIngredientView.setOnClickListener {
            showKeyBoard.set(true)
            val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE)as InputMethodManager
            imm.showSoftInput(searchIngredientView, 0)
        }

        ingredients = findViewById(R.id.ingredientsRecyclerView)
        ingredients.layoutManager = LinearLayoutManager(this)
        ingredients.adapter = ingredientsListAdapter

        ingredients.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY != oldScrollY) {
                if (!showKeyBoard.get()) {
                    val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    showKeyBoard.set(false)
                }
            }
        }

        val button = findViewById<Button>(R.id.myIngredientsButton)
        button.setOnClickListener {
            if (ingredientsLoader == null) {
                loaderManager.initLoader(IngredientByIdsLoader.ID, null, this)
            } else {
                loaderManager.restartLoader(IngredientByIdsLoader.ID, null, this)
            }
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<IngredientItem>> {
        return when (id) {
            IngredientByIdsLoader.ID -> {
                IngredientByIdsLoader(this, selectedIngredientsService.getSelectedItemIds())
            }
            IngredientsLoader.ID -> {
                val text = findViewById<TextView>(R.id.searchIngredientByName).text.toString()
                IngredientsLoader(this, text, selectedIngredientsService)
            }
            else -> throw UnsupportedOperationException()
        }
    }

    override fun onLoadFinished(loader: Loader<List<IngredientItem>>, data: List<IngredientItem>?) {
        if (loader.id == IngredientByIdsLoader.ID) {
            ingredientsListAdapter.ingredients = data!!
            ingredientsListAdapter.ingredientsCount = 0
            ingredients.adapter = ingredientsListAdapter
            progressBar.visibility = View.GONE
        } else if (loader.id == IngredientsLoader.ID) {
            ingredientsListAdapter.ingredients = data!!
            ingredientsListAdapter.ingredientsCount = 0
            ingredients.adapter = ingredientsListAdapter
            progressBar.visibility = View.GONE
        }
    }

    override fun onLoaderReset(loader: Loader<List<IngredientItem>>) {
    }
}