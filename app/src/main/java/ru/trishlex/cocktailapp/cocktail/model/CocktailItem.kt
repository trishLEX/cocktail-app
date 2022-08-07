package ru.trishlex.cocktailapp.cocktail.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.openapitools.client.model.CocktailIngredientDTO
import org.openapitools.client.model.CocktailLightDTO
import ru.trishlex.cocktailapp.Item
import ru.trishlex.cocktailapp.ingredient.model.IngredientItem
import ru.trishlex.cocktailapp.ingredient.model.IngredientType

open class CocktailItem(
    id: Int,
    name: String,
    preview: Bitmap?,
    open val ingredients: List<CocktailItemIngredient>,
    isSelected: Boolean = false,
    val missingCount: Int = 0,
) : Item(id, name, preview, isSelected) {
    constructor(cocktailLightDTO: CocktailLightDTO) : this(
        cocktailLightDTO.id,
        cocktailLightDTO.name,
        BitmapFactory.decodeByteArray(cocktailLightDTO.preview, 0, cocktailLightDTO.preview.size),
        cocktailLightDTO.ingredients.map { CocktailItemIngredient(it) },
        missingCount = cocktailLightDTO.missingIngredientsCount ?: 0
    )

    constructor(cocktailLightDTO: CocktailLightDTO, isSelected: Boolean) : this(
        cocktailLightDTO.id,
        cocktailLightDTO.name,
        BitmapFactory.decodeByteArray(cocktailLightDTO.preview, 0, cocktailLightDTO.preview.size),
        cocktailLightDTO.ingredients.map { CocktailItemIngredient(it) },
        isSelected,
        cocktailLightDTO.missingIngredientsCount ?: 0
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
    preview: Bitmap,
    ingredientType: IngredientType
) : IngredientItem(id, name, preview, ingredientType) {

    constructor(cocktailIngredientDTO: CocktailIngredientDTO) : this(
        cocktailIngredientDTO.id,
        cocktailIngredientDTO.name,
        BitmapFactory.decodeByteArray(cocktailIngredientDTO.preview, 0, cocktailIngredientDTO.preview.size),
        IngredientType(cocktailIngredientDTO.type)
    )
}