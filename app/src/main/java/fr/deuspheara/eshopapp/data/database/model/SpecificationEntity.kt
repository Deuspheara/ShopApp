package fr.deuspheara.eshopapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.database.model.SpecificationEntity
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 *
 *
 */

@Entity(tableName = "specifications", primaryKeys = ["id", "name"])
class SpecificationEntity(
    val id: String,

    val name: String,

    val description: String
)