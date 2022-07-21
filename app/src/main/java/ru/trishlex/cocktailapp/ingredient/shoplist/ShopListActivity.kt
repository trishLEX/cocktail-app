package ru.trishlex.cocktailapp.ingredient.shoplist

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.db.ShopListDao
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService
import ru.trishlex.cocktailapp.ingredient.loader.IngredientsLoader
import ru.trishlex.cocktailapp.ingredient.model.IngredientItem
import ru.trishlex.cocktailapp.ingredient.recycler.IngredientsListAdapter
import ru.trishlex.cocktailapp.loader.AsyncResult
import java.util.concurrent.atomic.AtomicBoolean

class ShopListActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<AsyncResult<List<IngredientItem>>> {
    private lateinit var ingredients: RecyclerView
    private lateinit var ingredientsListAdapter: IngredientsListAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var selectedIngredientsService: SelectedIngredientsService

    @Volatile
    private var showKeyBoard = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_list)

        progressBar = findViewById(R.id.myShopListProgressBar)

        val preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        selectedIngredientsService = SelectedIngredientsService.getInstance(preferences)
        ingredientsListAdapter = IngredientsListAdapter(
            ArrayList(),
            0,
            selectedIngredientsService,
            ShopListDao(this)
        )

        val loaderManager = LoaderManager.getInstance(this)
        val ingredientsLoader = loaderManager.getLoader<List<IngredientItem>>(IngredientsLoader.ID)
        if (ingredientsLoader == null) {
            loaderManager.initLoader(IngredientsToBuyLoader.ID, null, this)
        } else {
            loaderManager.restartLoader(IngredientsToBuyLoader.ID, null, this)
        }

        val searchIngredientView = findViewById<AutoCompleteTextView>(R.id.searchIngredientByName)
        searchIngredientView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchIngredientView.dismissDropDown()
                    val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    var focus = currentFocus
                    if (focus == null) {
                        focus = View(this@ShopListActivity)
                    }
                    imm.hideSoftInputFromWindow(focus.windowToken, 0)

                    progressBar.visibility = View.VISIBLE
                    val ingredientsByNameLoader = loaderManager.getLoader<List<IngredientItem>>(
                        IngredientsLoader.ID)
                    if (ingredientsByNameLoader == null) {
                        loaderManager.initLoader(IngredientsLoader.ID, null, this@ShopListActivity)
                    } else {
                        loaderManager.restartLoader(IngredientsLoader.ID, null, this@ShopListActivity)
                    }
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

                val ingredientsByNameLoader = loaderManager.getLoader<List<IngredientItem>>(
                    IngredientsLoader.ID)
                if (ingredientsByNameLoader == null) {
                    loaderManager.initLoader(IngredientsLoader.ID, null, this@ShopListActivity)
                } else {
                    loaderManager.restartLoader(IngredientsLoader.ID, null, this@ShopListActivity)
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

        val button = findViewById<Button>(R.id.myShopListButton)
        button.setOnClickListener {
            if (ingredientsLoader == null) {
                loaderManager.initLoader(IngredientsToBuyLoader.ID, null, this)
            } else {
                loaderManager.restartLoader(IngredientsToBuyLoader.ID, null, this)
            }
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<AsyncResult<List<IngredientItem>>> {
        return when (id) {
            IngredientsToBuyLoader.ID -> {
                IngredientsToBuyLoader(this, selectedIngredientsService)
            }
            IngredientsLoader.ID -> {
                val text = findViewById<TextView>(R.id.searchIngredientByName).text.toString()
                IngredientsLoader(this, text, selectedIngredientsService)
            }
            else -> throw UnsupportedOperationException("Unsupported ID $id")
        }
    }

    override fun onLoadFinished(loader: Loader<AsyncResult<List<IngredientItem>>>, data: AsyncResult<List<IngredientItem>>?) {
        if (loader.id == IngredientsToBuyLoader.ID || loader.id == IngredientsLoader.ID) {
            if (data!!.isCompleted()) {
                ingredientsListAdapter.ingredients = data.result!!
                ingredientsListAdapter.ingredientsCount = 0
                ingredients.adapter = ingredientsListAdapter
                progressBar.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                Toast.makeText(this, R.string.internetError, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLoaderReset(loader: Loader<AsyncResult<List<IngredientItem>>>) {
    }
}