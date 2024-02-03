package fr.deuspheara.eshopapp.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.deuspheara.eshopapp.data.database.room.ShopDatabase
import javax.inject.Singleton

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.database.DatabaseModule
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Database module
 *
 */
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideShopDatabase(
        @ApplicationContext context: Context
    ): ShopDatabase {
        return Room.databaseBuilder(
            context,
            ShopDatabase::class.java,
            ShopDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}