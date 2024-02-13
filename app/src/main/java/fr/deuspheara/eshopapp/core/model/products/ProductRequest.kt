package fr.deuspheara.eshopapp.core.model.products

import com.google.gson.annotations.SerializedName
import fr.deuspheara.eshopapp.data.network.model.shop.Specification

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.core.model.products.ProductRequest
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 *
 *
 */
data class ProductRequest(
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
    val currency: String? = null,
    val rating: Double? = null,
    @SerializedName("reviews_count")
    val reviewsCount: Int? = null,
)