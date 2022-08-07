package ru.trishlex.cocktailapp.ingredient.picker

import ru.trishlex.cocktailapp.PickerService

class IngredientPickerService private constructor() : PickerService<PickerIngredient>() {

    private val ingredientTypes: HashMap<Int, PickerIngredient> = HashMap()

    companion object {
        private var instance: IngredientPickerService? = null

        @Synchronized
        fun getInstance(): IngredientPickerService {
            if (instance == null) {
                instance = IngredientPickerService()
            }
            return instance as IngredientPickerService
        }
    }

    fun getIngredient(pickerIngredient: PickerIngredient): PickerIngredient {
        return ingredientTypes[pickerIngredient.id]!!
    }

    fun putIngredient(pickerIngredient: PickerIngredient) {
        ingredientTypes[pickerIngredient.id] = pickerIngredient
    }

    fun putIngredients(pickerIngredients: List<PickerIngredient>) {
        pickerIngredients.forEach { putIngredient(it) }
    }

    fun getIngredients(): Collection<PickerIngredient> {
        return ingredientTypes.values
    }
}