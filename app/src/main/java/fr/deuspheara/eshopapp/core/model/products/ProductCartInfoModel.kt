package fr.deuspheara.eshopapp.core.model.products

import fr.deuspheara.eshopapp.data.database.model.ItemCartEntity

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.core.model.products.ProductCartInfoModel
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 *
 *
 */
data class ProductCartInfoModel(
    val id: Identifier,
    val quantity: Quantity
){
    constructor(product: ItemCartEntity) : this(
        id = Identifier(product.productId),
        quantity = Quantity(product.quantity)
    )
}