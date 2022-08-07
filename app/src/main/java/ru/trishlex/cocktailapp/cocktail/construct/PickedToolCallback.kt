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
import ru.trishlex.cocktailapp.loader.AsyncResult
import ru.trishlex.cocktailapp.tool.picker.PickedTool
import ru.trishlex.cocktailapp.tool.picker.ToolPickerLoader
import ru.trishlex.cocktailapp.tool.picker.ToolPickerService

class PickedToolCallback(
    private val activity: AppCompatActivity,
    private val toolPickerService: ToolPickerService,

    @Volatile
    var isFinished: Boolean = false
) : LoaderManager.LoaderCallbacks<AsyncResult<List<PickedTool>>> {

    lateinit var pickedIngredientCallback: PickedIngredientCallback

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<AsyncResult<List<PickedTool>>> {
        return ToolPickerLoader(activity.baseContext)
    }

    override fun onLoadFinished(
        loader: Loader<AsyncResult<List<PickedTool>>>,
        data: AsyncResult<List<PickedTool>>?
    ) {
        if (loader.id == ToolPickerLoader.ID) {
            isFinished = true
            Log.d("debugLog", "PickedToolCallback finished, pickedIngredientCallback ${pickedIngredientCallback.isFinished}")
            if (data!!.isCompleted()) {
                toolPickerService.putTools(data.result!!)

                if (pickedIngredientCallback.isFinished) {
                    activity.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                    activity.findViewById<LinearLayout>(R.id.commonLayout).visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(activity, R.string.internetError, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLoaderReset(loader: Loader<AsyncResult<List<PickedTool>>>) {
        isFinished = false
    }
}