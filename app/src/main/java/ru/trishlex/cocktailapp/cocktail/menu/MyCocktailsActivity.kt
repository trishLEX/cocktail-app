package ru.trishlex.cocktailapp.cocktail.menu

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.PaginationScrollListener
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.cocktail.CocktailItem
import ru.trishlex.cocktailapp.cocktail.SelectedCocktailsService
import ru.trishlex.cocktailapp.cocktail.loader.CocktailsLoader
import ru.trishlex.cocktailapp.cocktail.recycler.CocktailsListAdapter
import ru.trishlex.cocktailapp.loader.AsyncResult
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.properties.Delegates

class MyCocktailsActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<AsyncResult<List<CocktailItem>>> {

    private lateinit var cocktails: RecyclerView
    private lateinit var cocktailsListAdapter: CocktailsListAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var selectedCocktailsService: SelectedCocktailsService
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
        cocktailsListAdapter = CocktailsListAdapter(selectedCocktailsService)


        val searchCocktailByNameView = findViewById<AutoCompleteTextView>(R.id.searchCocktailByName)
        searchCocktailByNameView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                showKeyBoard.set(false)
                currentLoaderId = CocktailsLoader.ID
                cocktailsListAdapter.type = CocktailsListAdapter.Type.BY_NAME
                val cocktailsLoader = cocktailLoaderManager.getLoader<AsyncResult<List<CocktailItem>>>(
                    CocktailsLoader.ID)
                cocktailsListAdapter.removeAll()
                progressBar.visibility = View.VISIBLE
                if (cocktailsLoader == null) {
                    cocktailLoaderManager.initLoader(CocktailsLoader.ID, null, this@MyCocktailsActivity)
                } else {
                    cocktailLoaderManager.restartLoader(CocktailsLoader.ID, null, this@MyCocktailsActivity)
                }
            }
        })
        searchCocktailByNameView.setOnClickListener {
            showKeyBoard.set(true)
            val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE)as InputMethodManager
            imm.showSoftInput(searchCocktailByNameView, 0)
        }

        val cocktailsByIdsLoader = cocktailLoaderManager.getLoader<List<CocktailItem>>(CocktailByIdsLoader.ID)
        if (cocktailsByIdsLoader == null) {
            currentLoaderId = CocktailByIdsLoader.ID
            cocktailLoaderManager.initLoader(CocktailByIdsLoader.ID, null, this)
        } else {
            currentLoaderId = CocktailByIdsLoader.ID
            cocktailLoaderManager.restartLoader(CocktailByIdsLoader.ID, null, this)
        }

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
            if (cocktailsByIdsLoader == null) {
                currentLoaderId = CocktailByIdsLoader.ID
                cocktailLoaderManager.initLoader(CocktailByIdsLoader.ID, null, this)
            } else {
                currentLoaderId = CocktailByIdsLoader.ID
                cocktailLoaderManager.restartLoader(CocktailByIdsLoader.ID, null, this)
            }
        }
    }

    fun loadNextPage() {
        val cocktailsLoader = cocktailLoaderManager.getLoader<List<CocktailsLoader>>(CocktailByIdsLoader.ID)
        val args = Bundle()
        args.putInt("start", cocktailsListAdapter.currentId)
        if (cocktailsLoader == null) {
            cocktailLoaderManager.initLoader(currentLoaderId, args, this@MyCocktailsActivity)
        } else {
            cocktailLoaderManager.restartLoader(currentLoaderId, args, this@MyCocktailsActivity)
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<AsyncResult<List<CocktailItem>>> {
        val start = args?.getInt("start")
        return when (id){
            CocktailByIdsLoader.ID -> {
                CocktailByIdsLoader(this, selectedCocktailsService.getSelectedItemIds(), start)
            }
            CocktailsLoader.ID -> {
                val text = findViewById<TextView>(R.id.searchCocktailByName).text.toString()
                CocktailsLoader(
                    this,
                    CocktailsLoader.Args(
                        CocktailsLoader.ArgType.BY_NAME,
                        text,
                        start
                    ),
                    SelectedCocktailsService.getInstance(getSharedPreferences("preferences", MODE_PRIVATE))
                )
            }
            else -> throw UnsupportedOperationException()
        }
    }

    override fun onLoadFinished(loader: Loader<AsyncResult<List<CocktailItem>>>, data: AsyncResult<List<CocktailItem>>?) {
        if (loader.id == CocktailByIdsLoader.ID || loader.id == CocktailsLoader.ID) {
            if (data!!.result != null) {
                cocktailsListAdapter.removeLoadingFooter()
                cocktailsListAdapter.isLoading = false
                cocktailsListAdapter.addAll(data.result!!)

                if (data.result.size == CocktailsLoader.LIMIT) {
                    cocktailsListAdapter.addLoadingFooter()
                } else {
                    cocktailsListAdapter.isLastPage = true
                }
                progressBar.visibility = View.GONE
            } else {
                cocktailsListAdapter.isLoading = false
                Toast.makeText(this, R.string.internetError, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLoaderReset(loader: Loader<AsyncResult<List<CocktailItem>>>) {
    }
}