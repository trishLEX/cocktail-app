package ru.trishlex.cocktailapp.cocktail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.CocktailIngredientDTO
import org.openapitools.client.model.CocktailLightDTO
import ru.trishlex.cocktailapp.Item

class CocktailItem(
    id: Int,
    name: String,
    preview: Bitmap?,
    val ingredients: List<CocktailItemIngredient>,
    isSelected: Boolean = false
) : Item(id, name, preview, isSelected) {
    constructor(cocktailLightDTO: CocktailLightDTO) : this(
        cocktailLightDTO.id,
        cocktailLightDTO.name,
        BitmapFactory.decodeByteArray(cocktailLightDTO.preview, 0, cocktailLightDTO.preview.size),
        cocktailLightDTO.ingredients.map { CocktailItemIngredient(it) }
    )

    constructor() : this(
        -1,
        "",
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
        ArrayList()
    )

    constructor(id: Int, name: String, isSelected: Boolean): this(
        id,
        name,
        null,
        ArrayList(),
        isSelected
    )
}

data class CocktailItemIngredient(val id: Int, val name: String, val preview: Bitmap) {

    constructor(cocktailIngredientDTO: CocktailIngredientDTO) : this(
        cocktailIngredientDTO.id,
        cocktailIngredientDTO.name,
        BitmapFactory.decodeByteArray(cocktailIngredientDTO.preview, 0, cocktailIngredientDTO.preview.size)
    )
}