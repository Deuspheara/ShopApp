package fr.deuspheara.eshopapp.data.datasource.shop.local

import androidx.paging.PagingSource
import fr.deuspheara.eshopapp.data.database.model.ProductEntity
import fr.deuspheara.eshopapp.data.database.model.ProductWithSpecifications
import fr.deuspheara.eshopapp.data.datasource.shop.remote.ProductResponse

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.datasource.shop.local.ShopLocalDataSource
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Shop local data source
 *
 */
interface ShopLocalDataSource {
    /**
     * Get all products
     *
     * @return List<[ProductEntity]>
     */
    suspend fun getAllProducts(
        page: Int,
        size: Int
    ): List<ProductEntity>

    /**
     * Create recent products paging source
     *
     * @return [PagingSource<Int, ProductEntity>]
     */
    fun createRecentProductsPagingSource(): PagingSource<Int, ProductEntity>

    /**
     * Get product by id
     *
     * @param ids List of ids
     *
     * @return [ProductEntity] or null
     */
    suspend fun getProductByIds(vararg ids: String): List<ProductWithSpecifications>?


    /**
     * Search products
     *
     * @param searchText
     *
     * @return List<[ProductEntity]>
     */
    fun searchProducts(searchText: String): List<ProductEntity>

    /**
     * Get products by category
     *
     * @param category
     *
     * @return List<[ProductEntity]>
     */
    fun getProductsByCategory(category: String): List<ProductEntity>

    /**
     * Insert product
     *
     * @param product
     *
     * @return Long
     */
    suspend fun insertProduct(product: ProductEntity): Long

    /**
     * Insert products
     *
     * @param products
     *
     * @return List<Long>
     */
    suspend fun insertProducts(products: ProductResponse): List<Long>

    /**
     * Delete all products
     */
    suspend fun deleteAllProducts()

    /**
     * Delete product by id
     *
     * @param id
     */
    suspend fun deleteProductById(id: String)
}