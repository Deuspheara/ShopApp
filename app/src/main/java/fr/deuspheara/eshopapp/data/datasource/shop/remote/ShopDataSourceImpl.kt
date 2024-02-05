package fr.deuspheara.eshopapp.data.datasource.shop.remote

import android.util.Log
import androidx.paging.PagingSource
import com.google.gson.Gson
import fr.deuspheara.eshopapp.core.coroutines.DispatcherModule
import fr.deuspheara.eshopapp.data.api.auth.ShopApi
import fr.deuspheara.eshopapp.data.network.NetworkModule.apiCall
import fr.deuspheara.eshopapp.data.network.NetworkModule.safeUnwrap
import fr.deuspheara.eshopapp.data.network.model.ResponseContainer
import fr.deuspheara.eshopapp.data.network.model.shop.ProductRemote
import fr.deuspheara.eshopapp.data.network.paging.ApiPagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.datasource.shop.remote.ShopDataSourceImpl
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Shop data source impl
 *
 */
class ShopDataSourceImpl @Inject constructor(
    private val shopApi: ShopApi,
    @DispatcherModule.DispatcherIO private val ioDispatcher: CoroutineDispatcher,
    private val gson: Gson
) : ShopDataSource {

    private companion object {
        private const val TAG = "ShopDataSourceImpl"
    }

    override suspend fun getProducts(
        id: String?,
        category: String?,
        page: Int?,
        size: Int?
    ): ProductResponse {
        return withContext(ioDispatcher) {
            apiCall {
                shopApi.getProducts(
                    id = id,
                    category = category,
                    page = page,
                    size = size
                )
            }.safeUnwrap()
        }
    }

    override suspend fun getProductByIds(vararg ids: String): ProductResponse {
        return withContext(ioDispatcher) {
            apiCall {
                shopApi.getProducts(
                    id = ids.joinToString(separator = ",", prefix = "[", postfix = "]")
                )
            }.safeUnwrap()
        }
    }

    override fun createProductsPagingSource(
        id: String?,
        category: String?,
    ): PagingSource<Int, ProductRemote> {
        return ApiPagingSource(
            apiCall = { page, size ->
                Log.d(TAG, "createProductsPagingSource: page: $page, size: $size")
                apiCall {
                    shopApi.getProducts(
                        id = id,
                        category = category,
                        page = page,
                        size = size
                    )
                }
            },
            dispatcher = ioDispatcher,
            gson = gson
        )
    }

    override suspend fun getCategories(): ResponseContainer<String> {
        return withContext(ioDispatcher) {
            apiCall {
                shopApi.getCategories()
            }.safeUnwrap()
        }
    }

    override suspend fun getCart() {
        TODO("Not yet implemented")
    }

    override suspend fun addToCart(productId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromCart(productId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getOrders() {
        TODO("Not yet implemented")
    }

    override suspend fun createOrder() {
        TODO("Not yet implemented")
    }

    override suspend fun getOrderDetail(orderId: String) {
        TODO("Not yet implemented")
    }
}