package ru.trishlex.cocktailapp.cocktail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader

class CocktailLoaderCallback(
    private val context: Context,
    private val cocktailsListAdapter: CocktailsListAdapter,
    private val progressBar: ProgressBar
) : LoaderManager.LoaderCallbacks<List<CocktailItemView>> {

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<CocktailItemView>> {
        val start = args?.getInt("start")
        return CocktailsLoader(
            context,
            CocktailsLoader.Args(
                CocktailsLoader.ArgType.BY_INGREDIENTS,
                args!!.getIntegerArrayList("INGREDIENTS")!!.toList(),
                start
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onLoadFinished(
        loader: Loader<List<CocktailItemView>>,
        data: List<CocktailItemView>?
    ) {
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

    override fun onLoaderReset(loader: Loader<List<CocktailItemView>>) {
    }
}