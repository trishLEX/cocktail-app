package ru.trishlex.cocktailapp.cocktail.menu

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.PaginationScrollListener
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.cocktail.SelectedCocktailsService
import ru.trishlex.cocktailapp.cocktail.loader.CocktailLoaderCallback
import ru.trishlex.cocktailapp.cocktail.loader.CocktailsLoader
import ru.trishlex.cocktailapp.cocktail.model.CocktailItem
import ru.trishlex.cocktailapp.cocktail.recycler.CocktailsListAdapter
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.properties.Delegates

class MyCocktailsActivity : AppCompatActivity() {

    private lateinit var cocktails: RecyclerView
    private lateinit var cocktailsListAdapter: CocktailsListAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var selectedCocktailsService: SelectedCocktailsService
    private lateinit var selectedIngredientsService: SelectedIngredientsService
    private lateinit var cocktailLoaderManager: LoaderManager

    private var currentLoaderId by Delegates.notNull<Int>()

    @Volatile
    private var showKeyBoard = AtomicBoolean(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cocktails)

        cocktailLoaderManager = LoaderManager.getInstance(this)
        progressBar = findViewById(R.id.myCocktailsProgressBar)

        val preferences = getSharedPreferences("preferences", MODE_PRIVATE)
        selectedCocktailsService = SelectedCocktailsService.getInstance(preferences)
        selectedIngredientsService = SelectedIngredientsService.getInstance(preferences)
        cocktailsListAdapter = CocktailsListAdapter(selectedCocktailsService, selectedIngredientsService)


        val searchCocktailByNameView = findViewById<AutoCompleteTextView>(R.id.searchCocktailByName)
        searchCocktailByNameView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                showKeyBoard.set(false)
                currentLoaderId = CocktailsLoader.ID
                cocktailsListAdapter.removeAll()
                progressBar.visibility = View.VISIBLE
                loadData(
                    CocktailsLoader.Args(
                        CocktailsLoader.ArgType.BY_NAME,
                        findViewById<TextView>(R.id.searchCocktailByName).text.toString()
                    )
                )
            }
        })
        searchCocktailByNameView.setOnClickListener {
            showKeyBoard.set(true)
            val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE)as InputMethodManager
            imm.showSoftInput(searchCocktailByNameView, 0)
        }

        loadSelectedCocktails()

        cocktails = findViewById(R.id.cocktailsRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        cocktails.layoutManager = layoutManager
        cocktails.adapter = cocktailsListAdapter
        cocktails.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
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
        cocktails.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY != oldScrollY) {
                cocktailsListAdapter.isLoading = false
                if (!showKeyBoard.get()) {
                    val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    showKeyBoard.set(false)
                }
            }
        }

        val button = findViewById<Button>(R.id.myCocktailsButton)
        button.setOnClickListener {
            cocktailsListAdapter.removeAll()
            loadSelectedCocktails()
        }
    }

    fun loadNextPage() {
        when (cocktailsListAdapter.type) {
            CocktailsListAdapter.Type.BY_NAME -> loadData(
                CocktailsLoader.Args(
                    CocktailsLoader.ArgType.BY_NAME,
                    findViewById<TextView>(R.id.searchCocktailByName).text.toString(),
                    cocktailsListAdapter.nextKey
                )
            )
            CocktailsListAdapter.Type.BY_IDS -> loadSelectedCocktails()
            else -> throw UnsupportedOperationException()
        }
    }

    private fun loadData(loaderArgs: CocktailsLoader.Args<*>) {
        cocktailsListAdapter.type = CocktailsListAdapter.Type.BY_NAME
        val cocktailsLoader = cocktailLoaderManager.getLoader<List<CocktailItem>>(CocktailsLoader.ID)
        val args = Bundle()
        args.putSerializable("loaderArgs", loaderArgs)
        if (cocktailsLoader == null) {
            cocktailLoaderManager.initLoader(
                CocktailsLoader.ID,
                args,
                CocktailLoaderCallback(
                    this,
                    selectedCocktailsService,
                    cocktailsListAdapter,
                    progressBar
                )
            )
            Log.d("debugLog", "CocktailFragment: init loader")
        } else {
            cocktailLoaderManager.restartLoader(
                CocktailsLoader.ID,
                args,
                CocktailLoaderCallback(
                    this,
                    selectedCocktailsService,
                    cocktailsListAdapter,
                    progressBar
                )
            )
        }
    }

    private fun loadSelectedCocktails() {
        cocktailsListAdapter.type = CocktailsListAdapter.Type.BY_IDS
        val cocktailsByIdsLoader = cocktailLoaderManager.getLoader<List<CocktailItem>>(CocktailByIdsLoader.ID)
        val args = Bundle()
        args.putInt("start", cocktailsListAdapter.nextKey)
        if (cocktailsByIdsLoader == null) {
            currentLoaderId = CocktailByIdsLoader.ID
            cocktailLoaderManager.initLoader(
                CocktailByIdsLoader.ID,
                args,
                CocktailLoaderCallback(
                    this,
                    selectedCocktailsService,
                    cocktailsListAdapter,
                    progressBar
                )
            )
        } else {
            currentLoaderId = CocktailByIdsLoader.ID
            cocktailLoaderManager.restartLoader(
                CocktailByIdsLoader.ID,
                args,
                CocktailLoaderCallback(
                    this,
                    selectedCocktailsService,
                    cocktailsListAdapter,
                    progressBar
                )
            )
        }
    }
}