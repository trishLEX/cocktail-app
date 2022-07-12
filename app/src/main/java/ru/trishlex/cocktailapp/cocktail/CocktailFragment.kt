package ru.trishlex.cocktailapp.cocktail

import android.app.Activity
import android.os.Bundle
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
import ru.trishlex.cocktailapp.R


class CocktailFragment(
    private val cocktailsListAdapter: CocktailsListAdapter
) : Fragment(R.layout.fragment_cocktail), LoaderManager.LoaderCallbacks<List<CocktailItemView>> {

    private lateinit var cocktails: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchCocktailByNameView: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cocktail, container, false)
        progressBar = view.findViewById(R.id.cocktailFragmentProgressBar)

        searchCocktailByNameView = view.findViewById(R.id.searchCocktailByName)
        val loaderManager = LoaderManager.getInstance(this)
        searchCocktailByNameView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    val cocktailsLoader = loaderManager.getLoader<List<CocktailItemView>>(CocktailsLoader.ID)
                    Log.d("debugLog", "CocktailFragment: enter")
                    searchCocktailByNameView.dismissDropDown()
                    val imm: InputMethodManager = requireActivity()
                        .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    var focus = requireActivity().currentFocus
                    if (focus == null) {
                        focus = View(activity)
                    }
                    imm.hideSoftInputFromWindow(focus.windowToken, 0)
                    progressBar.visibility = View.VISIBLE
                    if (cocktailsLoader == null) {
                        loaderManager.initLoader(CocktailsLoader.ID, null, this@CocktailFragment)
                        Log.d("debugLog", "CocktailFragment: init loader")
                    } else {
                        loaderManager.restartLoader(CocktailsLoader.ID, null, this@CocktailFragment)
                    }
                    return true
                }
                return false
            }
        })
        searchCocktailByNameView.threshold = 1
        searchCocktailByNameView.setAdapter(CocktailsSearchAdapter(requireContext()))

        cocktails = view.findViewById(R.id.cocktailsRecyclerView)
        cocktails.layoutManager = LinearLayoutManager(view.context)
        cocktails.isNestedScrollingEnabled = true

        cocktails.adapter = cocktailsListAdapter

        return view
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<CocktailItemView>> {
        val text = requireView().findViewById<TextView>(R.id.searchCocktailByName).text.toString()
        Log.d("debugLog", "CocktailFragment: loading is created: $text")
        return CocktailsLoader(
            requireContext(),
            CocktailsLoader.Args(CocktailsLoader.ArgType.BY_NAME, text)
        )
    }

    override fun onLoadFinished(loader: Loader<List<CocktailItemView>>, data: List<CocktailItemView>?) {
        Log.d("debugLog", "CocktailFragment: loading is finished in fragment")
        if (loader.id == CocktailsLoader.ID) {
            cocktailsListAdapter.cocktailItemViews = data!!
            cocktailsListAdapter.cocktailsCount = 0
            cocktails.adapter = cocktailsListAdapter
            progressBar.visibility = View.GONE
        }
    }

    override fun onLoaderReset(loader: Loader<List<CocktailItemView>>) {
    }

    fun updateCocktails() {
        cocktailsListAdapter.cocktailsCount = 0
        cocktails.adapter = cocktailsListAdapter
    }
}