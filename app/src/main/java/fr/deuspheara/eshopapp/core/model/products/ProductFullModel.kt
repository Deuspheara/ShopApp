package fr.deuspheara.eshopapp.core.model.products

import fr.deuspheara.eshopapp.data.network.model.shop.Specification

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.core.model.Product
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Product model
 *
 */

data class ProductFullModel(
    val id: Identifier,
    val name: Name,
    val author: Author,
    val brand: Brand,
    val category: Category,
    val availability: Availability,
    val stockQuantity: StockQuantity,
    val description: Description,
    val images: List<ImageUrl>,
    val specifications: List<Specification>,
    val price: Price,
    val promotionPrice: Price,
    val rating: Rating,
    val reviewsCount: ReviewsCount,
) {
    fun doesMatchSearch(searchText: String): Boolean {
        val matchingCombination = listOf(
            name,
            description
        ).joinToString(separator = " ")

        return matchingCombination.contains(searchText, ignoreCase = true)
    }
}

data class Price(
    val value: Double,
    val currency: String
)

@JvmInline
value class Author(val value: String)

@JvmInline
value class Brand(val value: String)

@JvmInline
value class Availability(val value: Boolean)

@JvmInline
value class StockQuantity(val value: Int)

@JvmInline
value class Rating(val value: Float)

@JvmInline
value class ImageUrl(val value: String)

@JvmInline
value class ReviewsCount(val value: Int)

@JvmInline
value class Category(val value: String)

@JvmInline
value class Identifier(val value: String)

@JvmInline
value class Name(val value: String)

@JvmInline
value class Description(val value: String)

