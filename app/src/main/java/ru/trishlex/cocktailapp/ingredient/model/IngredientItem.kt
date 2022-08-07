package ru.trishlex.cocktailapp.ingredient.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.IngredientLightDTO
import ru.trishlex.cocktailapp.Item

open class IngredientItem(
    id: Int,
    name: String,
    preview: Bitmap?,
    ingredientType: IngredientType,
    isSelected: Boolean = false
) : Item(id, name, preview, isSelected) {

    constructor(ingredient: IngredientLightDTO) : this(
        ingredient.id,
        ingredient.name,
        BitmapFactory.decodeByteArray(ingredient.preview, 0, ingredient.preview.size),
        IngredientType(ingredient.type),
    )

    constructor(ingredient: IngredientLightDTO, isSelected: Boolean) : this(
        ingredient.id,
        ingredient.name,
        BitmapFactory.decodeByteArray(ingredient.preview, 0, ingredient.preview.size),
        IngredientType(ingredient.type),
        isSelected
    )

    constructor(id: Int, name: String, isSelected: Boolean) : this(
        id,
        name,
        null,
        IngredientType(null, ""),
        isSelected
    )
}