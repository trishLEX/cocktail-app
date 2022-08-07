package ru.trishlex.cocktailapp.ingredient.picker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.IngredientLightDTO
import org.openapitools.client.model.SaveIngredientRequestDTO
import ru.trishlex.cocktailapp.EmptyFieldException
import ru.trishlex.cocktailapp.FIELD
import ru.trishlex.cocktailapp.PickedItem
import ru.trishlex.cocktailapp.ingredient.model.IngredientType
import ru.trishlex.cocktailapp.ingredient.model.Unit

class PickerIngredient(
    id: Int,
    name: String,
    preview: Bitmap,
    val type: IngredientType,
    var amount: Int? = null,
    var unit: Unit = Unit.ML
) : PickedItem(id, name, preview) {

    constructor(ingredientLightDTO: IngredientLightDTO) : this(
        ingredientLightDTO.id,
        ingredientLightDTO.name,
        BitmapFactory.decodeByteArray(ingredientLightDTO.preview, 0, ingredientLightDTO.preview.size),
        IngredientType(ingredientLightDTO.type)
    )

    fun toSaveDto(): SaveIngredientRequestDTO {
        val res = SaveIngredientRequestDTO()
        res.id = id
        res.amount = amount ?: throw EmptyFieldException(FIELD.AMOUNT)
        res.unit = unit.str
        return res
    }
}