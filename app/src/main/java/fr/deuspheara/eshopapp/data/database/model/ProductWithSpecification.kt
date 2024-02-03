package fr.deuspheara.eshopapp.data.database.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.database.model.SpecificationEntity
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Specification entity
 *
 */

data class ProductWithSpecifications(
    @Embedded
    val product: ProductEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val specifications: List<SpecificationEntity>
)