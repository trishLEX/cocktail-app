package ru.trishlex.cocktailapp.cocktail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.CocktailIngredientDTO
import org.openapitools.client.model.CocktailLightDTO
import ru.trishlex.cocktailapp.Item
import ru.trishlex.cocktailapp.ingredient.IngredientItem

open class CocktailItem(
    id: Int,
    name: String,
    preview: Bitmap?,
    open val ingredients: List<CocktailItemIngredient>,
    isSelected: Boolean = false
) : Item(id, name, preview, isSelected) {
    constructor(cocktailLightDTO: CocktailLightDTO) : this(
        cocktailLightDTO.id,
        cocktailLightDTO.name,
        BitmapFactory.decodeByteArray(cocktailLightDTO.preview, 0, cocktailLightDTO.preview.size),
        cocktailLightDTO.ingredients.map { CocktailItemIngredient(it) }
    )

    constructor(cocktailLightDTO: CocktailLightDTO, isSelected: Boolean) : this(
        cocktailLightDTO.id,
        cocktailLightDTO.name,
        BitmapFactory.decodeByteArray(cocktailLightDTO.preview, 0, cocktailLightDTO.preview.size),
        cocktailLightDTO.ingredients.map { CocktailItemIngredient(it) },
        isSelected
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

open class CocktailItemIngredient(
    id: Int,
    name: String,
    preview: Bitmap
) : IngredientItem(id, name, preview) {

    constructor(cocktailIngredientDTO: CocktailIngredientDTO) : this(
        cocktailIngredientDTO.id,
        cocktailIngredientDTO.name,
        BitmapFactory.decodeByteArray(cocktailIngredientDTO.preview, 0, cocktailIngredientDTO.preview.size)
    )
}