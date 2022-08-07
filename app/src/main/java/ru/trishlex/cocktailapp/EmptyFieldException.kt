package ru.trishlex.cocktailapp

class EmptyFieldException(val field: FIELD) : RuntimeException(field.text) {
}

enum class FIELD(val text: String) {
    AMOUNT("Не заполнен объем"),
    NAME("Не заполнено название"),
    INGREDIENTS("Не заполнены ингредиенты"),
    TOOLS("Не заполнены штучки"),
    INSTRUCTIONS("Не заполнен рецепт"),
    IMAGE("Не выбрано изображение коктейля"),
    PREVIEW("Не выбрано превью коктейля")
}