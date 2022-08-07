package ru.trishlex.cocktailapp.tool

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.CocktailToolDTO

data class ToolItem(
    val id: Int,
    val name: String,
    val preview: Bitmap
) {

    constructor(toolDTO: CocktailToolDTO): this(
        toolDTO.id,
        toolDTO.name,
        BitmapFactory.decodeByteArray(toolDTO.preview, 0, toolDTO.preview.size)
    )
}