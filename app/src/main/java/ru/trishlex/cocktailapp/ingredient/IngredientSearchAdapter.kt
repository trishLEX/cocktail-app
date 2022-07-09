package ru.trishlex.cocktailapp.ingredient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import org.openapitools.client.api.IngredientApi
import kotlin.math.min

class IngredientSearchAdapter(
    context: Context,
    private val ingredientApi: IngredientApi = IngredientApi(),
    private var names: ArrayList<String> = ArrayList()
) : ArrayAdapter<String>(context,  com.google.android.material.R.layout.support_simple_spinner_dropdown_item, names) {

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
        return IngredientFilter(ingredientApi, this)
    }

    class IngredientFilter(
        private val ingredientApi: IngredientApi,
        private val ingredientSearchAdapter: IngredientSearchAdapter
    ) : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            if (constraint?.isNotEmpty() == true) {
                val res = ingredientApi.getIngredientNames(constraint.toString()).map { it.name }
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
            ingredientSearchAdapter.names.clear()
            ingredientSearchAdapter.names.addAll(results.values as List<String>)
            ingredientSearchAdapter.notifyDataSetChanged()
        }
    }
}