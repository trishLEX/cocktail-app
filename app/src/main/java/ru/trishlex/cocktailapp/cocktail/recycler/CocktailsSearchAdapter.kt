package ru.trishlex.cocktailapp.cocktail.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import org.openapitools.client.api.CocktailApi
import kotlin.math.min

class CocktailsSearchAdapter(
    context: Context,
    private val cocktailApi: CocktailApi = CocktailApi(),
    private var names: ArrayList<String> = ArrayList()
) : ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, names) {

    companion object {
        private const val LIMIT = 5
    }

    override fun getCount(): Int {
        return min(LIMIT, names.count())
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater
            .from(context)
            .inflate(com.google.android.material.R.layout.support_simple_spinner_dropdown_item, null) as TextView
        view.setPadding(10, 10, 0, 10)
        val name = names[position]
        view.text = name
        return view
    }

    override fun getFilter(): Filter {
        return CocktailsFilter(cocktailApi, this)
    }

    class CocktailsFilter(
        private val cocktailApi: CocktailApi,
        private val cocktailsSearchAdapter: CocktailsSearchAdapter
        ) : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            if (constraint?.isNotEmpty() == true) {
                val res = cocktailApi.getCocktailNames(constraint.toString()).map { it.name }
                results.values = res
                results.count = res.size
            } else {
                results.values = ArrayList<String>()
                results.count = 0
            }
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            if (results.values == null) {
                return
            }
            cocktailsSearchAdapter.names.clear()
            cocktailsSearchAdapter.names.addAll(results.values as List<String>)
            cocktailsSearchAdapter.notifyDataSetChanged()
        }

    }
}