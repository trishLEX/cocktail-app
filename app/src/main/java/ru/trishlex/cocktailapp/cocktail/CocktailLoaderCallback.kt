package ru.trishlex.cocktailapp.cocktail

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader

class CocktailLoaderCallback(
    private val context: Context,
    private val cocktailsListAdapter: CocktailsListAdapter,
    private val loadDialog: ProgressDialog
) : LoaderManager.LoaderCallbacks<List<CocktailItemView>> {

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<CocktailItemView>> {
        return CocktailsLoader(
            context,
            CocktailsLoader.Args(
                CocktailsLoader.ArgType.BY_INGREDIENTS,
                args!!.getIntegerArrayList("INGREDIENTS")!!.toList()
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onLoadFinished(
        loader: Loader<List<CocktailItemView>>,
        data: List<CocktailItemView>?
    ) {
        if (loader.id == CocktailsLoader.ID) {
            cocktailsListAdapter.cocktailItemViews = data!!
            cocktailsListAdapter.cocktailsCount = 0
            cocktailsListAdapter.notifyDataSetChanged()
            loadDialog.dismiss()
        }
    }

    override fun onLoaderReset(loader: Loader<List<CocktailItemView>>) {
    }
}