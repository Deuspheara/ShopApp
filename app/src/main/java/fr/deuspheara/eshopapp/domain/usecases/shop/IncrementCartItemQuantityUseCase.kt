package fr.deuspheara.eshopapp.domain.usecases.shop

import fr.deuspheara.eshopapp.core.model.products.Identifier
import fr.deuspheara.eshopapp.data.repository.shop.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.shop.IncrementCartItemQuantityUseCase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Increment cart item quantity use case
 *
 */
class IncrementCartItemQuantityUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    private companion object {
        private const val TAG = "IncrementCartItemQuantityUseCase"
    }

    suspend operator fun invoke(productId: Identifier): Flow<Int> = flow{
        emit(shopRepository.incrementCartItemQuantityById(productId))
    }
}