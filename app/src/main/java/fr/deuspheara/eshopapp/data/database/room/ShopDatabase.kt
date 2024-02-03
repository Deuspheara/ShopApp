package fr.deuspheara.eshopapp.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import fr.deuspheara.eshopapp.data.database.dao.ShopDao
import fr.deuspheara.eshopapp.data.database.model.ProductEntity
import fr.deuspheara.eshopapp.data.database.model.SpecificationEntity

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.database.room.ShopDatabase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Shop database
 *
 */
@Database(
    entities = [ProductEntity::class, SpecificationEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListConverter::class, SpecificationListConverter::class)
abstract class ShopDatabase : RoomDatabase() {
    internal companion object {
        const val DATABASE_NAME = "shop_database.db"
    }

    abstract val shopDao: ShopDao
}

class StringListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }
}

class SpecificationListConverter {
    @TypeConverter
    fun fromString(value: String): List<SpecificationEntity> {
        val specifications = value.split(",").mapNotNull {
            val parts = it.split(";")
            if (parts.size == 3) {
                SpecificationEntity(
                    id = parts[0],
                    name = parts[1],
                    description = parts[2]
                )
            } else {
                null
            }
        }
        return specifications
    }

    @TypeConverter
    fun fromList(list: List<SpecificationEntity>): String {
        return list.joinToString(",") { "${it.id};${it.name};${it.description}" }
    }
}

