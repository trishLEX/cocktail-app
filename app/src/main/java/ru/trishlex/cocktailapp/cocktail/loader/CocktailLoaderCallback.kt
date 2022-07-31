package ru.trishlex.cocktailapp.cocktail.loader

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.cocktail.SelectedCocktailsService
import ru.trishlex.cocktailapp.cocktail.menu.CocktailByIdsLoader
import ru.trishlex.cocktailapp.cocktail.model.PagedCocktailItem
import ru.trishlex.cocktailapp.cocktail.recycler.CocktailsListAdapter
import ru.trishlex.cocktailapp.loader.AsyncResult

class CocktailLoaderCallback(
    private val context: Context,
    private val selectedCocktailsService: SelectedCocktailsService,
    private val cocktailsListAdapter: CocktailsListAdapter,
    private val progressBar: ProgressBar
) : LoaderManager.LoaderCallbacks<AsyncResult<PagedCocktailItem>> {

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<AsyncResult<PagedCocktailItem>> {
        return when (id) {
            CocktailsLoader.ID -> CocktailsLoader(
                context,
                args!!.getSerializable("loaderArgs") as CocktailsLoader.Args<*>,
                selectedCocktailsService
            )
            CocktailByIdsLoader.ID -> CocktailByIdsLoader(
                context,
                selectedCocktailsService.getSelectedItemIds(),
                args!!.getInt("start")
            )
            else -> throw UnsupportedOperationException("Unsupported id: $id")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onLoadFinished(
        loader: Loader<AsyncResult<PagedCocktailItem>>,
        data: AsyncResult<PagedCocktailItem>?
    ) {
        if (loader.id == CocktailByIdsLoader.ID || loader.id == CocktailsLoader.ID) {
            if (data!!.result != null) {
                cocktailsListAdapter.removeLoadingFooter()
                cocktailsListAdapter.isLoading = false

                cocktailsListAdapter.addAll(data.result!!)

                if (data.result.hasNext) {
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

    override fun onLoaderReset(loader: Loader<AsyncResult<PagedCocktailItem>>) {
    }
}