package fr.deuspheara.eshopapp.data.datasource.shop.local

import android.util.Log
import androidx.paging.PagingSource
import com.google.gson.Gson
import fr.deuspheara.eshopapp.core.coroutines.DispatcherModule
import fr.deuspheara.eshopapp.core.model.products.Identifier
import fr.deuspheara.eshopapp.data.database.model.ProductEntity
import fr.deuspheara.eshopapp.data.database.model.ProductWithSpecifications
import fr.deuspheara.eshopapp.data.database.model.SpecificationEntity
import fr.deuspheara.eshopapp.data.database.room.ShopDatabase
import fr.deuspheara.eshopapp.data.datasource.shop.remote.ProductResponse
import fr.deuspheara.eshopapp.data.network.NetworkModule.apiCall
import fr.deuspheara.eshopapp.data.network.model.ResponseContainer
import fr.deuspheara.eshopapp.data.network.model.shop.Specification
import fr.deuspheara.eshopapp.data.network.paging.ApiLocalPagingSource
import fr.deuspheara.eshopapp.data.network.paging.ApiPagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.datasource.shop.local.ShopLocalDataSourceImpl
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Shop local data source impl
 *
 */
class ShopLocalDataSourceImpl @Inject constructor(
    private val database: ShopDatabase,
    @DispatcherModule.DispatcherIO private val ioDispatcher: CoroutineDispatcher,
    private val gson: Gson
) : ShopLocalDataSource {
    private companion object {
        private const val TAG = "ShopLocalDataSourceImpl"
    }

    override suspend fun getAllProducts(page: Int, size: Int): List<ProductEntity> {
        return withContext(ioDispatcher) {
            try {
                database.shopDao.getAllProducts(page, size)
            } catch (e: Exception) {
                Log.e(TAG, "Error while getting all products", e)
                throw e
            }
        }
    }

    override fun createRecentProductsPagingSource(): PagingSource<Int, ProductEntity> {
        return ApiLocalPagingSource(
            apiCall = { page, size ->
                database.shopDao.getAllProducts(page, size)
            },
            dispatcher = ioDispatcher,
            gson = gson
        )
    }

    override suspend fun getProductByIds(vararg ids: String): List<ProductWithSpecifications> {
        return withContext(ioDispatcher) {
            try {
                database.shopDao.getProductWithSpecificationsById(*ids)
            } catch (e: Exception) {
                Log.e(TAG, "Error while getting product by id: ${ids.joinToString()}", e)
                throw e
            }
        }
    }

    override fun searchProducts(searchText: String): List<ProductEntity> {
        TODO("Not yet implemented")
    }

    override fun getProductsByCategory(category: String): List<ProductEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertProduct(product: ProductEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insertProducts(products: ProductResponse): List<Long> {
        return withContext(ioDispatcher) {
            try {
                //TODO: check if can do the two at the same time
                database.shopDao.insertProducts(products.toLocalProduct())
                database.shopDao.insertSpecifications(products.results.flatMap {
                    Log.d(TAG, "insertProducts: ${products.results.flatMap { it.specifications ?: emptyList()} }")
                    it.specifications?.map { specification ->
                        specification.toSpecificationEntity(it.id)
                    } ?: emptyList()

                })

            } catch (e: Exception) {
                Log.e(TAG, "Error while inserting products: ${products.results.joinToString()}", e)
                throw e
            }
        }
    }

    override suspend fun deleteAllProducts() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProductById(id: String) {
        TODO("Not yet implemented")
    }

    private fun Specification.toSpecificationEntity(productId: String): SpecificationEntity {
        return SpecificationEntity(
            id = productId,
            name = name,
            description = description,
        )
    }

    private fun ProductResponse.toLocalProduct(): List<ProductEntity> {
        return this.results.map { productResponse ->
            ProductEntity(
                id = productResponse.id,
                name = productResponse.name ?: "",
                author = productResponse.author ?: "",
                brand = productResponse.brand ?: "",
                category = productResponse.category ?: "",
                availability = productResponse.availability ?: false,
                stockQuantity = productResponse.stockQuantity ?: 0,
                description = productResponse.description ?: "",
                images = productResponse.images ?: emptyList(),
                price = productResponse.price ?: 0.0,
                promotionPrice = productResponse.promotionPrice ?: 0.0,
                rating = productResponse.rating?.toFloat() ?: 0.0f,
                reviewsCount = productResponse.reviewsCount ?: 0,
            )
        }
    }
}