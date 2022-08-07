package ru.trishlex.cocktailapp.tool.picker

import android.content.Context
import org.openapitools.client.api.ToolApi
import ru.trishlex.cocktailapp.LoaderType
import ru.trishlex.cocktailapp.loader.SafeAsyncTaskLoader

class ToolPickerLoader(
    context: Context,
    private val toolApi: ToolApi = ToolApi()
) : SafeAsyncTaskLoader<List<PickedTool>>(context) {

    companion object {
        val ID = LoaderType.TOOLS_PICKER_LOADER.id
    }

    override fun load(): List<PickedTool> {
        return toolApi.tools.map { PickedTool(it) }
    }
}