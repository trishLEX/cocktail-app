package ru.trishlex.cocktailapp.cocktail.construct

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import ru.trishlex.cocktailapp.R
import ru.trishlex.cocktailapp.ingredient.picker.IngredientPickerService
import ru.trishlex.cocktailapp.ingredient.picker.IngredientsPickerLoader
import ru.trishlex.cocktailapp.ingredient.picker.PickerIngredient
import ru.trishlex.cocktailapp.loader.AsyncResult

class PickedIngredientCallback(
    private val activity: AppCompatActivity,
    private var ingredientPickerService: IngredientPickerService,

    @Volatile
    var isFinished: Boolean = false
) : LoaderManager.LoaderCallbacks<AsyncResult<List<PickerIngredient>>> {

    lateinit var pickedToolCallback: PickedToolCallback

    override fun onCreateLoader(
        id: Int,
        args: Bundle?
    ): Loader<AsyncResult<List<PickerIngredient>>> {
        return IngredientsPickerLoader(activity.baseContext)
    }

    override fun onLoadFinished(
        loader: Loader<AsyncResult<List<PickerIngredient>>>,
        data: AsyncResult<List<PickerIngredient>>?
    ) {
        if (loader.id == IngredientsPickerLoader.ID) {
            isFinished = true
            Log.d("debugLog", "PickedIngredientCallback finished, pickedToolCallback ${pickedToolCallback.isFinished}")
            if (data!!.isCompleted()) {
                ingredientPickerService.putIngredients(data.result!!)

                if (pickedToolCallback.isFinished) {
                    activity.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                    activity.findViewById<LinearLayout>(R.id.commonLayout).visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(activity, R.string.internetError, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLoaderReset(loader: Loader<AsyncResult<List<PickerIngredient>>>) {
        isFinished = false
    }
}