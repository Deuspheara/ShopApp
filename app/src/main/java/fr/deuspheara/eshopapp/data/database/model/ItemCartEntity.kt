package fr.deuspheara.eshopapp.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.database.model.ItemCartEntity
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Item cart entity
 *
 */
@Entity(tableName = "cart", primaryKeys = ["product_id"])
data class ItemCartEntity (
    @ColumnInfo(name = "product_id")
    val productId: String,

    val quantity: Int
)

data class ItemCartWithProduct(
    @Embedded
    val itemCart: ItemCartEntity,

    @Relation(
        parentColumn = "product_id",
        entityColumn = "id"
    )
    val product: ProductEntity
)