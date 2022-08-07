package ru.trishlex.cocktailapp.ingredient.model

import org.openapitools.client.model.IngredientTypeDTO
import java.io.Serializable

data class IngredientType(
    val type: IngredientTypeEnum?,
    val name: String
) : Serializable {

    constructor(ingredientTypeDTO: IngredientTypeDTO): this(
        IngredientTypeEnum.fromDto(ingredientTypeDTO),
        ingredientTypeDTO.name
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IngredientType) return false

        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        return type?.hashCode() ?: 0
    }


}
