package fr.deuspheara.eshopapp.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.database.model.ProductEntity
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Product entity
 *
 */
@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: String,

    val name: String,

    val author: String,

    val brand: String,

    val category: String,

    val availability: Boolean,

    @ColumnInfo(name = "stock_quantity")
    val stockQuantity: Int,

    val description: String,

    val images: List<String>,

    val price: Double,

    @ColumnInfo(name = "promotion_price")
    val promotionPrice: Double,

    val rating: Float,

    @ColumnInfo(name = "reviews_count")
    val reviewsCount: Int,
)