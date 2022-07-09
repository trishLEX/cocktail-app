package ru.trishlex.cocktailapp.ingredient

import android.animation.LayoutTransition
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.cocktail.CocktailItemView
import ru.trishlex.cocktailapp.cocktail.CocktailLoaderCallback
import ru.trishlex.cocktailapp.cocktail.CocktailsListAdapter
import ru.trishlex.cocktailapp.cocktail.CocktailsLoader

class IngredientFragment(
    private val cocktailsListAdapter: CocktailsListAdapter
) : Fragment(R.layout.fragment_ingredient),
    LoaderManager.LoaderCallbacks<List<IngredientItem>>
{

    private lateinit var ingredients: RecyclerView
    private lateinit var ingredientItemAdapter: IngredientItemAdapter
    private lateinit var selectedIngredientsService: SelectedIngredientsService
    private lateinit var loadDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadDialog = ProgressDialog(context)
        loadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        loadDialog.setMessage("Loading. Please wait...")
        loadDialog.isIndeterminate = true
        loadDialog.setCanceledOnTouchOutside(false)

        val view = inflater.inflate(R.layout.fragment_ingredient, container, false)
        val selectedIngredientsLayout = view.findViewById<LinearLayout>(R.id.selectedIngredients)
        selectedIngredientsLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        selectedIngredientsService = SelectedIngredientsService(HashMap(), selectedIngredientsLayout)
        ingredientItemAdapter = IngredientItemAdapter(ArrayList(), 0, selectedIngredientsService)

        val searchIngredientView = view.findViewById<AutoCompleteTextView>(R.id.searchIngredientByName)
        val loaderManager = LoaderManager.getInstance(this)

        searchIngredientView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    val ingredientsLoader = loaderManager.getLoader<List<IngredientItem>>(IngredientsLoader.ID)
                    if (ingredientsLoader == null) {
                        loaderManager.initLoader(IngredientsLoader.ID, null, this@IngredientFragment)
                    } else {
                        loaderManager.restartLoader(IngredientsLoader.ID, null, this@IngredientFragment)
                    }
                    Log.d("debugLog", "IngredientFragment: selected ${ingredientItemAdapter.getSelectedIngredients()}")
                    return true
                }
                return false
            }
        })
        searchIngredientView.threshold = 1
        searchIngredientView.setAdapter(IngredientSearchAdapter(requireContext()))

        ingredients = view.findViewById(R.id.ingredientsRecyclerView)
        ingredients.layoutManager = LinearLayoutManager(view.context)
        ingredients.adapter = ingredientItemAdapter

        val searchCocktailsButton = view.findViewById<Button>(R.id.searchCocktailByIngredients)
        searchCocktailsButton.setOnClickListener {
            val cocktailsLoader = loaderManager.getLoader<List<CocktailItemView>>(CocktailsLoader.ID)
            loadDialog.show()
            val args = Bundle()
            args.putIntegerArrayList("INGREDIENTS", selectedIngredientsService.getSelectedIngredientIds())
            if (cocktailsLoader == null) {
               loaderManager.initLoader(
                    CocktailsLoader.ID,
                    args,
                    CocktailLoaderCallback(requireContext(), cocktailsListAdapter, loadDialog)
                )
            } else {
                loaderManager.restartLoader(
                    CocktailsLoader.ID,
                    args,
                    CocktailLoaderCallback(requireContext(), cocktailsListAdapter, loadDialog))
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
            ingredientItemAdapter.ingredients = data!!
            ingredientItemAdapter.ingredientsCount = 0
            ingredients.adapter = ingredientItemAdapter
        }
    }

    override fun onLoaderReset(loader: Loader<List<IngredientItem>>) {
    }
}