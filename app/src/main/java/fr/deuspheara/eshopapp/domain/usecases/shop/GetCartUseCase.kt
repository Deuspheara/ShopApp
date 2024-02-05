package fr.deuspheara.eshopapp.domain.usecases.shop

import fr.deuspheara.eshopapp.data.repository.shop.ShopRepository
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.shop.GetCartUseCase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Get cart use case
 *
 */
class GetCartUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    private companion object {
        private const val TAG = "GetCartUseCase"
    }

    suspend operator fun invoke() = shopRepository.getCart()
}