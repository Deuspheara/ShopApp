package fr.deuspheara.eshopapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import fr.deuspheara.eshopapp.data.database.model.ProductEntity
import fr.deuspheara.eshopapp.data.database.model.ProductWithSpecifications
import fr.deuspheara.eshopapp.data.database.model.SpecificationEntity

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.database.dao.ShopDao
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Shop dao
 *
 */
@Dao
interface ShopDao {

    @Query("SELECT * FROM products LIMIT :size OFFSET :page")
    fun getAllProducts(
        page: Int,
        size: Int
    ): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id IN (:id)")
    fun getProductByIds(vararg id: String): List<ProductEntity>

    @Query("SELECT * FROM products WHERE name LIKE '%' || :searchText || '%' OR description LIKE '%' || :searchText || '%'")
    fun searchProducts(searchText: String): List<ProductEntity>

    @Query("SELECT * FROM products WHERE category = :category")
    fun getProductsByCategory(category: String): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecifications(specification: List<SpecificationEntity>): List<Long>

    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteProductById(id: String)

    @Transaction
    @Query("SELECT * FROM products")
    fun getAllProductsWithSpecifications(): List<ProductWithSpecifications>

    @Transaction
    @Query("SELECT * FROM products WHERE id IN (:ids)")
    fun getProductWithSpecificationsById(vararg ids: String): List<ProductWithSpecifications>

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Update
    suspend fun updateProducts(products: List<ProductEntity>)

}