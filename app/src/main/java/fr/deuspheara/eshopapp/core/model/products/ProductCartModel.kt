package fr.deuspheara.eshopapp.core.model.products

import fr.deuspheara.eshopapp.data.database.model.ItemCartWithProduct

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.core.model.products.ProductCartModel
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Product cart model
 *
 */
data class ProductCartModel(
    val id: Identifier,
    val name: Name,
    val description: Description,
    val price: Price,
    val promotionPrice: Price,
    val quantity: Quantity,
    val imageUrl: ImageUrl? = null,
){
    constructor(product: ItemCartWithProduct) : this(
        id = Identifier(product.product.id),
        name = Name(product.product.name),
        description = Description(product.product.description),
        price = Price(product.product.price, "USD"),
        promotionPrice = Price(product.product.promotionPrice, "USD"),
        quantity = Quantity(product.itemCart.quantity),
        imageUrl = ImageUrl(product.product.images.firstOrNull() ?: "")
    )

}

@JvmInline
value class Quantity(val value: Int){
    operator fun minus(quantity: Int) = Quantity(value - quantity)

    operator fun plus(quantity: Int) = Quantity(value + quantity)
}