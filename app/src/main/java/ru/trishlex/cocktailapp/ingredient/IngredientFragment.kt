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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.cocktail.SelectedCocktailsService
import ru.trishlex.cocktailapp.cocktail.loader.CocktailLoaderCallback
import ru.trishlex.cocktailapp.cocktail.loader.CocktailsLoader
import ru.trishlex.cocktailapp.cocktail.model.CocktailItem
import ru.trishlex.cocktailapp.cocktail.recycler.CocktailsListAdapter
import ru.trishlex.cocktailapp.db.ShopListDao
import ru.trishlex.cocktailapp.ingredient.loader.IngredientsLoader
import ru.trishlex.cocktailapp.ingredient.loader.IngredientsLoaderCallback
import ru.trishlex.cocktailapp.ingredient.model.IngredientItem
import ru.trishlex.cocktailapp.ingredient.recycler.IngredientsListAdapter
import ru.trishlex.cocktailapp.loader.AsyncResult
import java.util.concurrent.atomic.AtomicBoolean

class IngredientFragment : Fragment(R.layout.fragment_ingredient) {

    private lateinit var ingredientLoaderManager: LoaderManager
    private lateinit var cocktailsListAdapter: CocktailsListAdapter
    private lateinit var ingredients: RecyclerView
    private lateinit var ingredientsListAdapter: IngredientsListAdapter
    private lateinit var selectedIngredientsService: SelectedIngredientsService
    private lateinit var selectedCocktailsService: SelectedCocktailsService
    private lateinit var shopListDao: ShopListDao
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
        val preferences = requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE)

        cocktailsListAdapter = CocktailsListAdapter.getInstance(preferences)

        cocktailsProgressBar = requireActivity().findViewById(R.id.cocktailFragmentProgressBar)

        selectedCocktailsService = SelectedCocktailsService.getInstance(preferences)
        selectedIngredientsService = SelectedIngredientsService.getInstance(preferences)

        shopListDao = ShopListDao(requireContext())
        ingredientsListAdapter = IngredientsListAdapter(
            ArrayList(),
            selectedIngredientsService,
            shopListDao
        )

        val searchIngredientView = view.findViewById<AutoCompleteTextView>(R.id.searchIngredientByName)
        ingredientLoaderManager = LoaderManager.getInstance(requireActivity())

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

                ingredientsListAdapter.removeAll()
                loadData(IngredientsLoader.ID)
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
            val cocktailsLoader = ingredientLoaderManager.getLoader<AsyncResult<List<CocktailItem>>>(CocktailsLoader.ID)
            cocktailsListAdapter.type = CocktailsListAdapter.Type.BY_INGREDIENTS
            cocktailsListAdapter.removeAll()
            cocktailsProgressBar.visibility = View.VISIBLE
            val args = Bundle()
            args.putSerializable(
                "loaderArgs",
                CocktailsLoader.Args(
                    CocktailsLoader.ArgType.BY_INGREDIENTS,
                    selectedIngredientsService.getSelectedItemIds()
                )
            )
            if (cocktailsLoader == null) {
               ingredientLoaderManager.initLoader(
                    CocktailsLoader.ID,
                    args,
                    CocktailLoaderCallback(
                        requireContext(),
                        selectedCocktailsService,
                        cocktailsListAdapter,
                        cocktailsProgressBar
                    )
                )
            } else {
                ingredientLoaderManager.restartLoader(
                    CocktailsLoader.ID,
                    args,
                    CocktailLoaderCallback(
                        requireContext(),
                        selectedCocktailsService,
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

    override fun onDestroy() {
        shopListDao.close()
        super.onDestroy()
    }

    private fun loadData(loaderId: Int) {
        val callback = IngredientsLoaderCallback(
            requireContext(),
            selectedIngredientsService,
            ingredientsListAdapter,
            progressBar
        )
        when (loaderId) {
            IngredientsLoader.ID -> {
                val args = Bundle()
                args.putString("name", requireActivity().findViewById<TextView>(R.id.searchIngredientByName).text.toString())
                val ingredientsByNameLoader = ingredientLoaderManager.getLoader<List<IngredientItem>>(IngredientsLoader.ID)
                if (ingredientsByNameLoader == null) {
                    ingredientLoaderManager.initLoader(IngredientsLoader.ID, args, callback)
                } else {
                    ingredientLoaderManager.restartLoader(IngredientsLoader.ID, args, callback)
                }
            }
            else -> throw UnsupportedOperationException()
        }
    }
}