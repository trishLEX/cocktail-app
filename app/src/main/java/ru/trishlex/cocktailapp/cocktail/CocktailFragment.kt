package ru.trishlex.cocktailapp.cocktail

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.PaginationScrollListener
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService


class CocktailFragment(
    private val cocktailsListAdapter: CocktailsListAdapter
) : Fragment(R.layout.fragment_cocktail), LoaderManager.LoaderCallbacks<List<CocktailItem>> {

    private lateinit var cocktails: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchCocktailByNameView: AutoCompleteTextView

    private lateinit var cocktailLoaderManager: LoaderManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cocktailLoaderManager = LoaderManager.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cocktail, container, false)
        progressBar = view.findViewById(R.id.cocktailFragmentProgressBar)

        searchCocktailByNameView = view.findViewById(R.id.searchCocktailByName)
        searchCocktailByNameView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    cocktailsListAdapter.type = CocktailsListAdapter.Type.BY_NAME
                    val cocktailsLoader = cocktailLoaderManager.getLoader<List<CocktailItem>>(CocktailsLoader.ID)
                    Log.d("debugLog", "CocktailFragment: enter")
                    searchCocktailByNameView.dismissDropDown()
                    val imm: InputMethodManager = requireActivity()
                        .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    var focus = requireActivity().currentFocus
                    if (focus == null) {
                        focus = View(activity)
                    }
                    cocktailsListAdapter.removeAll()
                    imm.hideSoftInputFromWindow(focus.windowToken, 0)
                    progressBar.visibility = View.VISIBLE
                    if (cocktailsLoader == null) {
                        cocktailLoaderManager.initLoader(CocktailsLoader.ID, null, this@CocktailFragment)
                        Log.d("debugLog", "CocktailFragment: init loader")
                    } else {
                        cocktailLoaderManager.restartLoader(CocktailsLoader.ID, null, this@CocktailFragment)
                    }
                    return true
                }
                return false
            }
        })
        searchCocktailByNameView.threshold = 1
//        searchCocktailByNameView.setAdapter(CocktailsSearchAdapter(requireContext()))
        searchCocktailByNameView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                cocktailsListAdapter.type = CocktailsListAdapter.Type.BY_NAME
                val cocktailsLoader = cocktailLoaderManager.getLoader<List<CocktailItem>>(CocktailsLoader.ID)
                Log.d("debugLog", "CocktailFragment: enter")
                cocktailsListAdapter.removeAll()
                progressBar.visibility = View.VISIBLE
                if (cocktailsLoader == null) {
                    cocktailLoaderManager.initLoader(CocktailsLoader.ID, null, this@CocktailFragment)
                    Log.d("debugLog", "CocktailFragment: init loader")
                } else {
                    cocktailLoaderManager.restartLoader(CocktailsLoader.ID, null, this@CocktailFragment)
                }
            }
        })

        cocktails = view.findViewById(R.id.cocktailsRecyclerView)
        val layoutManager = LinearLayoutManager(view.context)
        cocktails.layoutManager = layoutManager
        cocktails.isNestedScrollingEnabled = true //todo ???
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

        return view
    }

    fun loadNextPage() {
        Log.d("debugLog", "load more cocktails")
        val cocktailsLoader = cocktailLoaderManager.getLoader<List<CocktailItem>>(CocktailsLoader.ID)
        val args = Bundle()
        args.putInt("start", cocktailsListAdapter.currentId)
        if (cocktailsLoader == null) {
            cocktailLoaderManager.initLoader(CocktailsLoader.ID, args, this@CocktailFragment)
            Log.d("debugLog", "CocktailFragment: init loader")
        } else {
            cocktailLoaderManager.restartLoader(CocktailsLoader.ID, args, this@CocktailFragment)
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<CocktailItem>> {
        val start = args?.getInt("start")
        return when (cocktailsListAdapter.type) {
            CocktailsListAdapter.Type.BY_NAME -> {
                val text = requireView().findViewById<TextView>(R.id.searchCocktailByName).text.toString()
                Log.d("debugLog", "CocktailFragment: loading is created: $text")

                CocktailsLoader(
                    requireContext(),
                    CocktailsLoader.Args(CocktailsLoader.ArgType.BY_NAME, text, start),
                    SelectedCocktailsService.getInstance(requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE))
                )
            }
            CocktailsListAdapter.Type.BY_INGREDIENTS -> {
                val selectedIngredientsService = SelectedIngredientsService.getInstance(
                    requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE)
                )
                CocktailsLoader(
                    requireContext(),
                    CocktailsLoader.Args(
                        CocktailsLoader.ArgType.BY_INGREDIENTS,
                        selectedIngredientsService.getSelectedItemIds(),
                        start
                    ),
                    SelectedCocktailsService.getInstance(requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE))
                )
            }
        }
    }

    override fun onLoadFinished(loader: Loader<List<CocktailItem>>, data: List<CocktailItem>?) {
        Log.d("debugLog", "CocktailFragment: loading is finished in fragment")
        if (loader.id == CocktailsLoader.ID) {
            if (cocktailsListAdapter.currentId != 0) {
                cocktailsListAdapter.removeLoadingFooter()
            }
            cocktailsListAdapter.isLoading = false

            cocktailsListAdapter.addAll(data!!)

            if (data.size == CocktailsLoader.LIMIT) {
                cocktailsListAdapter.addLoadingFooter()
            } else {
                cocktailsListAdapter.isLastPage = true
            }
            progressBar.visibility = View.GONE
        }
    }

    override fun onLoaderReset(loader: Loader<List<CocktailItem>>) {
    }

    fun updateCocktails() {
//        cocktails.adapter = cocktailsListAdapter
    }
}