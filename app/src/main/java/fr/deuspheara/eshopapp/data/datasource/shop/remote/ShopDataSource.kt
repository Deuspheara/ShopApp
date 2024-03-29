package fr.deuspheara.eshopapp.data.datasource.shop.remote

import androidx.paging.PagingSource
import fr.deuspheara.eshopapp.data.network.model.ResponseContainer
import fr.deuspheara.eshopapp.data.network.model.shop.ProductRemote
import retrofit2.Response

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.datasource.shop.remote.ShopDataSource
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Shop data source
 *
 */
typealias ProductResponse = ResponseContainer<ProductRemote>

interface ShopDataSource {

    suspend fun getProducts(
        id: String?,
        category: String?,
        page: Int?,
        size: Int?
    ): ProductResponse

    suspend fun getProductByIds(vararg ids: String): ProductResponse?

    fun createProductsPagingSource(
        id: String?,
        category: String?,
    ): PagingSource<Int, ProductRemote>

    suspend fun getCategories() : ResponseContainer<String>

    suspend fun getCart()

    suspend fun addToCart(productId: String)

    suspend fun removeFromCart(productId: String)

    suspend fun getOrders()

    suspend fun createOrder()

    suspend fun getOrderDetail(orderId: String)
}