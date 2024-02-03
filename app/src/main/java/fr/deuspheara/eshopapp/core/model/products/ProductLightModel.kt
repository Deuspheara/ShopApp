package fr.deuspheara.eshopapp.core.model.products

import androidx.recyclerview.widget.DiffUtil
import fr.deuspheara.eshopapp.data.database.model.ProductEntity
import fr.deuspheara.eshopapp.data.network.model.shop.ProductRemote

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.core.model.products.ProductLightModel
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Product light model
 *
 */
data class ProductLightModel(
    val id: String,
    val name: String? = null,
    val description: String? = null,
    val images: List<String>? = null,
    val price: Double? = null,
    val promotionPrice: Double? = null,
    val rating: Double? = null,
) {
    constructor(remote: ProductRemote) : this(
        id = remote.id,
        name = remote.name,
        description = remote.description,
        images = remote.images,
        price = remote.price,
        promotionPrice = remote.promotionPrice,
        rating = remote.rating
    )

    constructor(local: ProductEntity) : this(
        id = local.id,
        name = local.name,
        description = local.description,
        images = local.images,
        price = local.price,
        promotionPrice = local.promotionPrice,
        rating = local.rating.toDouble()
    )

    fun doesMatchSearch(searchText: String): Boolean {
        val matchingCombination = listOf(
            name,
            description
        ).joinToString(separator = " ")

        return matchingCombination.contains(searchText, ignoreCase = true)
    }

    object ItemCallback : DiffUtil.ItemCallback<ProductLightModel>() {
        override fun areItemsTheSame(
            oldItem: ProductLightModel,
            newItem: ProductLightModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductLightModel,
            newItem: ProductLightModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}