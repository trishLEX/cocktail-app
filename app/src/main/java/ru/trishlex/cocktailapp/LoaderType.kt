package ru.trishlex.cocktailapp

enum class LoaderType(val id: Int) {
    COCKTAILS_LOADER(1),
    COCKTAIL_LOADER(2),
    INGREDIENTS_LOADER(3),
    INGREDIENT_LOADER(4),
    INGREDIENTS_BY_IDS_LOADER(5),
    COCKTAILS_BY_IDS_LOADER(6)
}
