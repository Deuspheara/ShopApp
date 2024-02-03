package fr.deuspheara.eshopapp.data.network.model.shop

import com.google.gson.annotations.SerializedName
import fr.deuspheara.eshopapp.core.model.products.Description
import fr.deuspheara.eshopapp.core.model.products.Identifier
import fr.deuspheara.eshopapp.core.model.products.Name

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.network.model.shop.ProductRemote
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 *
 *
 */
data class ProductRemote(
    val id: String,
    val name: String? = null,
    val author: String? = null,
    val brand: String? = null,
    val category: String? = null,
    val availability: Boolean? = null,
    @SerializedName("stock_quantity")
    val stockQuantity: Int? = null,
    val description: String? = null,
    val images: List<String>? = null,
    val specifications: List<Specification>? = null,
    val price: Double? = null,
    @SerializedName("promotion_price")
    val promotionPrice: Double? = null,
    val rating: Double? = null,
    @SerializedName("reviews_count")
    val reviewsCount: Int? = null,
)

data class Specification(
    val name: String,
    val description: String
)