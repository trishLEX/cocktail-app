package ru.trishlex.cocktailapp.ingredient.model

import org.openapitools.client.model.IngredientTypeDTO
import java.io.Serializable

enum class IngredientTypeEnum : Serializable {
    GIN,
    VODKA,
    TEQUILA,
    RUM,
    WHISKEY,
    BOURBON,
    ABSINTHE,
    COGNAC,
    PORT_WINE,
    SHERRY,
    APERITIF,
    LIQUOR,
    VERMOUTH,
    BITTER,
    WINE,
    BEER,
    SYRUP,
    NON_ALCOHOLIC,
    HONEY,
    DESSERT,
    ADDITIVE,
    GREENS,
    MISC,
    JAM,
    JUICE,
    SPICE,
    DRIED_FRUIT,
    DECORATION,
    FRUIT,
    VEGETABLE,
    TEA,
    NUT,
    BERRY,
    ICE,
    ;
    
    companion object {
        fun fromDto(ingredientTypeDTO: IngredientTypeDTO): IngredientTypeEnum? {
            return when(ingredientTypeDTO.type) {
                IngredientTypeDTO.TypeEnum.GIN -> GIN
                IngredientTypeDTO.TypeEnum.VODKA -> VODKA
                IngredientTypeDTO.TypeEnum.TEQUILA -> TEQUILA
                IngredientTypeDTO.TypeEnum.RUM -> RUM
                IngredientTypeDTO.TypeEnum.WHISKEY -> WHISKEY
                IngredientTypeDTO.TypeEnum.BOURBON -> BOURBON
                IngredientTypeDTO.TypeEnum.ABSINTHE -> ABSINTHE
                IngredientTypeDTO.TypeEnum.COGNAC -> COGNAC
                IngredientTypeDTO.TypeEnum.PORT_WINE -> PORT_WINE
                IngredientTypeDTO.TypeEnum.SHERRY -> SHERRY
                IngredientTypeDTO.TypeEnum.APERITIF -> APERITIF
                IngredientTypeDTO.TypeEnum.LIQUOR -> LIQUOR
                IngredientTypeDTO.TypeEnum.VERMOUTH -> VERMOUTH
                IngredientTypeDTO.TypeEnum.BITTER -> BITTER
                IngredientTypeDTO.TypeEnum.WINE -> WINE
                IngredientTypeDTO.TypeEnum.BEER -> BEER
                IngredientTypeDTO.TypeEnum.SYRUP -> SYRUP
                IngredientTypeDTO.TypeEnum.NON_ALCOHOLIC -> NON_ALCOHOLIC
                IngredientTypeDTO.TypeEnum.HONEY -> HONEY
                IngredientTypeDTO.TypeEnum.DESSERT -> DESSERT
                IngredientTypeDTO.TypeEnum.ADDITIVE -> ADDITIVE
                IngredientTypeDTO.TypeEnum.GREENS -> GREENS
                IngredientTypeDTO.TypeEnum.MISC -> MISC
                IngredientTypeDTO.TypeEnum.JAM -> JAM
                IngredientTypeDTO.TypeEnum.JUICE -> JUICE
                IngredientTypeDTO.TypeEnum.SPICE -> SPICE
                IngredientTypeDTO.TypeEnum.DRIED_FRUIT -> DRIED_FRUIT
                IngredientTypeDTO.TypeEnum.DECORATION -> DECORATION
                IngredientTypeDTO.TypeEnum.FRUIT -> FRUIT
                IngredientTypeDTO.TypeEnum.VEGETABLE -> VEGETABLE
                IngredientTypeDTO.TypeEnum.TEA -> TEA
                IngredientTypeDTO.TypeEnum.NUT -> NUT
                IngredientTypeDTO.TypeEnum.BERRY -> BERRY
                IngredientTypeDTO.TypeEnum.ICE -> ICE
                else -> null
            }
        }
    }
}