package ru.trishlex.cocktailapp.ingredient.loader

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.ingredient.SelectedIngredientsService
import ru.trishlex.cocktailapp.ingredient.menu.IngredientByIdsLoader
import ru.trishlex.cocktailapp.ingredient.model.IngredientItem
import ru.trishlex.cocktailapp.ingredient.recycler.IngredientsListAdapter
import ru.trishlex.cocktailapp.ingredient.shoplist.IngredientsToBuyLoader
import ru.trishlex.cocktailapp.loader.AsyncResult

class IngredientsLoaderCallback(
    private val context: Context,
    private val selectedIngredientsService: SelectedIngredientsService,
    private val ingredientsListAdapter: IngredientsListAdapter,
    private val progressBar: ProgressBar
) : LoaderManager.LoaderCallbacks<AsyncResult<List<IngredientItem>>> {

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<AsyncResult<List<IngredientItem>>> {
        return when (id) {
            IngredientsToBuyLoader.ID -> {
                IngredientsToBuyLoader(context, selectedIngredientsService)
            }
            IngredientsLoader.ID -> {
                IngredientsLoader(context, args!!.getString("name")!!, selectedIngredientsService)
            }
            IngredientByIdsLoader.ID -> {
                IngredientByIdsLoader(context, selectedIngredientsService.getSelectedItemIds())
            }
            else -> throw UnsupportedOperationException("Unsupported ID $id")
        }
    }

    override fun onLoadFinished(
        loader: Loader<AsyncResult<List<IngredientItem>>>,
        data: AsyncResult<List<IngredientItem>>?
    ) {
        if (loader.id == IngredientsToBuyLoader.ID
            || loader.id == IngredientsLoader.ID
            || loader.id == IngredientByIdsLoader.ID
        ) {
            if (data!!.isCompleted()) {
                ingredientsListAdapter.addAll(data.result!!)
                progressBar.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                Toast.makeText(context, R.string.internetError, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLoaderReset(loader: Loader<AsyncResult<List<IngredientItem>>>) {
    }
}