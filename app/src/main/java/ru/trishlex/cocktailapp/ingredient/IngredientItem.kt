package ru.trishlex.cocktailapp.ingredient

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.IngredientLightDTO

data class IngredientItem(
    val id: Int,
    val name: String,
    val preview: Bitmap?,
    var isSelected: Boolean = false
) {

    constructor(ingredient: IngredientLightDTO) : this(
        ingredient.id,
        ingredient.name,
        BitmapFactory.decodeByteArray(ingredient.preview, 0, ingredient.preview.size),
    )

    constructor(id: Int, name: String, isSelected: Boolean) : this(
        id,
        name,
        null,
        isSelected
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IngredientItem

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}