package fr.deuspheara.eshopapp.data.repository.shop

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import fr.deuspheara.eshopapp.core.coroutines.DispatcherModule
import fr.deuspheara.eshopapp.core.model.products.Author
import fr.deuspheara.eshopapp.core.model.products.Availability
import fr.deuspheara.eshopapp.core.model.products.Brand
import fr.deuspheara.eshopapp.core.model.products.Category
import fr.deuspheara.eshopapp.core.model.products.Description
import fr.deuspheara.eshopapp.core.model.products.Identifier
import fr.deuspheara.eshopapp.core.model.products.ImageUrl
import fr.deuspheara.eshopapp.core.model.products.Name
import fr.deuspheara.eshopapp.core.model.products.Price
import fr.deuspheara.eshopapp.core.model.products.ProductCartInfoModel
import fr.deuspheara.eshopapp.core.model.products.ProductCartModel
import fr.deuspheara.eshopapp.core.model.products.ProductFullModel
import fr.deuspheara.eshopapp.core.model.products.ProductLightModel
import fr.deuspheara.eshopapp.core.model.products.Quantity
import fr.deuspheara.eshopapp.core.model.products.Rating
import fr.deuspheara.eshopapp.core.model.products.ReviewsCount
import fr.deuspheara.eshopapp.core.model.products.StockQuantity
import fr.deuspheara.eshopapp.data.database.model.ProductWithSpecifications
import fr.deuspheara.eshopapp.data.datasource.shop.local.ShopLocalDataSource
import fr.deuspheara.eshopapp.data.datasource.shop.remote.ProductResponse
import fr.deuspheara.eshopapp.data.datasource.shop.remote.ShopDataSource
import fr.deuspheara.eshopapp.data.network.model.shop.Specification
import fr.deuspheara.eshopapp.data.network.paging.ApiPagingSource.Companion.pagingMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.repository.shop.ShopRepositoryImpl
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Shop repository implementation
 *
 */
class ShopRepositoryImpl @Inject constructor(
    private val shopDataSource: ShopDataSource,
    private val shopLocalDataSource: ShopLocalDataSource,
    @DispatcherModule.DispatcherDefault private val defaultDispatcher: CoroutineDispatcher
) : ShopRepository {

    private companion object {
        private const val TAG = "ShopRepositoryImpl"
    }

    override fun getProducts(
        id : String?,
        category : String?,
        page: Int?,
        size: Int?
    ): Flow<PagingData<ProductLightModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = size ?: 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                shopDataSource.createProductsPagingSource(
                    id = id,
                    category = category
                )
            },
        ).flow.pagingMap(::ProductLightModel).flowOn(defaultDispatcher)
    }

    override fun getRecentLocalProducts(
        page: Int,
        size: Int
    ): Flow<PagingData<ProductLightModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = size,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                shopLocalDataSource.createRecentProductsPagingSource()
            }
        ).flow.pagingMap(::ProductLightModel).flowOn(defaultDispatcher)
    }

    override suspend fun getProductByIds(vararg ids :String): List<ProductFullModel>? {
        val localProducts = shopLocalDataSource.getProductByIds(*ids)?.map { it.toProductFullModel() }
        Log.d(TAG, "getProductByIds: localProducts: $localProducts")
        //insert products ids that are not in local database
        val remoteIds = ids.filter { id -> localProducts?.none { it.id.value == id } == true }
        if (remoteIds.isNotEmpty()) {
            val remoteProducts = fetchAndInsertRemoteProducts(remoteIds.toTypedArray())
            Log.d(TAG, "getProductByIds: remoteProducts: $remoteProducts")
            return (localProducts ?: emptyList()) + (remoteProducts ?: emptyList())
        }
        return localProducts
    }

    private suspend fun fetchAndInsertRemoteProducts(ids: Array<out String>): List<ProductFullModel>? {
        val remoteProduct = shopDataSource.getProductByIds(*ids)
        remoteProduct?.let {
            shopLocalDataSource.insertProducts(remoteProduct)
        }
        return remoteProduct?.toProductList()
    }

    override suspend fun getCategories(): List<String> {
        return shopDataSource.getCategories().results
    }

    override suspend fun getCart() : List<ProductCartModel> {
        return shopLocalDataSource.getCartProducts().map(::ProductCartModel)
    }

    override suspend fun incrementCartItemQuantityById(itemId: Identifier): Int {
        return shopLocalDataSource.incrementCartItemQuantityById(itemId)
    }

    override suspend fun decrementCartItemQuantityById(itemId: Identifier): Int {
        return shopLocalDataSource.decrementCartItemQuantityById(itemId)
    }

    override suspend fun getCartItemById(itemId: Identifier): ProductCartInfoModel? {
        return shopLocalDataSource.getCartItemById(itemId)?.let {
            ProductCartInfoModel(
                id= Identifier(it.productId),
                quantity = Quantity(it.quantity)
            )
        }
    }

    override suspend fun getOrders() {
        TODO("Not yet implemented")
    }

    override suspend fun createOrder() {
        TODO("Not yet implemented")
    }

    override suspend fun getOrderDetail(orderId :String) {
        TODO("Not yet implemented")
    }


    private fun ProductResponse.toProductList(): List<ProductFullModel> {
        return this.results.map { productResponse ->
            ProductFullModel(
                id = Identifier(productResponse.id),
                name = Name(productResponse.name ?: ""),
                author = Author(productResponse.author ?: ""),
                brand = Brand(productResponse.brand ?: ""),
                category = Category(productResponse.category ?: ""),
                availability = Availability(productResponse.availability ?: false),
                stockQuantity = StockQuantity(productResponse.stockQuantity ?: 0),
                description = Description(productResponse.description ?: ""),
                images = productResponse.images?.map { imageUrl ->
                    ImageUrl(imageUrl)
                } ?: emptyList(),
                specifications = productResponse.specifications?.map { specification ->
                    Specification(
                        name = specification.name,
                        description = specification.description
                    )
                } ?: emptyList(),
                price = Price(productResponse.price ?: 0.0, "USD"),
                promotionPrice = Price(productResponse.promotionPrice ?: 0.0, "USD"),
                rating = Rating(productResponse.rating?.toFloat() ?: 0.0f),
                reviewsCount = ReviewsCount(productResponse.reviewsCount ?: 0)
            )
        }
    }

    private fun ProductWithSpecifications.toProductFullModel(): ProductFullModel =
        with(product) {
            ProductFullModel(
                id = Identifier(id),
                name = Name(name),
                author = Author(author),
                brand = Brand(brand),
                category = Category(category),
                availability = Availability(availability),
                stockQuantity = StockQuantity(stockQuantity),
                description = Description(description),
                images = images.map { ImageUrl(it) },
                specifications = specifications.map {
                    Specification(
                        name = it.name,
                        description = it.description
                    )
                },
                price = Price(price, "USD"),
                promotionPrice = Price(promotionPrice, "USD"),
                rating = Rating(rating),
                reviewsCount = ReviewsCount(reviewsCount),
            )
        }



}