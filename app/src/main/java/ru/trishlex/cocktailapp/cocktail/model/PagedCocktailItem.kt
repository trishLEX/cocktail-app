package ru.trishlex.cocktailapp.cocktail.model

import org.openapitools.client.model.PagedCocktailLightResponse

data class PagedCocktailItem(
    val hasNext: Boolean,
    val nextKey: Int,
    val cocktails: List<CocktailItem>
) {

    constructor(pagedCocktailLightResponse: PagedCocktailLightResponse): this(
        pagedCocktailLightResponse.hasNext,
        pagedCocktailLightResponse.nextStart,
        pagedCocktailLightResponse.cocktails.map { CocktailItem(it) }
    )
}