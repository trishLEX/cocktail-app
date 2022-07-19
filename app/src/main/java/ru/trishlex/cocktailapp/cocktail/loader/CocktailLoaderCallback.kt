package ru.trishlex.cocktailapp.cocktail.loader

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.cocktail.CocktailItem
import ru.trishlex.cocktailapp.cocktail.SelectedCocktailsService
import ru.trishlex.cocktailapp.cocktail.recycler.CocktailsListAdapter
import ru.trishlex.cocktailapp.loader.AsyncResult

class CocktailLoaderCallback(
    private val context: Context,
    private val sharedPreferences: SharedPreferences,
    private val cocktailsListAdapter: CocktailsListAdapter,
    private val progressBar: ProgressBar
) : LoaderManager.LoaderCallbacks<AsyncResult<List<CocktailItem>>> {

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<AsyncResult<List<CocktailItem>>> {
        val start = args?.getInt("start")
        return CocktailsLoader(
            context,
            CocktailsLoader.Args(
                CocktailsLoader.ArgType.BY_INGREDIENTS,
                args!!.getIntegerArrayList("INGREDIENTS")!!.toList(),
                start
            ),
            SelectedCocktailsService.getInstance(sharedPreferences)
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onLoadFinished(
        loader: Loader<AsyncResult<List<CocktailItem>>>,
        data: AsyncResult<List<CocktailItem>>?
    ) {
        if (loader.id == CocktailsLoader.ID) {
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
                Toast.makeText(context, R.string.internetError, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLoaderReset(loader: Loader<AsyncResult<List<CocktailItem>>>) {
    }
}