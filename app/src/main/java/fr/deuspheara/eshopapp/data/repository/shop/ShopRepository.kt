package fr.deuspheara.eshopapp.data.repository.shop

import androidx.paging.PagingData
import fr.deuspheara.eshopapp.core.model.products.ProductFullModel
import fr.deuspheara.eshopapp.core.model.products.ProductLightModel
import kotlinx.coroutines.flow.Flow

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.repository.shop.ShopRepository
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Shop repository
 *
 */
interface ShopRepository {

    fun getProducts(
        id: String?,
        category: String?,
        page: Int?,
        size: Int?
    ): Flow<PagingData<ProductLightModel>>

    fun getRecentLocalProducts(
        page: Int,
        size: Int
    ): Flow<PagingData<ProductLightModel>>

    suspend fun getProductByIds(vararg ids: String): List<ProductFullModel>?

    suspend fun getCategories()

    suspend fun getCart()

    suspend fun addToCart(productId: String)

    suspend fun removeFromCart(productId: String)

    suspend fun getOrders()

    suspend fun createOrder()

    suspend fun getOrderDetail(orderId: String)
}