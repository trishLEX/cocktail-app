package ru.trishlex.cocktailapp.ingredient.model

enum class Unit(val str: String) {
    ML("мл"),
    GR("г"),
    UNIT("шт")
    ;

    companion object {
        private val MAP = Unit.values().associateBy { it.ordinal }

        fun getByOrdinal(ordinal: Int): Unit {
            return MAP[ordinal]!!
        }
    }
}