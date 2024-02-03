package fr.deuspheara.eshopapp.data.datasource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.deuspheara.eshopapp.data.datasource.auth.remote.AuthDataSource
import fr.deuspheara.eshopapp.data.datasource.auth.remote.AuthDataSourceImpl
import fr.deuspheara.eshopapp.data.datasource.shop.local.ShopLocalDataSource
import fr.deuspheara.eshopapp.data.datasource.shop.local.ShopLocalDataSourceImpl
import fr.deuspheara.eshopapp.data.datasource.shop.remote.ShopDataSource
import fr.deuspheara.eshopapp.data.datasource.shop.remote.ShopDataSourceImpl

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.datasource.DataSourceModule
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Data source module
 *
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindsAuthDataSource(
        impl: AuthDataSourceImpl,
    ): AuthDataSource

    @Binds
    abstract fun bindsShopDataSource(
        impl: ShopDataSourceImpl,
    ): ShopDataSource

    @Binds
    abstract fun bindsShopLocalDataSource(
        impl: ShopLocalDataSourceImpl,
    ): ShopLocalDataSource
}
