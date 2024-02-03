package fr.deuspheara.eshopapp.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.deuspheara.eshopapp.data.repository.auth.AuthRepository
import fr.deuspheara.eshopapp.data.repository.auth.AuthRepositoryImpl
import fr.deuspheara.eshopapp.data.repository.shop.ShopRepository
import fr.deuspheara.eshopapp.data.repository.shop.ShopRepositoryImpl

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.repository.RepositoryModule
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Repository module
 *
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsAuthRepository(
        impl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    abstract fun bindsShopRepository(
        impl: ShopRepositoryImpl,
    ): ShopRepository
}