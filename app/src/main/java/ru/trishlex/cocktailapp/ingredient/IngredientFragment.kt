package ru.trishlex.cocktailapp.ingredient

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.cocktail.CocktailItem
import ru.trishlex.cocktailapp.cocktail.CocktailLoaderCallback
import ru.trishlex.cocktailapp.cocktail.CocktailsListAdapter
import ru.trishlex.cocktailapp.cocktail.CocktailsLoader
import java.util.concurrent.atomic.AtomicBoolean

class IngredientFragment : Fragment(R.layout.fragment_ingredient),
    LoaderManager.LoaderCallbacks<List<IngredientItem>>
{
    private lateinit var cocktailsListAdapter: CocktailsListAdapter
    private lateinit var ingredients: RecyclerView
    private lateinit var ingredientsListAdapter: IngredientsListAdapter
    private lateinit var selectedIngredientsService: SelectedIngredientsService
    private lateinit var progressBar: ProgressBar
    private lateinit var cocktailsProgressBar: ProgressBar

    @Volatile
    private var showKeyBoard = AtomicBoolean(false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_ingredient, container, false)
        progressBar = view.findViewById(R.id.ingredientFragmentProgressBar)
        cocktailsListAdapter = CocktailsListAdapter.getInstance(
            requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE)
        )

        cocktailsProgressBar = requireActivity().findViewById(R.id.cocktailFragmentProgressBar)

        selectedIngredientsService = SelectedIngredientsService.getInstance(requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE))
        ingredientsListAdapter = IngredientsListAdapter(ArrayList(), 0, selectedIngredientsService)

        val searchIngredientView = view.findViewById<AutoCompleteTextView>(R.id.searchIngredientByName)
        val loaderManager = LoaderManager.getInstance(requireActivity())

        searchIngredientView.threshold = 1
//        searchIngredientView.setAdapter(IngredientSearchAdapter(requireContext()))
        searchIngredientView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                showKeyBoard.set(false)
                progressBar.visibility = View.VISIBLE

                val ingredientsLoader = loaderManager.getLoader<List<IngredientItem>>(IngredientsLoader.ID)
                if (ingredientsLoader == null) {
                    loaderManager.initLoader(IngredientsLoader.ID, null, this@IngredientFragment)
                } else {
                    loaderManager.restartLoader(IngredientsLoader.ID, null, this@IngredientFragment)
                }
            }
        })
        searchIngredientView.setOnClickListener {
            showKeyBoard.set(true)
            val imm: InputMethodManager = requireActivity()
                .getSystemService(Activity.INPUT_METHOD_SERVICE)as InputMethodManager
            imm.showSoftInput(searchIngredientView, 0)
        }

        ingredients = view.findViewById(R.id.ingredientsRecyclerView)
        ingredients.layoutManager = LinearLayoutManager(view.context)
        ingredients.adapter = ingredientsListAdapter
        ingredients.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY != oldScrollY) {
                if (!showKeyBoard.get()) {
                    val imm: InputMethodManager = requireActivity()
                        .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    showKeyBoard.set(false)
                }
            }
        }

        val searchCocktailsButton = view.findViewById<Button>(R.id.searchCocktailByIngredients)
        searchCocktailsButton.setOnClickListener {
            val cocktailsLoader = loaderManager.getLoader<List<CocktailItem>>(CocktailsLoader.ID)
            cocktailsListAdapter.type = CocktailsListAdapter.Type.BY_INGREDIENTS
            cocktailsListAdapter.removeAll()
            cocktailsProgressBar.visibility = View.VISIBLE
            val args = Bundle()
            args.putIntegerArrayList("INGREDIENTS", selectedIngredientsService.getSelectedItemIds())
            if (cocktailsLoader == null) {
               loaderManager.initLoader(
                    CocktailsLoader.ID,
                    args,
                    CocktailLoaderCallback(
                        requireContext(),
                        requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE),
                        cocktailsListAdapter,
                        cocktailsProgressBar
                    )
                )
            } else {
                loaderManager.restartLoader(
                    CocktailsLoader.ID,
                    args,
                    CocktailLoaderCallback(
                        requireContext(),
                        requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE),
                        cocktailsListAdapter,
                        cocktailsProgressBar
                    )
                )
            }
            val tabLayout = requireActivity().findViewById<TabLayout>(R.id.tabs)
            tabLayout.getTabAt(0)!!.select()
        }

        return view
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<IngredientItem>> {
        val text = requireView().findViewById<TextView>(R.id.searchIngredientByName).text.toString()
        return IngredientsLoader(requireContext(), text, selectedIngredientsService)
    }

    override fun onLoadFinished(loader: Loader<List<IngredientItem>>, data: List<IngredientItem>?) {
        if (loader.id == IngredientsLoader.ID) {
            ingredientsListAdapter.ingredients = data!!
            ingredientsListAdapter.ingredientsCount = 0
            ingredients.adapter = ingredientsListAdapter
            progressBar.visibility = View.GONE
        }
    }

    override fun onLoaderReset(loader: Loader<List<IngredientItem>>) {
    }
}