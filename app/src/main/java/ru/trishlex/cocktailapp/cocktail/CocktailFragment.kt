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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.trishlex.cocktailapp.PaginationScrollListener
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.cocktail.loader.CocktailLoaderCallback
import ru.trishlex.cocktailapp.cocktail.loader.CocktailsLoader
import ru.trishlex.cocktailapp.cocktail.model.CocktailItem
import ru.trishlex.cocktailapp.cocktail.recycler.CocktailsListAdapter
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService
import java.util.concurrent.atomic.AtomicBoolean


class CocktailFragment : Fragment(R.layout.fragment_cocktail) {

    private lateinit var cocktailsListAdapter: CocktailsListAdapter

    private lateinit var cocktails: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchCocktailByNameView: AutoCompleteTextView
    private lateinit var cocktailLoaderManager: LoaderManager

    private lateinit var selectedCocktailsService: SelectedCocktailsService
    private lateinit var selectedIngredientsService: SelectedIngredientsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cocktailLoaderManager = LoaderManager.getInstance(requireActivity())
    }

    @Volatile
    private var showKeyBoard = AtomicBoolean(false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cocktail, container, false)
        progressBar = view.findViewById(R.id.cocktailFragmentProgressBar)

        val preferences = requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE)
        cocktailsListAdapter = CocktailsListAdapter.getInstance(preferences)
        selectedCocktailsService = SelectedCocktailsService.getInstance(preferences)
        selectedIngredientsService = SelectedIngredientsService.getInstance(preferences)

        searchCocktailByNameView = view.findViewById(R.id.searchCocktailByName)
        searchCocktailByNameView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    cocktailsListAdapter.type = CocktailsListAdapter.Type.BY_NAME
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
                    val args = CocktailsLoader.Args(
                        CocktailsLoader.ArgType.BY_NAME,
                        requireView().findViewById<TextView>(R.id.searchCocktailByName).text.toString(),
                        cocktailsListAdapter.nextKey
                    )
                    loadData(args)
                    return true
                }
                return false
            }
        })
        searchCocktailByNameView.threshold = 1
        searchCocktailByNameView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                showKeyBoard.set(false)
                cocktailsListAdapter.type = CocktailsListAdapter.Type.BY_NAME
                Log.d("debugLog", "CocktailFragment: enter")
                cocktailsListAdapter.removeAll()
                progressBar.visibility = View.VISIBLE
                val args = CocktailsLoader.Args(
                    CocktailsLoader.ArgType.BY_NAME,
                    requireView().findViewById<TextView>(R.id.searchCocktailByName).text.toString(),
                    cocktailsListAdapter.nextKey
                )
                loadData(args)
            }
        })
        searchCocktailByNameView.setOnClickListener {
            showKeyBoard.set(true)
            val imm: InputMethodManager = requireActivity()
                .getSystemService(Activity.INPUT_METHOD_SERVICE)as InputMethodManager
            imm.showSoftInput(searchCocktailByNameView, 0)
        }

        cocktails = view.findViewById(R.id.cocktailsRecyclerView)
        val layoutManager = LinearLayoutManager(view.context)
        cocktails.layoutManager = layoutManager
        cocktails.isNestedScrollingEnabled = false
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
                    val imm: InputMethodManager = requireActivity()
                        .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    showKeyBoard.set(false)
                }
            }
        }

        return view
    }

    fun loadNextPage() {
        Log.d("debugLog", "load more cocktails")
        when (cocktailsListAdapter.type) {
            CocktailsListAdapter.Type.BY_NAME -> loadData(
                CocktailsLoader.Args(
                    CocktailsLoader.ArgType.BY_NAME,
                    requireView().findViewById<TextView>(R.id.searchCocktailByName).text.toString(),
                    cocktailsListAdapter.nextKey
                )
            )
            CocktailsListAdapter.Type.BY_INGREDIENTS -> loadData(
                CocktailsLoader.Args(
                    CocktailsLoader.ArgType.BY_INGREDIENTS,
                    selectedIngredientsService.getSelectedItemIds(),
                    cocktailsListAdapter.nextKey
                )
            )
            CocktailsListAdapter.Type.BY_IDS -> throw UnsupportedOperationException()
        }
    }

    private fun loadData(loaderArgs: CocktailsLoader.Args<*>) {
        val cocktailsLoader = cocktailLoaderManager.getLoader<List<CocktailItem>>(CocktailsLoader.ID)
        val args = Bundle()
        args.putSerializable("loaderArgs", loaderArgs)
        if (cocktailsLoader == null) {
            cocktailLoaderManager.initLoader(
                CocktailsLoader.ID,
                args,
                CocktailLoaderCallback(
                    requireContext(),
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
                    requireContext(),
                    selectedCocktailsService,
                    cocktailsListAdapter,
                    progressBar
                )
            )
        }
    }

    fun updateCocktails() {
//        cocktails.adapter = cocktailsListAdapter
    }
}