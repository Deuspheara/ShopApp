package fr.deuspheara.eshopapp.data.repository.shop

import androidx.paging.PagingData
import fr.deuspheara.eshopapp.core.model.products.Identifier
import fr.deuspheara.eshopapp.core.model.products.ProductCartInfoModel
import fr.deuspheara.eshopapp.core.model.products.ProductCartModel
import fr.deuspheara.eshopapp.core.model.products.ProductFullModel
import fr.deuspheara.eshopapp.core.model.products.ProductLightModel
import fr.deuspheara.eshopapp.data.network.model.shop.Specification
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

    suspend fun getCategories(): List<String>

    suspend fun getCart() : List<ProductCartModel>

    suspend fun incrementCartItemQuantityById(itemId: Identifier): Int

    suspend fun decrementCartItemQuantityById(itemId: Identifier): Int

    suspend fun getCartItemById(itemId: Identifier): ProductCartInfoModel?

    suspend fun getOrders()

    suspend fun createOrder()

    suspend fun getOrderDetail(orderId: String)

    suspend fun createProduct(
        name : String,
        author : String,
        price : Double,
        promotionPrice : Double,
        description : String,
        currency : String,
        brand : String,
        category : String,
        availability : Boolean,
        stockQuantity : Int,
        images : List<String>,
        specifications : List<Specification>,
        rating : Double,
        reviewsCount : Int
    ) : ProductLightModel
}