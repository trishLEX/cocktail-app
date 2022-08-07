package ru.trishlex.cocktailapp.tool.picker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.ToolLightDTO
import ru.trishlex.cocktailapp.PickedItem

class PickedTool(
    id: Int,
    name: String,
    preview: Bitmap
) : PickedItem(id, name, preview) {

    constructor(toolLightDTO: ToolLightDTO) : this(
        toolLightDTO.id,
        toolLightDTO.name,
        BitmapFactory.decodeByteArray(toolLightDTO.preview, 0, toolLightDTO.preview.size)
    )

}